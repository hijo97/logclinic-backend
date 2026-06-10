package org.logclinic.api.controller;

import java.util.List;

import org.logclinic.api.dao.ConvenioDAO;
import org.logclinic.api.model.Convenio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; // Importa o @PostMapping e @RequestBody
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/convenios")
@CrossOrigin(origins = "*")
public class ConvenioWebController {

    private final ConvenioDAO convenioDAO = new ConvenioDAO();

    @GetMapping("/listar")
    public ResponseEntity<List<Convenio>> listar() {
        List<Convenio> lista = convenioDAO.listarTodos();
        return ResponseEntity.ok(lista);
    }

    // ADICIONE ESTA ROTA PARA CASAR COM O SEU SCRIPT JAVASCRIPT
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody Convenio convenio) {
        if (convenio.getNome() == null || convenio.getNome().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O nome do convênio é obrigatório.");
        }

        boolean sucesso = convenioDAO.cadastrarConvenio(convenio);

        if (sucesso) {
            return ResponseEntity.ok("Convênio cadastrado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar o convênio no banco de dados.");
        }
    }
}