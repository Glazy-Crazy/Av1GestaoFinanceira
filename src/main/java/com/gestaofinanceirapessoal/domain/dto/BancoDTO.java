package com.gestaofinanceirapessoal.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BancoDTO(
        Long id,

        @NotBlank(message = "O nome do banco é obrigatório.")
        @Size(max = 80, message = "Nome do banco deve ter no máximo 80 caracteres.")
        String nome
) {}
