/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.crediya;

import com.mycompany.crediya.modelo.clases.Cliente;
import com.mycompany.crediya.modelo.clases.Empleado;
import com.mycompany.crediya.modelo.clases.Pago;
import com.mycompany.crediya.modelo.clases.Prestamo;
import com.mycompany.crediya.modelo.persistencia.ClienteDAO;
import com.mycompany.crediya.modelo.persistencia.ConexionBD;
import com.mycompany.crediya.modelo.persistencia.EmpleadoDAO;
import com.mycompany.crediya.modelo.persistencia.PrestamoDAO;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;



/**
 *
 * @author jessica urrego
 */


public class CrediYa {

    public static void main(String[] args) {
        

        try {

            Connection conexion = ConexionBD.conectar();

            System.out.println("================================");
            System.out.println("CONEXIÓN EXITOSA A MYSQL");
            System.out.println("================================");

            conexion.close();

        } catch (Exception e) {

            System.out.println("ERROR AL CONECTAR CON MYSQL");
            System.out.println(e.getMessage());
        }
        // fin dee conexion
        
    


        PrestamoDAO dao = new PrestamoDAO();

        Prestamo prestamo = new Prestamo(
                0,
                1,
                1,
                1000000,
                10,
                5,
                LocalDate.now(),
                "PENDIENTE"
        );

        dao.insertar(prestamo);

        System.out.println("\n=== PRÉSTAMOS ===");

        List<Prestamo> prestamos = dao.listar();

        for (Prestamo p : prestamos) {
            System.out.println(p);
        }
    }
}
       

      

        

    

       
    
    
    

    
    
    
    
    
    
    

    

        