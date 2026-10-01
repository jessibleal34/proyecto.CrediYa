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

/**
 *
 * @author jessica urrego
 */
public class PrestamoDAO {
    
    public void insertar(Prestamo prestamo) {

    String sql = "INSERT INTO prestamos "
            + "(cliente_id, empleado_id, monto, interes, cuotas, fecha_inicio, estado) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement ps = conexion.prepareStatement(sql)) {

        ps.setInt(1, prestamo.getClienteId());
        ps.setInt(2, prestamo.getEmpleadoId());
        ps.setDouble(3, prestamo.getMonto());
        ps.setDouble(4, prestamo.getInteres());
        ps.setInt(5, prestamo.getCuotas());
        ps.setDate(6, java.sql.Date.valueOf(prestamo.getFechaInicio()));
        ps.setString(7, prestamo.getEstado());

        ps.executeUpdate();

        System.out.println("Préstamo guardado correctamente.");

    } catch (SQLException e) {

        System.out.println("Error al guardar préstamo: "
                + e.getMessage());
    }
}
    public List<Prestamo> listar() {

    List<Prestamo> prestamos = new ArrayList<>();

    String sql = "SELECT * FROM prestamos";

    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement ps = conexion.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {

            Prestamo prestamo = new Prestamo(
                    rs.getInt("id"),
                    rs.getInt("cliente_id"),
                    rs.getInt("empleado_id"),
                    rs.getDouble("monto"),
                    rs.getDouble("interes"),
                    rs.getInt("cuotas"),
                    rs.getDate("fecha_inicio").toLocalDate(),
                    rs.getString("estado")
            );

            prestamos.add(prestamo);
        }

    } catch (SQLException e) {

        System.out.println("Error al listar préstamos: "
                + e.getMessage());
    }

    return prestamos;
}
}
