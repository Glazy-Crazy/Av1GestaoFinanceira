package com.gestaofinanceirapessoal.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaDTO(
        Long id,

        @NotBlank(message = "O nome da categoria é obrigatório.")
        @Size(max = 60, message = "Nome da categoria deve ter no máximo 60 caracteres.")
        String nome,

        @Size(max = 120, message = "Descrição deve ter no máximo 120 caracteres.")
        String descricao
) {}
