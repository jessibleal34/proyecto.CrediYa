/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crediya.modelo.persistencia;


import com.mycompany.crediya.modelo.clases.Cliente;
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
public class ClienteDAO {
    public void insertar(Cliente cliente) {

    String sql = "INSERT INTO clientes "
            + "(nombre, documento, correo, telefono) "
            + "VALUES (?, ?, ?, ?)";

    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement ps = conexion.prepareStatement(sql)) {

        ps.setString(1, cliente.getNombre());
        ps.setString(2, cliente.getDocumento());
        ps.setString(3, cliente.getCorreo());
        ps.setString(4, cliente.getTelefono());

        ps.executeUpdate();

        System.out.println("Cliente guardado correctamente.");

    } catch (SQLException e) {

        System.out.println("Error al guardar cliente: "
                + e.getMessage());
    }
}public List<Cliente> listar() {

    List<Cliente> clientes = new ArrayList<>();

    String sql = "SELECT * FROM clientes";

    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement ps = conexion.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {

            Cliente cliente = new Cliente(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("documento"),
                    rs.getString("correo"),
                    rs.getString("telefono")
            );

            clientes.add(cliente);
        }

    } catch (SQLException e) {

        System.out.println("Error al listar clientes: "
                + e.getMessage());
    }

    return clientes;
}
    
    
}
