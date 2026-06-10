package org.logclinic.api.controller;

import java.util.List;
import org.logclinic.api.dao.OperadorDAO;
import org.logclinic.api.model.Operador;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/operador")
@CrossOrigin(origins = "*")
public class OperadorWebController {

    private final OperadorDAO operadorDAO = new OperadorDAO();

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody Operador operador) {
        if (operador.getNome() == null || operador.getNome().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O nome do operador é obrigatório.");
        }
       if (operador.getCpf() == null || operador.getCpf().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O CPF do operador é obrigatório.");
        }
        if (operador.getLogin() == null || operador.getLogin().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O Login do operador é obrigatório.");
        }
        if (operador.getSenha() == null || operador.getSenha().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O Senha do operador é obrigatório.");
        }
        if (operador.getSetor() == null || operador.getSetor().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O setor do operador é obrigatório.");
        }
        if (operador.getPerfil() == null || operador.getPerfil().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O perfil do operador é obrigatório.");
        }

        boolean sucesso = operadorDAO.cadastrarOperador(operador);

        if (sucesso) {
            return ResponseEntity.ok("Usuário cadastrado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar o usuário no banco de dados.");
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Operador>> listar() {
        List<Operador> lista = operadorDAO.listarTodos();
        return ResponseEntity.ok(lista);
    }



@PutMapping("/atualizar")
    public ResponseEntity<?> atualizar(@RequestBody Operador operador) {
        if (operador.getId() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID inválido para atualização.");
        }

        boolean sucesso = operadorDAO.atualizarOperador(operador);

        if (sucesso) {
            return ResponseEntity.ok("Operador atualizado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar o serviço no banco de dados.");
        }
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<?> excluir(@PathVariable("id") int id) {
        boolean sucesso = operadorDAO.excluirOperador(id);

        if (sucesso) {
            return ResponseEntity.ok("Operador excluído com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao excluir o serviço no banco de dados.");
        }
    }
}