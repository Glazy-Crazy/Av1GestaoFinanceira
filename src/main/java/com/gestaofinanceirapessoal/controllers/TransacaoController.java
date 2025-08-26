package com.gestaofinanceirapessoal.controllers;

import com.gestaofinanceirapessoal.domain.dto.FiltroLancamentoDTO;
import com.gestaofinanceirapessoal.domain.dto.TransacaoDTO;
import com.gestaofinanceirapessoal.services.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transacoes")
@RequiredArgsConstructor
public class TransacaoController {

    private final TransacaoService service;

    @GetMapping
    public ResponseEntity<Page<TransacaoDTO>> list(Pageable pageable) {
        return ResponseEntity.ok(service.list(pageable));
    }

    @PostMapping("/search")
    public ResponseEntity<Page<TransacaoDTO>> search(@RequestBody FiltroLancamentoDTO filtro,
                                                     Pageable pageable) {
        return ResponseEntity.ok(service.search(filtro, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransacaoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<TransacaoDTO> create(@RequestBody @Validated TransacaoDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransacaoDTO> update(@PathVariable Long id, @RequestBody @Validated TransacaoDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
