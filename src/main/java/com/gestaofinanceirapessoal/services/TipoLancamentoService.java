package com.gestaofinanceirapessoal.services;

import com.gestaofinanceirapessoal.api.exception.NotFoundException;
import com.gestaofinanceirapessoal.domain.dto.TipoLancamentoDTO;
import com.gestaofinanceirapessoal.domain.model.TipoLancamento;
import com.gestaofinanceirapessoal.repositories.TipoLancamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TipoLancamentoService {

    private final TipoLancamentoRepository repository;

    @Transactional(readOnly = true)
    public Page<TipoLancamentoDTO> list(Pageable pageable) {
        return repository.findAll(pageable)
                .map(t -> new TipoLancamentoDTO(t.getId(), t.getDescricao(), t.getAtivo()));
    }

    @Transactional(readOnly = true)
    public TipoLancamentoDTO findById(Long id) {
        var tipo = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tipo de lançamento não encontrado"));
        return new TipoLancamentoDTO(tipo.getId(), tipo.getDescricao(), tipo.getAtivo());
    }

    @Transactional
    public TipoLancamentoDTO create(TipoLancamentoDTO dto) {
        var entity = TipoLancamento.builder()
                .descricao(dto.descricao())
                .ativo(dto.ativo() == null ? Boolean.TRUE : dto.ativo())
                .build();
        entity = repository.save(entity);
        return new TipoLancamentoDTO(entity.getId(), entity.getDescricao(), entity.getAtivo());
    }

    @Transactional
    public TipoLancamentoDTO update(Long id, TipoLancamentoDTO dto) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tipo de lançamento não encontrado"));
        entity.setDescricao(dto.descricao());
        if (dto.ativo() != null) entity.setAtivo(dto.ativo());
        entity = repository.save(entity);
        return new TipoLancamentoDTO(entity.getId(), entity.getDescricao(), entity.getAtivo());
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) throw new NotFoundException("Tipo de lançamento não encontrado");
        repository.deleteById(id);
    }
}
