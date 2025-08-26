package com.gestaofinanceirapessoal.services;

import com.gestaofinanceirapessoal.api.exception.NotFoundException;
import com.gestaofinanceirapessoal.domain.dto.FiltroLancamentoDTO;
import com.gestaofinanceirapessoal.domain.dto.LancamentoDTO;
import com.gestaofinanceirapessoal.domain.model.Categoria;
import com.gestaofinanceirapessoal.domain.model.Conta;
import com.gestaofinanceirapessoal.domain.model.Lancamento;
import com.gestaofinanceirapessoal.domain.model.TipoLancamento;
import com.gestaofinanceirapessoal.repositories.CategoriaRepository;
import com.gestaofinanceirapessoal.repositories.ContaRepository;
import com.gestaofinanceirapessoal.repositories.LancamentoRepository;
import com.gestaofinanceirapessoal.repositories.TipoLancamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LancamentoService {

    private final LancamentoRepository repository;
    private final CategoriaRepository categoriaRepository;
    private final ContaRepository contaRepository;
    private final TipoLancamentoRepository tipoLancamentoRepository;

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> search(FiltroLancamentoDTO filtro, Pageable pageable) {
        Specification<Lancamento> spec = buildSpec(filtro);
        return repository.findAll(spec, pageable).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public LancamentoDTO findById(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Lançamento não encontrado"));
        return toDTO(entity);
    }

    @Transactional
    public LancamentoDTO create(LancamentoDTO dto) {
        var entity = toEntity(dto);
        entity = repository.save(entity);
        return toDTO(entity);
    }

    @Transactional
    public LancamentoDTO update(Long id, LancamentoDTO dto) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Lançamento não encontrado"));
        apply(entity, dto);
        entity = repository.save(entity);
        return toDTO(entity);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Lançamento não encontrado");
        }
        repository.deleteById(id);
    }

    // ---------- mapping ----------
    private LancamentoDTO toDTO(Lancamento l) {
        return new LancamentoDTO(
                l.getId(),
                l.getData(),
                l.getValor(),
                l.getDescricao(),
                l.getCategoria().getId(),
                l.getConta().getId(),
                l.getTipoLancamento().getId(),
                l.getFormaPagamento(),
                l.getSituacao()
        );
    }

    private Lancamento toEntity(LancamentoDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada"));
        Conta conta = contaRepository.findById(dto.contaId())
                .orElseThrow(() -> new NotFoundException("Conta não encontrada"));
        TipoLancamento tipo = tipoLancamentoRepository.findById(dto.tipoLancamentoId())
                .orElseThrow(() -> new NotFoundException("Tipo de lançamento não encontrado"));

        return Lancamento.builder()
                .data(dto.data())
                .valor(dto.valor())
                .descricao(dto.descricao())
                .categoria(categoria)
                .conta(conta)
                .tipoLancamento(tipo)
                .formaPagamento(dto.formaPagamento())
                .situacao(dto.situacao())
                .build();
    }

    private void apply(Lancamento entity, LancamentoDTO dto) {
        if (!Objects.equals(entity.getCategoria().getId(), dto.categoriaId())) {
            entity.setCategoria(categoriaRepository.findById(dto.categoriaId())
                    .orElseThrow(() -> new NotFoundException("Categoria não encontrada")));
        }
        if (!Objects.equals(entity.getConta().getId(), dto.contaId())) {
            entity.setConta(contaRepository.findById(dto.contaId())
                    .orElseThrow(() -> new NotFoundException("Conta não encontrada")));
        }
        if (!Objects.equals(entity.getTipoLancamento().getId(), dto.tipoLancamentoId())) {
            entity.setTipoLancamento(tipoLancamentoRepository.findById(dto.tipoLancamentoId())
                    .orElseThrow(() -> new NotFoundException("Tipo de lançamento não encontrado")));
        }
        entity.setData(dto.data());
        entity.setValor(dto.valor());
        entity.setDescricao(dto.descricao());
        entity.setFormaPagamento(dto.formaPagamento());
        entity.setSituacao(dto.situacao());
    }

    // ---------- specification ----------
    private Specification<Lancamento> buildSpec(FiltroLancamentoDTO f) {
        return (root, q, cb) -> {
            var p = cb.conjunction();

            if (f.dataInicio() != null)
                p.getExpressions().add(cb.greaterThanOrEqualTo(root.get("data"), f.dataInicio()));

            if (f.dataFim() != null)
                p.getExpressions().add(cb.lessThanOrEqualTo(root.get("data"), f.dataFim()));

            if (f.categoriaId() != null)
                p.getExpressions().add(cb.equal(root.get("categoria").get("id"), f.categoriaId()));

            if (f.contaId() != null)
                p.getExpressions().add(cb.equal(root.get("conta").get("id"), f.contaId()));

            if (f.tipoLancamentoId() != null)
                p.getExpressions().add(cb.equal(root.get("tipoLancamento").get("id"), f.tipoLancamentoId()));

            if (f.situacao() != null)
                p.getExpressions().add(cb.equal(root.get("situacao"), f.situacao()));

            if (f.valorMin() != null)
                p.getExpressions().add(cb.greaterThanOrEqualTo(root.get("valor"), f.valorMin()));

            if (f.valorMax() != null)
                p.getExpressions().add(cb.lessThanOrEqualTo(root.get("valor"), f.valorMax()));

            if (f.descricao() != null && !f.descricao().isBlank())
                p.getExpressions().add(cb.like(cb.lower(root.get("descricao")), "%" + f.descricao().toLowerCase() + "%"));

            return p;
        };
    }
}
