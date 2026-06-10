package org.logclinic.api.controller;

import java.util.List;

import org.logclinic.api.dao.MedicoDAO;
import org.logclinic.api.model.Medico;  
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/*import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;*/

@RestController
@RequestMapping("/api/medicos")
@CrossOrigin(origins = "*") // Permite que o seu HTML acesse esta rota sem bloqueios de segurança (CORS)
public class MedicoWebController {

    private final MedicoDAO medicoDAO = new MedicoDAO();

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody Medico medico) {
        // Validação básica de segurança antes de mandar pro banco
        if (medico.getNome() == null || medico.getNome().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O nome do médico é obrigatório.");
        }
        if (medico.getCrm() == null || medico.getCrm().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O CRM do médico é obrigatório.");
        }

        // Executa a inserção através do DAO
        boolean sucesso = medicoDAO.cadastrarMedico(medico);

        if (sucesso) {
            return ResponseEntity.ok("Médico cadastrado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar o médico no banco de dados. Verifique os logs.");
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Medico>> listarTodosMedicos() {   
        List<Medico> medicos = medicoDAO.listarTodos();
        return ResponseEntity.ok(medicos);
    }   

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable("id") int id) {
        Medico medico = medicoDAO.buscarPorId(id);
        if (medico != null) {
            return ResponseEntity.ok(medico);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Médico não encontrado.");
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") int id, @RequestBody Medico medico) {
        // Validação básica de segurança
        if (medico.getNome() == null || medico.getNome().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O nome do médico é obrigatório.");
        }
        if (medico.getCrm() == null || medico.getCrm().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O CRM do médico é obrigatório.");
        }

        // Injeta o ID vindo da URL (da rota) para dentro do objeto antes de enviar ao DAO
        medico.setId(id);
        
        boolean sucesso = medicoDAO.atualizarMedico(medico);

        if (sucesso) {
            return ResponseEntity.ok("Médico atualizado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar o médico no banco de dados. Verifique os logs.");
        }
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<?> excluir(@PathVariable("id") int id) {
        boolean sucesso = medicoDAO.excluirMedico(id);

        if (sucesso) {
            return ResponseEntity.ok("Médico excluído com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao excluir o médico do banco de dados. Verifique os logs.");
        }
    }
}