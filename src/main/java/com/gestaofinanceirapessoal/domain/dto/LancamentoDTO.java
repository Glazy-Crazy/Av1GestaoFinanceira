package com.gestaofinanceirapessoal.domain.dto;

import com.gestaofinanceirapessoal.domain.enums.Situacao;
import com.gestaofinanceirapessoal.domain.enums.TipoFormaPagamento;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LancamentoDTO(
        Long id,

        @NotNull(message = "A data é obrigatória.")
        LocalDate data,

        @NotNull(message = "O valor é obrigatório.")
        @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero.")
        BigDecimal valor,

        @NotBlank(message = "A descrição é obrigatória.")
        @Size(max = 120, message = "Descrição deve ter no máximo 120 caracteres.")
        String descricao,

        @NotNull(message = "A categoria é obrigatória.")
        Long categoriaId,

        @NotNull(message = "A conta é obrigatória.")
        Long contaId,

        @NotNull(message = "O tipo de lançamento é obrigatório.")
        Long tipoLancamentoId,

        @NotNull(message = "A forma de pagamento é obrigatória.")
        TipoFormaPagamento formaPagamento,

        @NotNull(message = "A situação é obrigatória.")
        Situacao situacao
) {}
