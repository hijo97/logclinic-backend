package org.logclinic.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.logclinic.api.database.ConexaoBanco;
import org.logclinic.api.model.Medico;

public class MedicoDAO {

    // Método para Cadastrar (Salvar) o médico no Banco
    public boolean cadastrarMedico(Medico medico) {
        String sql = "INSERT INTO medico (nome, crm, crm_uf, especialidade, telefone) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBanco.conectar()) {
            if (conn == null) return false;

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, medico.getNome());
                ps.setString(2, medico.getCrm());
                ps.setString(3, medico.getCrm_uf());
                ps.setString(4, medico.getEspecialidade()); 
                ps.setString(5, medico.getTelefone());

                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar médico no banco: " + e.getMessage());
            return false;
        }
    }

    // Método para Listar todos os Médicos
    public List<Medico> listarTodos() {
        List<Medico> lista = new ArrayList<>();
        String sql = "SELECT * FROM medico ORDER BY nome ASC";

        try (Connection conn = ConexaoBanco.conectar()) {
            if (conn == null) return lista;

            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Medico m = new Medico();
                    m.setId(rs.getInt("id"));
                    m.setNome(rs.getString("nome"));
                    m.setCrm(rs.getString("crm"));
                    m.setCrm_uf(rs.getString("crm_uf"));
                    m.setEspecialidade(rs.getString("especialidade"));
                    m.setTelefone(rs.getString("telefone"));

                    lista.add(m);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar médicos: " + e.getMessage());
        }
        return lista;
    }

    // Método para Buscar Médico Específico por ID
    public Medico buscarPorId(int id) {
        String sql = "SELECT * FROM medico WHERE id = ?";
        
        try (Connection conn = ConexaoBanco.conectar()) {
            if (conn == null) return null;

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Medico m = new Medico();
                        m.setId(rs.getInt("id"));
                        m.setNome(rs.getString("nome"));
                        m.setCrm(rs.getString("crm"));
                        m.setCrm_uf(rs.getString("crm_uf"));
                        m.setEspecialidade(rs.getString("especialidade"));
                        m.setTelefone(rs.getString("telefone"));
                        return m;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar médico por ID: " + e.getMessage());
        }
        return null;
    }

    // Método para Atualizar o Médico no Banco
    public boolean atualizarMedico(Medico medico) {
        String sql = "UPDATE medico SET nome = ?, crm = ?, crm_uf = ?, especialidade = ?, telefone = ? WHERE id = ?";
    
        try (Connection conn = ConexaoBanco.conectar()) {
            if (conn == null) return false;

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, medico.getNome());
                ps.setString(2, medico.getCrm());
                ps.setString(3, medico.getCrm_uf());
                ps.setString(4, medico.getEspecialidade()); 
                ps.setString(5, medico.getTelefone());
                ps.setInt(6, medico.getId()); // ID usado na cláusula WHERE

                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar médico: " + e.getMessage());
            return false;
        }
    }

    // Método para Excluir o Médico do Banco
    public boolean excluirMedico(int id) {
        String sql = "DELETE FROM medico WHERE id = ?";

        try (Connection conn = ConexaoBanco.conectar()) {
            if (conn == null) return false;

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao excluir médico: " + e.getMessage());
            return false;
        }
    }
}