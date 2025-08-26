package com.gestaofinanceirapessoal.repositories;

import com.gestaofinanceirapessoal.domain.model.TipoLancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoLancamentoRepository extends JpaRepository<TipoLancamento, Long> {
}
