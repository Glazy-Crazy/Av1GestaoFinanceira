package com.gestaofinanceirapessoal.domain.dto;

import com.gestaofinanceirapessoal.domain.enums.Situacao;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FiltroLancamentoDTO(
        LocalDate dataInicio,
        LocalDate dataFim,
        Long categoriaId,
        Long contaId,
        Long tipoLancamentoId,
        Situacao situacao,

        @DecimalMin(value = "0.00", message = "Valor mínimo não pode ser negativo.")
        BigDecimal valorMin,

        @DecimalMin(value = "0.00", message = "Valor máximo não pode ser negativo.")
        BigDecimal valorMax,

        @Size(max = 120, message = "Descrição deve ter no máximo 120 caracteres.")
        String descricao
) {}
