package com.gestaofinanceirapessoal.services;

import com.gestaofinanceirapessoal.api.exception.NotFoundException;
import com.gestaofinanceirapessoal.domain.dto.BancoDTO;
import com.gestaofinanceirapessoal.domain.model.Banco;
import com.gestaofinanceirapessoal.repositories.BancoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BancoService {

    private final BancoRepository repository;

    public Page<BancoDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(b -> new BancoDTO(b.getId(), b.getNome()));
    }

    public BancoDTO findById(Long id) {
        Banco banco = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Banco não encontrado"));
        return new BancoDTO(banco.getId(), banco.getNome());
    }

    public BancoDTO create(BancoDTO dto) {
        Banco banco = new Banco(null, dto.nome());
        banco = repository.save(banco);
        return new BancoDTO(banco.getId(), banco.getNome());
    }

    public BancoDTO update(Long id, BancoDTO dto) {
        Banco banco = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Banco não encontrado"));
        banco.setNome(dto.nome());
        banco = repository.save(banco);
        return new BancoDTO(banco.getId(), banco.getNome());
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Banco não encontrado");
        }
        repository.deleteById(id);
    }
}
