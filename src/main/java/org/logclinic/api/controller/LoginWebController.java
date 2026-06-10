package org.logclinic.api.controller;

import java.util.Map;

import org.logclinic.api.dao.OperadorDAO;
import org.logclinic.api.model.Operador;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Permite que o seu HTML acesse o Java sem bloqueios de segurança
public class LoginWebController {

    private final OperadorDAO operadorDAO = new OperadorDAO();

    @PostMapping("/login")
    public ResponseEntity<?> efetuarLogin(@RequestBody Map<String, String> dados) {
        // Validação básica de segurança para evitar NullPointerException
        if (dados == null || !dados.containsKey("login") || !dados.containsKey("senha")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parâmetros de login e senha são obrigatórios.");
        }

        String login = dados.get("login");
        String senha = dados.get("senha");

        // Valida se os campos não foram enviados vazios
        if (login == null || login.trim().isEmpty() || senha == null || senha.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login e senha não podem estar em branco.");
        }

        // Validação usando o método que acabamos de criar no DAO
        Operador operadorLogado = operadorDAO.realizarLogin(login, senha);

        if (operadorLogado != null) {
            // Se der certo, devolve os dados do usuário para o frontend
            return ResponseEntity.ok(operadorLogado);
        } else {
            // Se der errado, devolve um erro de não autorizado
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha incorretos.");
        }
    }
}