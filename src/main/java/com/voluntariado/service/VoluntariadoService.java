package com.voluntariado.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class VoluntariadoService {

    private final JdbcTemplate jdbcTemplate;

    // Constructor para inyectar JdbcTemplate
    public VoluntariadoService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Método para registrar un nuevo usuario
    public void registrarUsuario(String nombre, String email, String password, String rol) {
        String sql = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, nombre, email, password, rol);
    }

    // Método para crear un nuevo proyecto
    public void crearProyecto(String titulo, String descripcion, String fechaInicio, String fechaFin, int creadoPor) {
        String sql = "INSERT INTO projects (title, description, start_date, end_date, created_by) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, titulo, descripcion, fechaInicio, fechaFin, creadoPor);
    }

}
