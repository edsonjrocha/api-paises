package br.com.ejrocha.api.controller;

import br.com.ejrocha.api.service.PaisService;
import br.com.ejrocha.api.dto.PaisDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paises")
public class PaisController {

    @Autowired
    private PaisService paisService;

    @GetMapping
    public List<PaisDTO> listarTodos() {
        return paisService.listarTodos();
    }

    @GetMapping("/listar-por-populacao")
    public List<PaisDTO> listarPorPopulacao() {
        return paisService.listarPorPopulacao();
    }

    @GetMapping("/listar-por-area")
    public List<PaisDTO> listarPorArea() {
        return paisService.listarPorArea();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaisDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(paisService.buscarPorId(id));
    }

    @GetMapping("/codigo/{codigoIso}")
    public ResponseEntity<PaisDTO> buscarPorCodigoIso(@PathVariable String codigoIso) {
        PaisDTO pais = paisService.buscarPorCodigoIso(codigoIso);
        return ResponseEntity.ok(pais);
    }

    @PostMapping
    public ResponseEntity<PaisDTO> criar(@Valid @RequestBody PaisDTO paisDTO) {
        PaisDTO novoPais = paisService.salvar(paisDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPais);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaisDTO> atualizar(@PathVariable Long id, @Valid @RequestBody PaisDTO paisDTO) {
        PaisDTO paisAtualizado = paisService.atualizar(id, paisDTO);
        return ResponseEntity.ok(paisAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        paisService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // Listar todos os países externos
    @GetMapping("/externos")
    public List<PaisDTO> listarPaisesExternos() {
        return paisService.listarPaisesExternos();
    }

    // Buscar país externo por código ISO
    @GetMapping("/externos/{codigoIso}")
    public ResponseEntity<PaisDTO> buscarPaisExternoPorCodigoIso(@PathVariable String codigoIso) {
        PaisDTO pais = paisService.buscarPaisExternoPorCodigoIso(codigoIso);
        return pais != null ? ResponseEntity.ok(pais) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping("/sincronizar")
    public ResponseEntity<String> sincronizarPaisesExternos() {
        try {
            int count = paisService.sincronizarPaisesExternos();
            return ResponseEntity.ok("Sincronização concluída com sucesso! " + count + " países foram adicionados.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao sincronizar países: " + e.getMessage());
        }
    }

}
