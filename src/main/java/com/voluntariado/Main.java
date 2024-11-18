package com.voluntariado;

import com.voluntariado.dao.*;
import com.voluntariado.model.*;
import java.sql.*;
import java.util.*;

public class Main {
    private static Connection connection;
    private static UserDao userDao;
    private static ProjectDao projectDao;
    private static InscriptionDao inscriptionDao;

    public static void main(String[] args) {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/voluntariado", "root", "tu_contraseña");
            userDao = new UserDao(connection);
            projectDao = new ProjectDao(connection);
            inscriptionDao = new InscriptionDao(connection);

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Menú:");
                System.out.println("1. Registrar Usuario");
                System.out.println("2. Iniciar sesión");
                System.out.println("3. Crear Proyecto");
                System.out.println("4. Inscribirse en Proyecto");
                System.out.println("5. Salir");
                System.out.print("Seleccione una opción: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        registrarUsuario(scanner);
                        break;
                    case 2:
                        iniciarSesion(scanner);
                        break;
                    case 3:
                        crearProyecto(scanner);
                        break;
                    case 4:
                        inscribirseEnProyecto(scanner);
                        break;
                    case 5:
                        System.out.println("Saliendo...");
                        return;
                    default:
                        System.out.println("Opción no válida.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void registrarUsuario(Scanner scanner) {
        System.out.print("Nombre: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();
        System.out.print("Rol (VOLUNTARIO/PUBLICANTE): ");
        String role = scanner.nextLine();

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);

        try {
            userDao.registerUser(user);
            System.out.println("Usuario registrado exitosamente.");
        } catch (SQLException e) {
            System.out.println("Error al registrar el usuario.");
        }
    }

    private static void iniciarSesion(Scanner scanner) {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        try {
            User user = userDao.getUserByEmail(email);
            if (user != null && user.getPassword().equals(password)) {
                System.out.println("Bienvenido, " + user.getName());
            } else {
                System.out.println("Credenciales incorrectas.");
            }
        } catch (SQLException e) {
            System.out.println("Error al iniciar sesión.");
        }
    }

    private static void crearProyecto(Scanner scanner) {
        System.out.print("Título del proyecto: ");
        String title = scanner.nextLine();
        System.out.print("Descripción: ");
        String description = scanner.nextLine();
        System.out.print("Fecha de inicio (YYYY-MM-DD): ");
        String startDateStr = scanner.nextLine();
        System.out.print("Fecha de fin (YYYY-MM-DD): ");
        String endDateStr = scanner.nextLine();

        Date startDate = Date.valueOf(startDateStr);
        Date endDate = Date.valueOf(endDateStr);

        int publicanteId = 1;


        Project project = new Project();
        project.setTitle(title);
        project.setDescription(description);
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setCreatedBy(publicanteId);

        try {
            projectDao.createProject(project);
            System.out.println("Proyecto creado exitosamente.");
        } catch (SQLException e) {
            System.out.println("Error al crear el proyecto.");
        }
    }

    private static void inscribirseEnProyecto(Scanner scanner) {
        System.out.print("ID del proyecto al que inscribirse: ");
        int projectId = scanner.nextInt();
        scanner.nextLine();

        int volunteerId = 1;

        Inscription inscription = new Inscription();
        inscription.setUserId(volunteerId);
        inscription.setProjectId(projectId);
        inscription.setDate(new Date(System.currentTimeMillis()));

        try {
            inscriptionDao.registerInscription(inscription);
            System.out.println("Inscripción realizada exitosamente.");
        } catch (SQLException e) {
            System.out.println("Error al inscribirse en el proyecto.");
        }
    }
}
