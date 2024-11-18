package com.voluntariado.dao;

import com.voluntariado.model.Project;
import java.sql.*;
import java.util.*;

public class ProjectDao {
    private Connection connection;

    public ProjectDao(Connection connection) {
        this.connection = connection;
    }

    public void createProject(Project project) throws SQLException {
        String query = "INSERT INTO projects (title, description, start_date, end_date, created_by) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, project.getTitle());
            stmt.setString(2, project.getDescription());
            stmt.setDate(3, project.getStartDate());
            stmt.setDate(4, project.getEndDate());
            stmt.setInt(5, project.getCreatedBy());
            stmt.executeUpdate();
        }
    }

    public List<Project> getAllProjects() throws SQLException {
        String query = "SELECT * FROM projects";
        List<Project> projects = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Project project = new Project();
                project.setId(rs.getInt("id"));
                project.setTitle(rs.getString("title"));
                project.setDescription(rs.getString("description"));
                project.setStartDate(rs.getDate("start_date"));
                project.setEndDate(rs.getDate("end_date"));
                project.setCreatedBy(rs.getInt("created_by"));
                projects.add(project);
            }
        }
        return projects;
    }
}
