package org.logclinic.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.logclinic.api.database.ConexaoBanco;
import org.logclinic.api.model.Operador;



public class OperadorDAO {

   public boolean cadastrarOperador(Operador operador) {
        
        String sql = "INSERT INTO operador (nome, cpf, login, senha, setor, perfil) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBanco.conectar()) {
            if (conn == null) return false;

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, operador.getNome());
                ps.setString(2, operador.getCpf());
                ps.setString(3, operador.getLogin());
                ps.setString(4, operador.getSenha());
                ps.setString(5, operador.getSetor());  
                ps.setString(6, operador.getPerfil());
                
                int linhasAfetadas = ps.executeUpdate();
                return linhasAfetadas > 0;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar operador no banco: " + e.getMessage());
            return false;
        }
    }

    public List<Operador> listarTodos() {
        List<Operador> lista = new ArrayList<>();
        String sql = "SELECT * FROM operador ORDER BY nome ASC";

        try (Connection conn = ConexaoBanco.conectar()) {
            if (conn == null) return lista;

            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Operador p = new Operador();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setCpf(rs.getString("cpf"));
                    p.setLogin(rs.getString("login"));
                    p.setSenha(rs.getString("senha"));
                    p.setSetor(rs.getString("setor"));
                    p.setPerfil(rs.getString("perfil"));
                   
                    lista.add(p);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar operadores: " + e.getMessage());
        }
        return lista;
    }

    public boolean excluirOperador(int id) {
        String sqlOperador = "DELETE FROM operador WHERE id = ?";

        try (Connection conn = ConexaoBanco.conectar()) {
            if (conn == null) return false;

            conn.setAutoCommit(false);

            try (PreparedStatement ps2 = conn.prepareStatement(sqlOperador)) {
                ps2.setInt(1, id);
                int linhasAfetadas = ps2.executeUpdate();

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
            System.out.println("Erro ao conectar ou excluir operador: " + e.getMessage());
            return false;
        }
    }

    public Operador buscarPorId(int id) {
        String sql = "SELECT * FROM operador WHERE id = ?";
        try (Connection conn = ConexaoBanco.conectar()) {
            if (conn == null) return null;

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Operador p = new Operador();
                        p.setId(rs.getInt("id"));
                        p.setNome(rs.getString("nome"));
                        p.setCpf(rs.getString("cpf"));
                        p.setLogin(rs.getString("login"));
                        p.setSenha(rs.getString("senha"));
                        p.setSetor(rs.getString("setor"));
                        p.setPerfil(rs.getString("perfil"));
                        
                        return p;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar operador por ID: " + e.getMessage());
        }
        return null;
    }

    public boolean atualizarOperador(Operador operador) {
        
        String sql = "UPDATE operador SET nome=?, cpf=?, senha=?, setor=?, login=?, perfil=? WHERE id=?";

        try (Connection conn = ConexaoBanco.conectar()) {
            if (conn == null) return false;

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                // Alinhado estritamente com a ordem da String SQL acima
                ps.setString(1, operador.getNome());
                ps.setString(2, operador.getCpf());
                ps.setString(3, operador.getLogin());
                ps.setString(4, operador.getSenha());       
                ps.setString(5, operador.getSetor());
                ps.setString(6, operador.getPerfil());
                ps.setInt(7, operador.getId());

                int linhasAfetadas = ps.executeUpdate();
                return linhasAfetadas > 0;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar operador: " + e.getMessage());
            return false;
        }
    }
public Operador realizarLogin(String login, String senha) {
    // Busca um operador que combine exatamente o login E a senha informados
    String sql = "SELECT * FROM operador WHERE login = ? AND senha = ?";
    
    try (Connection conn = ConexaoBanco.conectar()) {
        if (conn == null) return null;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, login);
            ps.setString(2, senha);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Se encontrou o registro, monta o objeto Operador com os dados do banco
                    Operador p = new Operador();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setCpf(rs.getString("cpf"));
                    p.setLogin(rs.getString("login"));
                    p.setSenha(rs.getString("senha"));
                    p.setSetor(rs.getString("setor"));
                    p.setPerfil(rs.getString("perfil"));
                    
                    return p; // Retorna o usuário logado com sucesso!
                }
            }
        }
    } catch (SQLException e) {
        System.out.println("Erro ao realizar login no banco: " + e.getMessage());
    }
    return null; // Retorna null se não encontrar ou se der erro
}

}