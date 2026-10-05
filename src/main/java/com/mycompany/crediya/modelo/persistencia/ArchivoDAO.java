/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crediya.modelo.persistencia;

/**
 *
 * @author jessica urrego
 */

 import com.mycompany.crediya.modelo.clases.Empleado;   
import com.mycompany.crediya.modelo.clases.Prestamo;
import com.mycompany.crediya.modelo.clases.Cliente;
import com.mycompany.crediya.modelo.clases.Pago;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ArchivoDAO {

    private static final String ARCHIVO_CLIENTES = "clientes.txt";
    private static final String ARCHIVO_PRESTAMOS = "prestamos.txt";
    private static final String ARCHIVO_PAGOS = "pagos.txt";
    private static final String ARCHIVO_EMPLEADOS = "empleados.txt";
   

    public void guardarCliente(Cliente cliente) {

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(ARCHIVO_CLIENTES, true))) {

            writer.write(
                    cliente.getId() + " | "
                    + cliente.getNombre() + " | "
                    + cliente.getDocumento() + " | "
                    + cliente.getCorreo() + " | "
                    + cliente.getTelefono()
            );

            writer.newLine();

            System.out.println("Cliente guardado en clientes.txt.");

        } catch (IOException e) {

            System.out.println(
                    "Error al guardar el cliente en archivo: "
                    + e.getMessage()
            );
        }
    }
        
        public void guardarPrestamo(Prestamo prestamo) {

    try (BufferedWriter writer = new BufferedWriter(
            new FileWriter(ARCHIVO_PRESTAMOS, true))) {

        writer.write(
                prestamo.getId() + " | "
                + prestamo.getClienteId() + " | "
                + prestamo.getEmpleadoId() + " | "
                + prestamo.getMonto() + " | "
                + prestamo.getInteres() + " | "
                + prestamo.getCuotas() + " | "
                + prestamo.getFechaInicio() + " | "
                + prestamo.getEstado() + " | "
                + prestamo.getSaldoPendiente()
        );

        writer.newLine();

        System.out.println("Préstamo guardado en prestamos.txt.");

    } catch (IOException e) {

        System.out.println(
                "Error al guardar el préstamo en archivo: "
                + e.getMessage()
        );
    }
}
   public void guardarPago(Pago pago) {

    try (BufferedWriter writer = new BufferedWriter(
            new FileWriter(ARCHIVO_PAGOS, true))) {

        writer.write(
                pago.getId() + " | "
                + pago.getPrestamoId() + " | "
                + pago.getFechaPago() + " | "
                + pago.getMonto()
        );

        writer.newLine();

        System.out.println("Pago guardado en pagos.txt.");

    } catch (IOException e) {

        System.out.println(
                "Error al guardar el pago en archivo: "
                + e.getMessage()
        );
    }
} 
   public void guardarEmpleado(Empleado empleado) {

    try (BufferedWriter writer = new BufferedWriter(
            new FileWriter(ARCHIVO_EMPLEADOS, true))) {

        writer.write(
                empleado.getId() + " | "
                + empleado.getNombre() + " | "
                + empleado.getDocumento() + " | "
                + empleado.getRol() + " | "
                + empleado.getCorreo() + " | "
                + empleado.getSalario()
        );

        writer.newLine();

        System.out.println("Empleado guardado en empleados.txt.");

    } catch (IOException e) {

        System.out.println(
                "Error al guardar el empleado en archivo: "
                + e.getMessage()
        );
    }
}
}
    
    
   

