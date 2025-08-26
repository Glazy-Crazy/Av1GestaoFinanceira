package com.gestaofinanceirapessoal.controllers;

import com.gestaofinanceirapessoal.domain.dto.TipoLancamentoDTO;
import com.gestaofinanceirapessoal.services.TipoLancamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tipos-lancamento")
@RequiredArgsConstructor
public class TipoLancamentoController {

    private final TipoLancamentoService service;

    @GetMapping
    public ResponseEntity<Page<TipoLancamentoDTO>> list(Pageable pageable) {
        return ResponseEntity.ok(service.list(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoLancamentoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<TipoLancamentoDTO> create(@RequestBody @Validated TipoLancamentoDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoLancamentoDTO> update(@PathVariable Long id, @RequestBody @Validated TipoLancamentoDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
