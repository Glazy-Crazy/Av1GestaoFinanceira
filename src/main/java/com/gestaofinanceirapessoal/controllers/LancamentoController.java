package com.gestaofinanceirapessoal.controllers;

import com.gestaofinanceirapessoal.domain.dto.FiltroLancamentoDTO;
import com.gestaofinanceirapessoal.domain.dto.LancamentoDTO;
import com.gestaofinanceirapessoal.services.LancamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lancamentos")
@RequiredArgsConstructor
public class LancamentoController {

    private final LancamentoService service;

    @GetMapping
    public ResponseEntity<Page<LancamentoDTO>> list(Pageable pageable) {
        return ResponseEntity.ok(service.list(pageable));
    }

    @PostMapping("/search")
    public ResponseEntity<Page<LancamentoDTO>> search(@RequestBody FiltroLancamentoDTO filtro,
                                                      Pageable pageable) {
        return ResponseEntity.ok(service.search(filtro, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LancamentoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<LancamentoDTO> create(@RequestBody @Validated LancamentoDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LancamentoDTO> update(@PathVariable Long id, @RequestBody @Validated LancamentoDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        ret
