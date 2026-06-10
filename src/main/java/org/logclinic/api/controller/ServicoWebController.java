package org.logclinic.api.controller;

import java.util.List;
import org.logclinic.api.dao.ServicoDAO;
import org.logclinic.api.model.Servico;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/servicos")
@CrossOrigin(origins = "*")
public class ServicoWebController {

    private final ServicoDAO servicoDAO = new ServicoDAO();

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody Servico servico) {
        if (servico.getNome() == null || servico.getNome().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O nome do serviço é obrigatório.");
        }
        if (servico.getValor() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O valor do serviço não pode ser negativo.");
        }

        boolean sucesso = servicoDAO.cadastrarServico(servico);

        if (sucesso) {
            return ResponseEntity.ok("Serviço cadastrado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar o serviço no banco de dados.");
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Servico>> listar() {
        List<Servico> lista = servicoDAO.listarTodos();
        return ResponseEntity.ok(lista);
    }



@PutMapping("/atualizar")
    public ResponseEntity<?> atualizar(@RequestBody Servico servico) {
        if (servico.getId() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID inválido para atualização.");
        }

        boolean sucesso = servicoDAO.atualizarServico(servico);

        if (sucesso) {
            return ResponseEntity.ok("Serviço atualizado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar o serviço no banco de dados.");
        }
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<?> excluir(@PathVariable("id") int id) {
        boolean sucesso = servicoDAO.excluirServico(id);

        if (sucesso) {
            return ResponseEntity.ok("Serviço excluído com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao excluir o serviço no banco de dados.");
        }
    }
}