package controller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.DatabaseConnection;

public class LoginController {
    public boolean autenticar(String nome, String senha) {
        String sql = "SELECT * FROM LOGIN WHERE nome = ? AND senha = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
