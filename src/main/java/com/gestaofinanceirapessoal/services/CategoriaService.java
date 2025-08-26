package com.gestaofinanceirapessoal.services;

import com.gestaofinanceirapessoal.api.exception.NotFoundException;
import com.gestaofinanceirapessoal.domain.dto.CategoriaDTO;
import com.gestaofinanceirapessoal.domain.model.Categoria;
import com.gestaofinanceirapessoal.repositories.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository repository;

    public Page<CategoriaDTO> list(String nome, Pageable pageable) {
        Page<Categoria> categorias = repository.findAll(pageable);
        return categorias.map(c -> new CategoriaDTO(c.getId(), c.getNome(), c.getDescricao()));
    }

    public CategoriaDTO findById(Long id) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada"));
        return new CategoriaDTO(categoria.getId(), categoria.getNome(), categoria.getDescricao());
    }

    public CategoriaDTO create(CategoriaDTO dto) {
        Categoria categoria = new Categoria(null, dto.nome(), dto.descricao());
        categoria = repository.save(categoria);
        return new CategoriaDTO(categoria.getId(), categoria.getNome(), categoria.getDescricao());
    }

    public CategoriaDTO update(Long id, CategoriaDTO dto) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada"));
        categoria.setNome(dto.nome());
        categoria.setDescricao(dto.descricao());
        categoria = repository.save(categoria);
        return new CategoriaDTO(categoria.getId(), categoria.getNome(), categoria.getDescricao());
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Categoria não encontrada");
        }
        repository.deleteById(id);
    }
}
