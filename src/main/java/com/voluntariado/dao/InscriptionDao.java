package com.voluntariado.dao;

import com.voluntariado.model.Inscription;
import java.sql.*;
import java.util.*;

public class InscriptionDao {
    private Connection connection;

    public InscriptionDao(Connection connection) {
        this.connection = connection;
    }

    public void registerInscription(Inscription inscription) throws SQLException {
        String query = "INSERT INTO inscriptions (user_id, project_id, date) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, inscription.getUserId());
            stmt.setInt(2, inscription.getProjectId());
            stmt.setDate(3, inscription.getDate());
            stmt.executeUpdate();
        }
    }

    public List<Inscription> getInscriptionByUserId(int userId) throws SQLException {
        String query = "SELECT * FROM inscriptions WHERE user_id = ?";
        List<Inscription> inscriptions = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Inscription inscription = new Inscription();
                inscription.setId(rs.getInt("id"));
                inscription.setUserId(rs.getInt("user_id"));
                inscription.setProjectId(rs.getInt("project_id"));
                inscription.setDate(rs.getDate("date"));
                inscriptions.add(inscription);
            }
        }
        return inscriptions;
    }
}
