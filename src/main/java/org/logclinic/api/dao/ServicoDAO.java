package org.logclinic.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.logclinic.api.database.ConexaoBanco;
import org.logclinic.api.model.Servico;

public class ServicoDAO {

    // Método para Salvar o Serviço no Banco
    public boolean cadastrarServico(Servico servico) {
        String sql = "INSERT INTO servicos (nome, valor) VALUES (?, ?)";

        try (Connection conn = ConexaoBanco.conectar()) {
            if (conn == null) return false;

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, servico.getNome());
                ps.setDouble(2, servico.getValor());

                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar serviço no banco: " + e.getMessage());
            return false;
        }
    }

    // Método para Listar todos os Serviços
    public List<Servico> listarTodos() {
        List<Servico> lista = new ArrayList<>();
        String sql = "SELECT id, nome, valor FROM servicos ORDER BY nome ASC";

        try (Connection conn = ConexaoBanco.conectar()) {
            if (conn == null) return lista;

            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Servico s = new Servico();
                    s.setId(rs.getInt("id"));
                    s.setNome(rs.getString("nome"));
                    s.setValor(rs.getDouble("valor"));
                    lista.add(s);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar serviços: " + e.getMessage());
        }
        return lista;
    }

    // Método para Atualizar o Serviço no Banco
    public boolean atualizarServico(Servico servico) {
        String sql = "UPDATE servicos SET nome = ?, valor = ? WHERE id = ?";

        try (Connection conn = ConexaoBanco.conectar()) {
            if (conn == null) return false;

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, servico.getNome());
                ps.setDouble(2, servico.getValor());
                ps.setInt(3, servico.getId());

                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar serviço no banco: " + e.getMessage());
            return false;
        }
    }

    // Método para Excluir o Serviço do Banco
    public boolean excluirServico(int id) {
        String sql = "DELETE FROM servicos WHERE id = ?";

        try (Connection conn = ConexaoBanco.conectar()) {
            if (conn == null) return false;

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao excluir serviço: " + e.getMessage());
            return false;
        }
    }
    
}