package com.gestaofinanceirapessoal.services;

import com.gestaofinanceirapessoal.api.exception.NotFoundException;
import com.gestaofinanceirapessoal.domain.dto.UsuarioDTO;
import com.gestaofinanceirapessoal.domain.model.Usuario;
import com.gestaofinanceirapessoal.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;

    @Transactional(readOnly = true)
    public Page<UsuarioDTO> list(String nomeOuEmail, Pageable pageable) {
        // Caso queira refinar, pode implementar busca por nome/email no repository
        return repository.findAll(pageable)
                .map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public UsuarioDTO findById(Long id) {
        var usuario = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        return toDTO(usuario);
    }

    @Transactional
    public UsuarioDTO create(UsuarioDTO dto) {
        var entity = toEntity(dto);
        entity = repository.save(entity);
        return toDTO(entity);
    }

    @Transactional
    public UsuarioDTO update(Long id, UsuarioDTO dto) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        entity.setNome(dto.nome());
        entity.setEmail(dto.email());
        entity = repository.save(entity);
        return toDTO(entity);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Usuário não encontrado");
        }
        repository.deleteById(id);
    }

    // ---------- Mapping ----------
    private UsuarioDTO toDTO(Usuario u) {
        return new UsuarioDTO(u.getId(), u.getNome(), u.getEmail());
    }

    private Usuario toEntity(UsuarioDTO dto) {
        return Usuario.builder()
                .nome(dto.nome())
                .email(dto.email())
                .build();
    }
}
