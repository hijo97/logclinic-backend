package org.logclinic.api.controller;

import java.util.List;

import org.logclinic.api.dao.PacienteDAO;
import org.logclinic.api.model.Paciente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pacientes")
@CrossOrigin(origins = "*") // Permite que o seu HTML acesse esta rota sem bloqueios de segurança (CORS)
public class PacienteWebController {

    private final PacienteDAO pacienteDAO = new PacienteDAO();

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody Paciente paciente) {
        // Validação básica de segurança antes de mandar pro banco
        if (paciente.getNome() == null || paciente.getNome().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O nome do paciente é obrigatório.");
        }
        if (paciente.getCpf() == null || paciente.getCpf().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O CPF do paciente é obrigatório.");
        }

        // Executa a inserção através do DAO que você acabou de criar
        boolean sucesso = pacienteDAO.cadastrarPaciente(paciente);

        if (sucesso) {
            return ResponseEntity.ok("Paciente cadastrado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar o paciente no banco de dados. Verifique os logs.");
        }
    }


    @GetMapping("/listar")
    public ResponseEntity<List<Paciente>> listarTodosPacientes() {   
        List<Paciente> pacientes = pacienteDAO.listarTodos();
        return ResponseEntity.ok(pacientes);
    }

    @DeleteMapping("/excluir/{id}")
public ResponseEntity<?> excluir(@PathVariable("id") int id) { // 
    boolean sucesso = pacienteDAO.excluirPaciente(id);

    if (sucesso) {
        return ResponseEntity.ok("Paciente excluído com sucesso!");
    } else {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao excluir o paciente no banco de dados.");
    }
}

@GetMapping("/buscar/{id}")
public ResponseEntity<?> buscarPorId(@PathVariable("id") int id) {
    Paciente paciente = pacienteDAO.buscarPorId(id);

    if (paciente != null) {
        return ResponseEntity.ok(paciente);
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Paciente não encontrado.");
    }
}

@PutMapping("/atualizar")
public ResponseEntity<?> atualizar(@RequestBody Paciente paciente) {
    boolean sucesso = pacienteDAO.atualizarPaciente(paciente);

    if (sucesso) {
        return ResponseEntity.ok("Paciente atualizado com sucesso!");
    } else {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao atualizar o paciente no banco de dados.");
    }
}


} // Fim da classe PacienteWebController
