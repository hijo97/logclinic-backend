package org.logclinic.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.logclinic.api.database.ConexaoBanco;
import org.logclinic.api.model.Paciente;

public class PacienteDAO {

   public boolean cadastrarPaciente(Paciente paciente) {
        String sql = "INSERT INTO paciente (nome, data_nascimento, genero, cpf, telefone, email, empresa, " +
                     "logradouro, bairro, cidade, numero, complemento, cep, uf, convenio_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBanco.conectar()) {
            if (conn == null) return false;

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, paciente.getNome());
                ps.setString(2, paciente.getDataNascimento()); // Corrigido mapeamento do getter
                ps.setString(3, paciente.getGenero());       
                ps.setString(4, paciente.getCpf());
                ps.setString(5, paciente.getTelefone());
                ps.setString(6, paciente.getEmail());
                ps.setString(7, paciente.getEmpresa());
                ps.setString(8, paciente.getLogradouro());
                ps.setString(9, paciente.getBairro());
                ps.setString(10, paciente.getCidade());     
                ps.setString(11, paciente.getNumero());
                ps.setString(12, paciente.getComplemento());
                ps.setString(13, paciente.getCep());
                ps.setString(14, paciente.getUf());         
                ps.setInt(15, paciente.getConvenioId()); // Corrigido mapeamento do getter

                int linhasAfetadas = ps.executeUpdate();
                return linhasAfetadas > 0;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar paciente no banco: " + e.getMessage());
            return false;
        }
    }

    public List<Paciente> listarTodos() {
    List<Paciente> lista = new ArrayList<>();
    String sql = "SELECT * FROM paciente ORDER BY nome ASC"; // Traz em ordem alfabética

    try (Connection conn = ConexaoBanco.conectar()) {
        if (conn == null) return lista;

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Paciente p = new Paciente();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setDataNascimento(rs.getString("data_nascimento"));
                p.setGenero(rs.getString("genero"));
                p.setCpf(rs.getString("cpf"));
                p.setTelefone(rs.getString("telefone"));
                p.setEmail(rs.getString("email"));
                p.setEmpresa(rs.getString("empresa"));
                p.setLogradouro(rs.getString("logradouro"));
                p.setBairro(rs.getString("bairro"));
                p.setCidade(rs.getString("cidade"));
                p.setNumero(rs.getString("numero"));
                p.setComplemento(rs.getString("complemento"));
                p.setCep(rs.getString("cep"));
                p.setUf(rs.getString("uf"));
                p.setConvenioId(rs.getInt("convenio_id"));
                p.setDataCadastro(rs.getString("data_cadastro"));

                lista.add(p);
            }
        }
    } catch (SQLException e) {
        System.out.println("Erro ao listar pacientes: " + e.getMessage());
    }
    return lista;
}

public boolean excluirPaciente(int id) {
    // 1. Primeiro apaga as consultas vinculadas a esse paciente para liberar a FK
    String sqlConsultas = "DELETE FROM consulta WHERE paciente_id = ?";
    // 2. Depois apaga o paciente
    String sqlPaciente = "DELETE FROM paciente WHERE id = ?";

    try (Connection conn = ConexaoBanco.conectar()) {
        if (conn == null) return false;

        // Desativa o auto-commit para fazer os dois deletes na mesma transação
        conn.setAutoCommit(false);

        try (PreparedStatement ps1 = conn.prepareStatement(sqlConsultas);
             PreparedStatement ps2 = conn.prepareStatement(sqlPaciente)) {
            
            // Apaga as consultas primeiro
            ps1.setInt(1, id);
            ps1.executeUpdate();

            // Apaga o paciente
            ps2.setInt(1, id);
            int linhasAfetadas = ps2.executeUpdate();

            // Se o paciente foi apagado, confirma as duas alterações no banco
            if (linhasAfetadas > 0) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("Erro na transação de exclusão: " + e.getMessage());
            return false;
        }
    } catch (SQLException e) {
        System.out.println("Erro ao conectar ou excluir paciente: " + e.getMessage());
        return false;
    }
}

public Paciente buscarPorId(int id) {
    String sql = "SELECT * FROM paciente WHERE id = ?";
    try (Connection conn = ConexaoBanco.conectar()) {
        if (conn == null) return null;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Paciente p = new Paciente();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setDataNascimento(rs.getString("data_nascimento"));
                    p.setGenero(rs.getString("genero"));
                    p.setCpf(rs.getString("cpf"));
                    p.setTelefone(rs.getString("telefone"));
                    p.setEmail(rs.getString("email"));
                    p.setEmpresa(rs.getString("empresa"));
                    p.setLogradouro(rs.getString("logradouro"));
                    p.setBairro(rs.getString("bairro"));
                    p.setCidade(rs.getString("cidade"));
                    p.setNumero(rs.getString("numero"));
                    p.setComplemento(rs.getString("complemento"));
                    p.setCep(rs.getString("cep"));
                    p.setUf(rs.getString("uf"));
                    p.setConvenioId(rs.getInt("convenio_id"));
                    return p;
                }
            }
        }
    } catch (SQLException e) {
        System.out.println("Erro ao buscar paciente por ID: " + e.getMessage());
    }
    return null;
}

public boolean atualizarPaciente(Paciente paciente) {
        String sql = "UPDATE paciente SET nome=?, data_nascimento=?, genero=?, cpf=?, telefone=?, email=?, empresa=?, " +
                     "logradouro=?, bairro=?, cidade=?, numero=?, complemento=?, cep=?, uf=?, convenio_id=? WHERE id=?";

        try (Connection conn = ConexaoBanco.conectar()) {
            if (conn == null) return false;

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, paciente.getNome());
                ps.setString(2, paciente.getDataNascimento()); // Corrigido mapeamento do getter
                ps.setString(3, paciente.getGenero());
                ps.setString(4, paciente.getCpf());
                ps.setString(5, paciente.getTelefone());
                ps.setString(6, paciente.getEmail());
                ps.setString(7, paciente.getEmpresa());
                ps.setString(8, paciente.getLogradouro());
                ps.setString(9, paciente.getBairro());
                ps.setString(10, paciente.getCidade());
                ps.setString(11, paciente.getNumero());
                ps.setString(12, paciente.getComplemento());
                ps.setString(13, paciente.getCep());
                ps.setString(14, paciente.getUf());
                ps.setInt(15, paciente.getConvenioId()); // Corrigido mapeamento do getter
                ps.setInt(16, paciente.getId());

                int linhasAfetadas = ps.executeUpdate();
                return linhasAfetadas > 0;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar paciente: " + e.getMessage());
            return false;
        }
    }
}// Fim da classe PacienteDAO