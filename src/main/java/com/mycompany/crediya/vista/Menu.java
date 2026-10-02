/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package com.mycompany.crediya.vista;



import com.mycompany.crediya.modelo.clases.Cliente;
import com.mycompany.crediya.modelo.clases.Pago;
import com.mycompany.crediya.modelo.clases.Prestamo;
import com.mycompany.crediya.modelo.persistencia.ClienteDAO;
import com.mycompany.crediya.modelo.persistencia.PagoDAO;
import com.mycompany.crediya.modelo.persistencia.PrestamoDAO;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private static final Scanner scanner = new Scanner(System.in);

    private static final ClienteDAO clienteDAO = new ClienteDAO();
    private static final PrestamoDAO prestamoDAO = new PrestamoDAO();
    private static final PagoDAO pagoDAO = new PagoDAO();

    public static void main(String[] args) {

        int opcion;

        do {

            mostrarMenu();

            System.out.print("Seleccione una opción: ");
            opcion = leerEntero();

            switch (opcion) {

                case 1:
                    registrarCliente();
                    break;

                case 2:
                    listarClientes();
                    break;

                case 3:
                    listarPrestamos();
                    break;

                case 4:
                    registrarPago();
                    break;

                case 5:
                    listarPagos();
                    break;

                case 6:
                    mostrarReportes();
                    break;

                case 7:
                    System.out.println("\nSaliendo de CrediYa...");
                    break;

                default:
                    System.out.println(
                            "\nOpción no válida. Intente nuevamente."
                    );
            }

        } while (opcion != 7);

        scanner.close();
    }

    // =========================
    // MENÚ PRINCIPAL
    // =========================

    private static void mostrarMenu() {

        System.out.println("\n=================================");
        System.out.println("          CREDIYA S.A.S.");
        System.out.println("     Sistema de Créditos");
        System.out.println("=================================");
        System.out.println("1. Registrar cliente");
        System.out.println("2. Listar clientes");
        System.out.println("3. Listar préstamos");
        System.out.println("4. Registrar pago");
        System.out.println("5. Ver histórico de pagos");
        System.out.println("6. Reportes");
        System.out.println("7. Salir");
        System.out.println("=================================");
    }

    // =========================
    // CLIENTES
    // =========================

    private static void registrarCliente() {

        System.out.println("\n=== REGISTRAR CLIENTE ===");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Documento: ");
        String documento = scanner.nextLine();

        System.out.print("Correo: ");
        String correo = scanner.nextLine();

        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();

        if (nombre.isBlank()
                || documento.isBlank()
                || correo.isBlank()
                || telefono.isBlank()) {

            System.out.println(
                    "Todos los campos son obligatorios."
            );

            return;
        }

        Cliente cliente = new Cliente(
                0,
                nombre,
                documento,
                correo,
                telefono
        );

        clienteDAO.insertar(cliente);
    }

    private static void listarClientes() {

        System.out.println("\n=== CLIENTES REGISTRADOS ===");

        List<Cliente> clientes = clienteDAO.listar();

        if (clientes.isEmpty()) {

            System.out.println("No hay clientes registrados.");

            return;
        }

        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }
    }

    // =========================
    // PRÉSTAMOS
    // =========================

    private static void listarPrestamos() {

        System.out.println("\n=== PRÉSTAMOS ===");

        List<Prestamo> prestamos = prestamoDAO.listar();

        if (prestamos.isEmpty()) {

            System.out.println("No hay préstamos registrados.");

            return;
        }

        for (Prestamo prestamo : prestamos) {
            System.out.println(prestamo);
        }
    }

    // =========================
    // PAGOS
    // =========================

    private static void registrarPago() {

        System.out.println("\n=== REGISTRAR PAGO ===");

        System.out.print("ID del préstamo: ");
        int prestamoId = leerEntero();

        System.out.print("Monto del pago: ");
        double monto = leerDouble();

        if (monto <= 0) {

            System.out.println(
                    "El monto debe ser mayor que cero."
            );

            return;
        }

        Pago pago = new Pago(
                0,
                prestamoId,
                LocalDate.now(),
                monto
        );

        pagoDAO.insertar(pago);
    }

    private static void listarPagos() {

        System.out.println("\n=== HISTÓRICO DE PAGOS ===");

        List<Pago> pagos = pagoDAO.listar();

        if (pagos.isEmpty()) {

            System.out.println("No hay pagos registrados.");

            return;
        }

        for (Pago pago : pagos) {
            System.out.println(pago);
        }
    }

    // =========================
    // REPORTES
    // =========================

    private static void mostrarReportes() {

        int opcion;

        do {

            System.out.println("\n=================================");
            System.out.println("            REPORTES");
            System.out.println("=================================");
            System.out.println("1. Préstamos pendientes");
            System.out.println("2. Préstamos pagados");
            System.out.println("3. Volver al menú principal");
            System.out.println("=================================");

            System.out.print("Seleccione una opción: ");
            opcion = leerEntero();

            switch (opcion) {

                case 1:
                    reportePendientes();
                    break;

                case 2:
                    reportePagados();
                    break;

                case 3:
                    break;

                default:
                    System.out.println(
                            "Opción no válida."
                    );
            }

        } while (opcion != 3);
    }

    private static void reportePendientes() {

        System.out.println(
                "\n=== PRÉSTAMOS PENDIENTES ==="
        );

        List<Prestamo> prestamos = prestamoDAO.listar();

        prestamos.stream()
                .filter(p -> p.getSaldoPendiente() > 0)
                .forEach(System.out::println);
    }

    private static void reportePagados() {

        System.out.println(
                "\n=== PRÉSTAMOS PAGADOS ==="
        );

        List<Prestamo> prestamos = prestamoDAO.listar();

        prestamos.stream()
                .filter(p -> p.getSaldoPendiente() == 0)
                .forEach(System.out::println);
    }

    // =========================
    // MÉTODOS AUXILIARES
    // =========================

    private static int leerEntero() {

        while (true) {

            try {

                int numero = Integer.parseInt(
                        scanner.nextLine()
                );

                return numero;

            } catch (NumberFormatException e) {

                System.out.print(
                        "Ingrese un número válido: "
                );
            }
        }
    }

    private static double leerDouble() {

        while (true) {

            try {

                double numero = Double.parseDouble(
                        scanner.nextLine()
                );

                return numero;

            } catch (NumberFormatException e) {

                System.out.print(
                        "Ingrese un valor válido: "
                );
            }
        }
    }
}
