package com.caixaeletronico.proxy.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.caixaeletronico.factories.UsuarioContaFactory;
import com.caixaeletronico.models.Conta;
import com.caixaeletronico.models.Transacao;

@Service
public class ContaServiceProxy implements IContaService {

    private final ContaService contaService;

    public ContaServiceProxy(ContaService contaService) {
        this.contaService = contaService;
    }

    @Override
    public boolean autenticar(Long accountId, String senha) {
        if(accountId == null || senha == null){
            return false;
        }

        return contaService.autenticar(accountId, senha);
    }

    @Override
    public Conta criarContaComUsuario(UsuarioContaFactory factory, String senha, String titular) {
        return contaService.criarContaComUsuario(factory, senha, titular);
    }

    @Override
    public Optional<Conta> getContaById(Long id) {
        return contaService.getContaById(id);
    }

    @Override
    public List<Conta> getTodasContas() {
        return contaService.getTodasContas();
    }

    @Override
    public Conta depositar(Long contaId, double valor, boolean depositoViaTransferencia) {
        return contaService.depositar(contaId, valor, depositoViaTransferencia);
    }

    @Override
    public Conta sacar(Long contaId, double valor) {
        return contaService.sacar(contaId, valor);
    }

    @Override
    public Conta transferir(Long contaOrigemId, Long contaDestinoId, double valor) {
        return contaService.transferir(contaOrigemId, contaDestinoId, valor);
    }

    @Override
    public List<Transacao> emitirExtrato(Long contaId) {
        return contaService.emitirExtrato(contaId);
    }
}
