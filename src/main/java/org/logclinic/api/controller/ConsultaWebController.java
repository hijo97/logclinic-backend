package org.logclinic.api.controller;

import java.util.List;

import org.logclinic.api.dao.ConsultaDAO;
import org.logclinic.api.model.Consulta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/consultas")
@CrossOrigin(origins = "*")
public class ConsultaWebController {

    private final ConsultaDAO consultaDAO = new ConsultaDAO();

    @PostMapping("/agendar")
    public ResponseEntity<?> agendar(@RequestBody Consulta consulta) {
        if (consulta.getPaciente_id() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Paciente inválido ou não selecionado.");
        }

        // CORRIGIDO: Mudou de agendarConsulta para agendar
        boolean sucesso = consultaDAO.agendar(consulta);

        if (sucesso) {
            return ResponseEntity.ok("Consulta agendada com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao gravar o agendamento no banco de dados.");
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Consulta>> listar() {
        // CORRIGIDO: Mudou de listarTodas para listarToda (conforme está no seu DAO)
        List<Consulta> consultas = consultaDAO.listarToda();
        return ResponseEntity.ok(consultas);
    }

    @DeleteMapping("/desmarcar/{id}")
    public ResponseEntity<?> desmarcar(@PathVariable("id") int id) {
        boolean sucesso = consultaDAO.desmarcarConsulta(id);

        if (sucesso) {
            return ResponseEntity.ok("Consulta desmarcada com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao remover a consulta no banco de dados.");
        }
    }
}