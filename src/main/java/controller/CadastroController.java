package controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.DatabaseConnection;
import Model.ValorSalvo;

public class CadastroController {

    public void adicionarCadastro(String nome, double entrada, double saida, String descricao) {
        String sql = "INSERT INTO CADASTRO (entrada, saida, saldo, descricao, id_login) " +
                     "VALUES (?, ?, ?, ?, (SELECT id_login FROM LOGIN WHERE nome = ?))";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, entrada);
            stmt.setDouble(2, saida);
            stmt.setDouble(3, entrada - saida);
            stmt.setString(4, descricao);
            stmt.setString(5, nome);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluirCadastro(int idCadastro) {
        String sql = "DELETE FROM CADASTRO WHERE id_cadastro = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCadastro);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> listarCadastros(String nome) {
        List<String> cadastros = new ArrayList<>();
        String sql = "SELECT id_cadastro, entrada, saida, saldo, descricao FROM CADASTRO " +
                     "WHERE id_login = (SELECT id_login FROM LOGIN WHERE nome = ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idCadastro = rs.getInt("id_cadastro");
                double entrada = rs.getDouble("entrada");
                double saida = rs.getDouble("saida");
                // double saldo = rs.getDouble("saldo");
                String descricao = rs.getString("descricao");

                cadastros.add(idCadastro + ": Entrada: " + entrada + " | Saída: " + saida + " | Descrição: " + descricao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cadastros;
    }

    public double calcularTotalCaixa(String nome) {
        double totalCaixa = 0;
        String sql = "SELECT SUM(entrada) - SUM(saida) AS totalCaixa FROM CADASTRO " +
                     "WHERE id_login = (SELECT id_login FROM LOGIN WHERE nome = ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                totalCaixa = rs.getDouble("totalCaixa");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalCaixa;
    }
    
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

    public List<ValorSalvo> listarValoresSalvos(String nome) {
        List<ValorSalvo> valoresSalvos = new ArrayList<>();
        String sql = "SELECT valor_total, data_inicio, data_fim FROM SALVOS " +
                     "WHERE id_login = (SELECT id_login FROM LOGIN WHERE nome = ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                double valorTotal = rs.getDouble("valor_total");
                Date dataInicio = rs.getDate("data_inicio");
                Date dataFim = rs.getDate("data_fim");

                valoresSalvos.add(new ValorSalvo(valorTotal, dataInicio, dataFim));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return valoresSalvos;
    }
}
