package org.logclinic.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.logclinic.api.database.ConexaoBanco;
import org.logclinic.api.model.Consulta;

public class ConsultaDAO {

    public boolean agendar(Consulta consulta) {
        // CORRIGIDO PARA O SINGULAR: INSERT INTO consulta
        String sql = "INSERT INTO consulta (paciente_id, servicos_id, convenio_id, data_consulta, hora_consulta) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBanco.conectar()) {
            if (conn == null) return false;

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, consulta.getPaciente_id());
                ps.setInt(2, consulta.getServicos_id());
                ps.setInt(3, consulta.getConvenio_id());
                ps.setString(4, consulta.getData_consulta());
                ps.setString(5, consulta.getHora_consulta());

                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao agendar consulta no banco: " + e.getMessage());
            return false;
        }
    }

  public List<Consulta> listarToda() {
    List<Consulta> lista = new ArrayList<>();
    
    // SQL atualizado com o segundo INNER JOIN para buscar o nome do serviço
    String sql = "SELECT c.id, c.paciente_id, c.servicos_id, c.convenio_id, c.data_consulta, c.hora_consulta, " +
                 "p.nome AS paciente_nome, s.nome AS servico_nome " +
                 "FROM consulta c " +
                 "INNER JOIN paciente p ON c.paciente_id = p.id " +
                 "INNER JOIN servicos s ON c.servicos_id = s.id " +
                 "ORDER BY c.data_consulta ASC, c.hora_consulta ASC";

    try (Connection conn = ConexaoBanco.conectar()) {
        if (conn == null) return lista;

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Consulta c = new Consulta();
                c.setId(rs.getInt("id"));
                c.setPaciente_id(rs.getInt("paciente_id"));
                c.setServicos_id(rs.getInt("servicos_id")); // Use o nome exato da coluna ajustada (com ou sem S)
                c.setConvenio_id(rs.getInt("convenio_id"));
                c.setData_consulta(rs.getString("data_consulta"));
                c.setHora_consulta(rs.getString("hora_consulta"));
                
                // Mapeia os nomes vindos do JOIN para o front-end ler
                c.setPaciente_nome(rs.getString("paciente_nome"));
                c.setServico_nome(rs.getString("servico_nome")); // <--- Nova linha adicionada
                
                lista.add(c);
            }
        }
    } catch (SQLException e) {
        System.out.println("Erro ao listar consultas da agenda: " + e.getMessage());
    }
    return lista;
}
    public boolean desmarcarConsulta(int id) {
        // CORRIGIDO PARA O SINGULAR: DELETE FROM consulta
        String sql = "DELETE FROM consulta WHERE id = ?"; 

        try (Connection conn = ConexaoBanco.conectar()) {
            if (conn == null) return false;

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao desmarcar consulta: " + e.getMessage());
            return false;
        }
    }
}