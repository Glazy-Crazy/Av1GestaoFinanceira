package com.gestaofinanceirapessoal.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tipos_lancamento")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TipoLancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, length = 40)
    private String descricao;

    @Column(nullable = false)
    private Boolean ativo = Boolean.TRUE;
}
