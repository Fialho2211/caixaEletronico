package com.caixaeletronico.proxy.services;

import java.util.List;
import java.util.Optional;

import com.caixaeletronico.factories.UsuarioContaFactory;
import com.caixaeletronico.models.Conta;
import com.caixaeletronico.models.Transacao;

public interface IContaService {
    boolean autenticar(Long accountId, String senha );
    Conta criarContaComUsuario(UsuarioContaFactory factory, String senha, String titular);
    Optional<Conta> getContaById(Long id);
    List<Conta> getTodasContas();
    Conta depositar(Long contaId, double valor, boolean depositoViaTransferencia);
    Conta sacar(Long contaId, double valor);
    Conta transferir(Long contaOrigemId, Long contaDestinoId, double valor);
    List<Transacao> emitirExtrato(Long contaId);
}
