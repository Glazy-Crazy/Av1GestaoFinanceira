package com.gestaofinanceirapessoal.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TipoLancamentoDTO(
        Long id,

        @NotBlank(message = "A descrição do tipo de lançamento é obrigatória.")
        @Size(max = 40, message = "Descrição deve ter no máximo 40 caracteres.")
        String descricao,

        // opcional: controle de exibição/uso
        Boolean ativo
) {}
