package org.logclinic.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.logclinic.api.database.ConexaoBanco;
import org.logclinic.api.model.Convenio;

public class ConvenioDAO {

    public List<Convenio> listarTodos() {
        List<Convenio> lista = new ArrayList<>();
        String sql = "SELECT id, nome, cobertura FROM convenio ORDER BY nome ASC";

        try (Connection conn = ConexaoBanco.conectar()) {
            if (conn == null) return lista;

            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Convenio c = new Convenio();
                    c.setId(rs.getInt("id"));
                    c.setNome(rs.getString("nome"));
                    c.setCobertura(rs.getString("cobertura"));
                    lista.add(c);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar convenios: " + e.getMessage());
        }
        return lista;
    }

    // MÉTODO PARA SALVAR NO BANCO
    public boolean cadastrarConvenio(Convenio convenio) {
        String sql = "INSERT INTO convenio (nome, cobertura) VALUES (?, ?)";

        try (Connection conn = ConexaoBanco.conectar()) {
            if (conn == null) return false;

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, convenio.getNome());
                ps.setString(2, convenio.getCobertura());

                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar convênio no banco: " + e.getMessage());
            return false;
        }
    }
}