package com.gestaofinanceirapessoal.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bancos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Banco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, length = 80)
    private String nome;
}
