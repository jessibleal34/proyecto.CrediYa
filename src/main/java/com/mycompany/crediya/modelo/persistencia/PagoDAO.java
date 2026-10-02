/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crediya.modelo.persistencia;

import com.mycompany.crediya.modelo.clases.Pago;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jessica urrego
 */

public class PagoDAO {

    public void insertar(Pago pago) {

        String consultaSaldo = "SELECT saldo_pendiente "
                + "FROM prestamos WHERE id = ?";

        String insertarPago = "INSERT INTO pagos "
                + "(prestamo_id, fecha_pago, monto) "
                + "VALUES (?, ?, ?)";

        String actualizarPrestamo = "UPDATE prestamos "
                + "SET saldo_pendiente = saldo_pendiente - ? "
                + "WHERE id = ?";

        Connection conexion = null;

        try {

            // 1. Abrir conexión
            conexion = ConexionBD.conectar();

            // 2. Desactivar autocommit
            conexion.setAutoCommit(false);

            // 3. Consultar saldo del préstamo
            try (PreparedStatement psSaldo =
                    conexion.prepareStatement(consultaSaldo)) {

                psSaldo.setInt(1, pago.getPrestamoId());

                try (ResultSet rs = psSaldo.executeQuery()) {

                    // El préstamo no existe
                    if (!rs.next()) {
                        System.out.println(
                                "No se encontró el préstamo."
                        );

                        conexion.rollback();
                        return;
                    }

                    double saldoActual =
                            rs.getDouble("saldo_pendiente");

                    // 4. Validar que el pago sea mayor que cero
                    if (pago.getMonto() <= 0) {

                        System.out.println(
                                "El monto del pago debe ser mayor que cero."
                        );

                        conexion.rollback();
                        return;
                    }

                    // 5. Validar que el pago no supere el saldo
                    if (pago.getMonto() > saldoActual) {

                        System.out.println(
                                "El pago no puede ser mayor "
                                + "al saldo pendiente."
                        );

                        conexion.rollback();
                        return;
                    }

                    // 6. Insertar el pago
                    try (PreparedStatement psPago =
                            conexion.prepareStatement(insertarPago)) {

                        psPago.setInt(1, pago.getPrestamoId());
                        psPago.setDate(
                                2,
                                java.sql.Date.valueOf(
                                        pago.getFechaPago()
                                )
                        );
                        psPago.setDouble(3, pago.getMonto());

                        psPago.executeUpdate();
                    }

                    // 7. Actualizar saldo
                    try (PreparedStatement psActualizar =
                            conexion.prepareStatement(
                                    actualizarPrestamo)) {

                        psActualizar.setDouble(
                                1,
                                pago.getMonto()
                        );

                        psActualizar.setInt(
                                2,
                                pago.getPrestamoId()
                        );

                        psActualizar.executeUpdate();
                    }

                    // 8. Si todo salió bien, confirmar
                    conexion.commit();

                    double nuevoSaldo =
                            saldoActual - pago.getMonto();

                    System.out.println(
                            "Pago registrado correctamente."
                    );

                    System.out.println(
                            "Saldo anterior: $" + saldoActual
                    );

                    System.out.println(
                            "Pago realizado: $" + pago.getMonto()
                    );

                    System.out.println(
                            "Nuevo saldo: $" + nuevoSaldo
                    );
                }
            }

        } catch (SQLException e) {

            // Si algo falla, deshacer toda la operación
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

            // Volver a activar autocommit
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

    public List<Pago> listar() {

        List<Pago> pagos = new ArrayList<>();

        String sql = "SELECT * FROM pagos";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps =
                     conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Pago pago = new Pago(
                        rs.getInt("id"),
                        rs.getInt("prestamo_id"),
                        rs.getDate("fecha_pago").toLocalDate(),
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