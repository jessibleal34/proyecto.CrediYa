/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crediya.modelo.persistencia;


import com.mycompany.crediya.modelo.clases.Prestamo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

/**
 *
 * @author jessica urrego
 */
public class PrestamoDAO {
    
   public void insertar(Prestamo prestamo) {

    String sql = "INSERT INTO prestamos "
            + "(cliente_id, empleado_id, monto, interes, cuotas, "
            + "fecha_inicio, estado, saldo_pendiente) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement ps = conexion.prepareStatement(
                 sql,
                 Statement.RETURN_GENERATED_KEYS)) {

        double saldoInicial = prestamo.calcularMontoTotal();
        prestamo.setSaldoPendiente(saldoInicial);

        ps.setInt(1, prestamo.getClienteId());
        ps.setInt(2, prestamo.getEmpleadoId());
        ps.setDouble(3, prestamo.getMonto());
        ps.setDouble(4, prestamo.getInteres());
        ps.setInt(5, prestamo.getCuotas());
        ps.setDate(6, java.sql.Date.valueOf(prestamo.getFechaInicio()));
        ps.setString(7, prestamo.getEstado());
        ps.setDouble(8, saldoInicial);

        ps.executeUpdate();

        // Obtener el ID generado por MySQL
        try (ResultSet rs = ps.getGeneratedKeys()) {

            if (rs.next()) {

                int idGenerado = rs.getInt(1);

                // Guardar el ID en el objeto Prestamo
                prestamo.setId(idGenerado);

                System.out.println(
                        "Préstamo guardado correctamente."
                );

                System.out.println(
                        "ID generado: " + idGenerado
                );

                System.out.println(
                        "Saldo pendiente inicial: $" + saldoInicial
                );
            }
        }

    } catch (SQLException e) {

        System.out.println(
                "Error al guardar préstamo: "
                + e.getMessage()
        );
    }
}
    public List<Prestamo> listar() {

    List<Prestamo> prestamos = new ArrayList<>();

    String sql = """
    SELECT id, cliente_id, empleado_id, monto, interes, cuotas,
           fecha_inicio, estado, saldo_pendiente
    FROM prestamos
    """;

    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement ps = conexion.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {

           while (rs.next()) {

    Prestamo prestamo = new Prestamo();

    prestamo.setId(rs.getInt("id"));
    prestamo.setClienteId(rs.getInt("cliente_id"));
    prestamo.setEmpleadoId(rs.getInt("empleado_id"));
    prestamo.setMonto(rs.getDouble("monto"));
    prestamo.setInteres(rs.getDouble("interes"));
    prestamo.setCuotas(rs.getInt("cuotas"));
    prestamo.setFechaInicio(
        rs.getDate("fecha_inicio").toLocalDate()
    );
    prestamo.setEstado(
        rs.getString("estado")
    );

    prestamo.setSaldoPendiente(
        rs.getDouble("saldo_pendiente")
    );

    prestamos.add(prestamo);
}
           
        }

    } catch (SQLException e) {

        System.out.println("Error al listar préstamos: "
                + e.getMessage());
    }

    return prestamos;
}
}
