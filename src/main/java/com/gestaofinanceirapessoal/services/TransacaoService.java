package com.gestaofinanceirapessoal.services;

import com.gestaofinanceirapessoal.api.exception.NotFoundException;
import com.gestaofinanceirapessoal.domain.dto.FiltroLancamentoDTO;
import com.gestaofinanceirapessoal.domain.dto.TransacaoDTO;
import com.gestaofinanceirapessoal.domain.model.Categoria;
import com.gestaofinanceirapessoal.domain.model.Conta;
import com.gestaofinanceirapessoal.domain.model.TipoLancamento;
import com.gestaofinanceirapessoal.domain.model.Transacao;
import com.gestaofinanceirapessoal.repositories.CategoriaRepository;
import com.gestaofinanceirapessoal.repositories.ContaRepository;
import com.gestaofinanceirapessoal.repositories.TipoLancamentoRepository;
import com.gestaofinanceirapessoal.repositories.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository repository;
    private final CategoriaRepository categoriaRepository;
    private final ContaRepository contaRepository;
    private final TipoLancamentoRepository tipoLancamentoRepository;

    public Page<TransacaoDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(this::toDTO);
    }

    public Page<TransacaoDTO> search(FiltroLancamentoDTO filtro, Pageable pageable) {
        Specification<Transacao> spec = buildSpec(filtro);
        return repository.findAll(spec, pageable).map(this::toDTO);
    }

    public TransacaoDTO findById(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Transação não encontrada"));
        return toDTO(entity);
    }

    public TransacaoDTO create(TransacaoDTO dto) {
        var entity = toEntity(dto);
        entity = repository.save(entity);
        return toDTO(entity);
    }

    public TransacaoDTO update(Long id, TransacaoDTO dto) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Transação não encontrada"));
        apply(entity, dto);
        entity = repository.save(entity);
        return toDTO(entity);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) throw new NotFoundException("Transação não encontrada");
        repository.deleteById(id);
    }

    // ---------- Mapping ----------
    private TransacaoDTO toDTO(Transacao t) {
        return new TransacaoDTO(
                t.getId(),
                t.getData(),
                t.getValor(),
                t.getDescricao(),
                t.getTipoLancamento().getId(),
                t.getCategoria().getId(),
                t.getConta().getId(),
                t.getFormaPagamento()
        );
    }

    private Transacao toEntity(TransacaoDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada"));
        Conta conta = contaRepository.findById(dto.contaId())
                .orElseThrow(() -> new NotFoundException("Conta não encontrada"));
        TipoLancamento tipo = tipoLancamentoRepository.findById(dto.tipoLancamentoId())
                .orElseThrow(() -> new NotFoundException("Tipo de lançamento não encontrado"));

        return Transacao.builder()
                .data(dto.data())
                .valor(dto.valor())
                .descricao(dto.descricao())
                .categoria(categoria)
                .conta(conta)
                .tipoLancamento(tipo)
                .formaPagamento(dto.formaPagamento())
                .build();
    }

    private void apply(Transacao entity, TransacaoDTO dto) {
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
    }

    // ---------- Specification ----------
    private Specification<Transacao> buildSpec(FiltroLancamentoDTO f) {
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

            if (f.situacao() != null) {
                // Transacao não tem campo situacao → se quiser, adicione no model
            }

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
