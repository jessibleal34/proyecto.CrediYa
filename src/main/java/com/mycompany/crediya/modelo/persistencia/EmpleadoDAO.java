/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crediya.modelo.persistencia;

import com.mycompany.crediya.modelo.clases.Empleado;
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
public class EmpleadoDAO {
    public void insertar(Empleado empleado) {

    String sql = "INSERT INTO empleados "
            + "(nombre, documento, rol, correo, salario) "
            + "VALUES (?, ?, ?, ?, ?)";

    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement ps = conexion.prepareStatement(sql)) {

        ps.setString(1, empleado.getNombre());
        ps.setString(2, empleado.getDocumento());
        ps.setString(3, empleado.getRol());
        ps.setString(4, empleado.getCorreo());
        ps.setDouble(5, empleado.getSalario());

        ps.executeUpdate();

        System.out.println("Empleado guardado correctamente.");

    } catch (SQLException e) {

        System.out.println("Error al guardar empleado: "
                + e.getMessage());
    }
    }
    
    public List<Empleado> listar() {

    List<Empleado> empleados = new ArrayList<>();

    String sql = "SELECT * FROM empleados";

    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement ps = conexion.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {

            Empleado empleado = new Empleado(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("documento"),
                    rs.getString("rol"),
                    rs.getString("correo"),
                    rs.getDouble("salario")
            );

            empleados.add(empleado);
        }

    } catch (SQLException e) {

        System.out.println("Error al listar empleados: "
                + e.getMessage());
    }

    return empleados;
}
}