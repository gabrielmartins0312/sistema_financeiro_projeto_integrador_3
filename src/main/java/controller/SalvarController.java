package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.DatabaseConnection;
import Model.Salvar;

public class SalvarController {

    public void salvarTotalCaixa(double valorTotal, Date dataInicio, Date dataFim, String nome) {
        String sql = "INSERT INTO SALVOS (valor_total, data_inicio, data_fim, id_login) " +
                     "VALUES (?, ?, ?, (SELECT id_login FROM LOGIN WHERE nome = ?))";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, valorTotal);
            stmt.setDate(2, dataInicio);
            stmt.setDate(3, dataFim);
            stmt.setString(4, nome);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Salvar> listarValoresSalvos(String nome) {
        List<Salvar> valoresSalvos = new ArrayList<>();
        String sql = "SELECT id_salvo, valor_total, data_inicio, data_fim FROM SALVOS " +
                     "WHERE id_login = (SELECT id_login FROM LOGIN WHERE nome = ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idSalvo = rs.getInt("id_salvo");
                double valorTotal = rs.getDouble("valor_total");
                Date dataInicio = rs.getDate("data_inicio");
                Date dataFim = rs.getDate("data_fim");

                valoresSalvos.add(new Salvar(idSalvo, valorTotal, dataInicio, dataFim));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return valoresSalvos;
    }

    public void excluirValorSalvo(int idSalvo) {
        String sql = "DELETE FROM SALVOS WHERE id_salvo = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idSalvo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
