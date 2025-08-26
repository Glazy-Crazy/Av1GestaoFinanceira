package com.gestaofinanceirapessoal.services;

import com.gestaofinanceirapessoal.api.exception.NotFoundException;
import com.gestaofinanceirapessoal.domain.dto.ContaDTO;
import com.gestaofinanceirapessoal.domain.model.Banco;
import com.gestaofinanceirapessoal.domain.model.Conta;
import com.gestaofinanceirapessoal.repositories.BancoRepository;
import com.gestaofinanceirapessoal.repositories.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContaService {

    private final ContaRepository contaRepository;
    private final BancoRepository bancoRepository;

    public Page<ContaDTO> list(String descricao, Long bancoId, Pageable pageable) {
        Page<Conta> contas = contaRepository.findAll(pageable); // pode filtrar depois
        return contas.map(c -> new ContaDTO(c.getId(), c.getDescricao(), c.getBanco().getId(), c.getTipo(), c.getSaldoInicial()));
    }

    public ContaDTO findById(Long id) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Conta não encontrada"));
        return new ContaDTO(conta.getId(), conta.getDescricao(), conta.getBanco().getId(), conta.getTipo(), conta.getSaldoInicial());
    }

    public ContaDTO create(ContaDTO dto) {
        Banco banco = bancoRepository.findById(dto.bancoId())
                .orElseThrow(() -> new NotFoundException("Banco não encontrado"));
        Conta conta = Conta.builder()
                .descricao(dto.descricao())
                .banco(banco)
                .tipo(dto.tipo())
                .saldoInicial(dto.saldoInicial())
                .build();
        conta = contaRepository.save(conta);
        return new ContaDTO(conta.getId(), conta.getDescricao(), conta.getBanco().getId(), conta.getTipo(), conta.getSaldoInicial());
    }

    public ContaDTO update(Long id, ContaDTO dto) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Conta não encontrada"));
        Banco banco = bancoRepository.findById(dto.bancoId())
                .orElseThrow(() -> new NotFoundException("Banco não encontrado"));

        conta.setDescricao(dto.descricao());
        conta.setBanco(banco);
        conta.setTipo(dto.tipo());
        conta.setSaldoInicial(dto.saldoInicial());

        conta = contaRepository.save(conta);
        return new ContaDTO(conta.getId(), conta.getDescricao(), conta.getBanco().getId(), conta.getTipo(), conta.getSaldoInicial());
    }

    public void delete(Long id) {
        if (!contaRepository.existsById(id)) {
            throw new NotFoundException("Conta não encontrada");
        }
        contaRepository.deleteById(id);
    }
}
