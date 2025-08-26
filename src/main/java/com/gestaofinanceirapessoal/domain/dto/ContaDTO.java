package com.gestaofinanceirapessoal.domain.dto;

import com.gestaofinanceirapessoal.domain.enums.TipoConta;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ContaDTO(
        Long id,

        @NotBlank(message = "A descrição da conta é obrigatória.")
        @Size(max = 60, message = "Descrição deve ter no máximo 60 caracteres.")
        String descricao,

        @NotNull(message = "O banco é obrigatório.")
        Long bancoId,

        @NotNull(message = "O tipo da conta é obrigatório.")
        TipoConta tipo,

        @NotNull(message = "O saldo inicial é obrigatório.")
        @DecimalMin(value = "0.00", message = "Saldo inicial não pode ser negativo.")
        BigDecimal saldoInicial
) {}
