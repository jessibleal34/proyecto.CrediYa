/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crediya.modelo.persistencia;

import com.mycompany.crediya.modelo.clases.Pago;
import com.mycompany.crediya.modelo.clases.Prestamo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PagoDAO {

    private final ArchivoDAO archivoDAO = new ArchivoDAO();

    public void insertar(Pago pago) {

        String consultaSaldo =
                "SELECT id, cliente_id, empleado_id, monto, interes, "
                + "cuotas, fecha_inicio, estado, saldo_pendiente "
                + "FROM prestamos "
                + "WHERE id = ?";

        String insertarPago =
                "INSERT INTO pagos "
                + "(prestamo_id, fecha_pago, monto) "
                + "VALUES (?, ?, ?)";

        String actualizarPrestamo =
                "UPDATE prestamos "
                + "SET saldo_pendiente = saldo_pendiente - ?, "
                + "estado = CASE "
                + "WHEN saldo_pendiente - ? <= 0 THEN 'pagado' "
                + "ELSE 'pendiente' "
                + "END "
                + "WHERE id = ?";

        Connection conexion = null;

        try {

            // ==========================================
            // 1. ABRIR CONEXIÓN
            // ==========================================

            conexion = ConexionBD.conectar();

            // ==========================================
            // 2. DESACTIVAR AUTOCOMMIT
            // ==========================================

            conexion.setAutoCommit(false);

            // ==========================================
            // 3. CONSULTAR PRÉSTAMO
            // ==========================================

            try (PreparedStatement psSaldo =
                    conexion.prepareStatement(consultaSaldo)) {

                psSaldo.setInt(
                        1,
                        pago.getPrestamoId()
                );

                try (ResultSet rs =
                        psSaldo.executeQuery()) {

                    // ==========================================
                    // VALIDAR QUE EL PRÉSTAMO EXISTA
                    // ==========================================

                    if (!rs.next()) {

                        System.out.println(
                                "No se encontró el préstamo."
                        );

                        conexion.rollback();

                        return;
                    }

                    // ==========================================
                    // OBTENER SALDO ACTUAL
                    // ==========================================

                    double saldoActual =
                            rs.getDouble("saldo_pendiente");

                    // ==========================================
                    // 4. VALIDAR MONTO DEL PAGO
                    // ==========================================

                    if (pago.getMonto() <= 0) {

                        System.out.println(
                                "El monto del pago debe "
                                + "ser mayor que cero."
                        );

                        conexion.rollback();

                        return;
                    }

                    // ==========================================
                    // 5. VALIDAR QUE NO SUPERE EL SALDO
                    // ==========================================

                    if (pago.getMonto() > saldoActual) {

                        System.out.println(
                                "El pago no puede ser mayor "
                                + "al saldo pendiente."
                        );

                        conexion.rollback();

                        return;
                    }

                    // ==========================================
                    // 6. INSERTAR PAGO EN MYSQL
                    // ==========================================

                    try (PreparedStatement psPago =
                            conexion.prepareStatement(
                                    insertarPago,
                                    Statement.RETURN_GENERATED_KEYS)) {

                        psPago.setInt(
                                1,
                                pago.getPrestamoId()
                        );

                        psPago.setDate(
                                2,
                                java.sql.Date.valueOf(
                                        pago.getFechaPago()
                                )
                        );

                        psPago.setDouble(
                                3,
                                pago.getMonto()
                        );

                        psPago.executeUpdate();

                        // ==========================================
                        // OBTENER ID GENERADO
                        // ==========================================

                        try (ResultSet rsPago =
                                psPago.getGeneratedKeys()) {

                            if (rsPago.next()) {

                                int idGenerado =
                                        rsPago.getInt(1);

                                pago.setId(idGenerado);

                                System.out.println(
                                        "ID del pago generado: "
                                        + idGenerado
                                );
                            }
                        }
                    }

                    // ==========================================
                    // 7. ACTUALIZAR SALDO Y ESTADO EN MYSQL
                    // ==========================================

                    try (PreparedStatement psActualizar =
                            conexion.prepareStatement(
                                    actualizarPrestamo)) {

                        // Primer ?
                        psActualizar.setDouble(
                                1,
                                pago.getMonto()
                        );

                        // Segundo ?
                        psActualizar.setDouble(
                                2,
                                pago.getMonto()
                        );

                        // Tercer ?
                        psActualizar.setInt(
                                3,
                                pago.getPrestamoId()
                        );

                        int filasActualizadas =
                                psActualizar.executeUpdate();

                        System.out.println(
                                "Filas de préstamo actualizadas: "
                                + filasActualizadas
                        );
                    }

                    // ==========================================
                    // 8. CALCULAR NUEVO SALDO
                    // ==========================================

                    double nuevoSaldo =
                            saldoActual - pago.getMonto();

                    // Evitar -0.0
                    if (nuevoSaldo < 0) {
                        nuevoSaldo = 0;
                    }

                    // ==========================================
                    // 9. DETERMINAR NUEVO ESTADO
                    // ==========================================

                    String nuevoEstado;

                    if (nuevoSaldo == 0) {

                        nuevoEstado = "pagado";

                    } else {

                        nuevoEstado = "pendiente";
                    }

                    // ==========================================
                    // 10. CONFIRMAR TRANSACCIÓN MYSQL
                    // ==========================================

                    conexion.commit();

                    // ==========================================
                    // 11. CREAR PRÉSTAMO ACTUALIZADO
                    // ==========================================

                    Prestamo prestamoActualizado =
                            new Prestamo();

                    prestamoActualizado.setId(
                            rs.getInt("id")
                    );

                    prestamoActualizado.setClienteId(
                            rs.getInt("cliente_id")
                    );

                    prestamoActualizado.setEmpleadoId(
                            rs.getInt("empleado_id")
                    );

                    prestamoActualizado.setMonto(
                            rs.getDouble("monto")
                    );

                    prestamoActualizado.setInteres(
                            rs.getDouble("interes")
                    );

                    prestamoActualizado.setCuotas(
                            rs.getInt("cuotas")
                    );

                    prestamoActualizado.setFechaInicio(
                            rs.getDate("fecha_inicio")
                                    .toLocalDate()
                    );

                    prestamoActualizado.setEstado(
                            nuevoEstado
                    );

                    prestamoActualizado.setSaldoPendiente(
                            nuevoSaldo
                    );

                    // ==========================================
                    // 12. ACTUALIZAR PRESTAMOS.TXT
                    // ==========================================

                    archivoDAO.actualizarPrestamo(
                            prestamoActualizado
                    );

                    // ==========================================
                    // 13. GUARDAR PAGO EN PAGOS.TXT
                    // ==========================================

                    archivoDAO.guardarPago(pago);

                    // ==========================================
                    // 14. MOSTRAR RESULTADO
                    // ==========================================

                    System.out.println(
                            "Pago registrado correctamente."
                    );

                    System.out.println(
                            "Saldo anterior: $"
                            + saldoActual
                    );

                    System.out.println(
                            "Pago realizado: $"
                            + pago.getMonto()
                    );

                    System.out.println(
                            "Nuevo saldo: $"
                            + nuevoSaldo
                    );

                    System.out.println(
                            "Estado del préstamo: "
                            + nuevoEstado.toUpperCase()
                    );
                }
            }

        } catch (SQLException e) {

            // ==========================================
            // ROLLBACK SI OCURRE UN ERROR
            // ==========================================

            if (conexion != null) {

                try {

                    conexion.rollback();

                    System.out.println(
                            "Error. Se realizó ROLLBACK."
                    );

                } catch (SQLException ex) {

                    System.out.println(
                            "Error al realizar rollback: "
                            + ex.getMessage()
                    );
                }
            }

            System.out.println(
                    "Error al registrar el pago: "
                    + e.getMessage()
            );

        } finally {

            // ==========================================
            // CERRAR CONEXIÓN
            // ==========================================

            if (conexion != null) {

                try {

                    conexion.setAutoCommit(true);

                    conexion.close();

                } catch (SQLException e) {

                    System.out.println(
                            "Error al cerrar la conexión: "
                            + e.getMessage()
                    );
                }
            }
        }
    }

    // ==================================================
    // LISTAR PAGOS
    // ==================================================

    public List<Pago> listar() {

        List<Pago> pagos = new ArrayList<>();

        String sql =
                "SELECT * FROM pagos";

        try (Connection conexion =
                ConexionBD.conectar();
             PreparedStatement ps =
                conexion.prepareStatement(sql);
             ResultSet rs =
                ps.executeQuery()) {

            while (rs.next()) {

                Pago pago = new Pago(
                        rs.getInt("id"),
                        rs.getInt("prestamo_id"),
                        rs.getDate("fecha_pago")
                                .toLocalDate(),
                        rs.getDouble("monto")
                );

                pagos.add(pago);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar pagos: "
                    + e.getMessage()
            );
        }

        return pagos;
    }
}