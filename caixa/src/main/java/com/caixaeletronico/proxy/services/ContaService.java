package com.caixaeletronico.proxy.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caixaeletronico.factories.UsuarioContaFactory;
import com.caixaeletronico.models.Conta;
import com.caixaeletronico.models.Transacao;
import com.caixaeletronico.repositories.ContaRepository;
import com.caixaeletronico.repositories.TransacaoRepository;
import java.time.format.DateTimeFormatter;


@Service
public class ContaService implements IContaService {
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Autowired
    private ContaRepository contaRepository;
    
    @Autowired
    private TransacaoRepository transacaoRepository;
    
    @Override
    public boolean autenticar(Long accountId, String senha ) {
        Optional<Conta> contaOpt = getContaById(accountId);
        Conta conta = contaOpt.get();    

        if(conta.getSenha().equals(senha)){
            return true;
        }        
        return false;
    }
    
    @Override
    public Conta criarContaComUsuario(UsuarioContaFactory factory, String senha, String titular) {
        Conta conta = factory.criarConta();
       //Usuario usuario = factory.criarUsuario();
        conta.setTitular(titular);   
        conta.setSenha(senha);        
        return contaRepository.save(conta);
    }
    
    @Override
    public Optional<Conta> getContaById(Long id) {
        return contaRepository.findById(id);
    }
    
    @Override
    public List<Conta> getTodasContas() {
        return contaRepository.findAll();
    }
        
    @Override
    public Conta depositar(Long contaId, double valor, boolean depositoViaTransferencia) {                
        Conta conta = contaRepository.findById(contaId).orElseThrow();
        conta.setSaldo(conta.getSaldo() + valor);
        contaRepository.save(conta);

        Transacao transacao = new Transacao();
        transacao.setTipo(depositoViaTransferencia ? "TRANSFERÊNCIA" : "DEPÓSITO");
        transacao.setValor(valor);
        transacao.setData(LocalDateTime.now().format(formatter));
        transacao.setConta(conta);
        transacaoRepository.save(transacao);

        return conta;
    }
    
    @Override
    public Conta sacar(Long contaId, double valor) {
        Conta conta = contaRepository.findById(contaId).orElseThrow();
        if (conta.getSaldo() < valor) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
        conta.setSaldo(conta.getSaldo() - valor);
        contaRepository.save(conta);

        Transacao transacao = new Transacao();
        transacao.setTipo("SAQUE");
        transacao.setValor(valor);
        transacao.setData(LocalDateTime.now().format(formatter));
        transacao.setConta(conta);
        transacaoRepository.save(transacao);

        return conta;
    }
    
    @Override
    public Conta transferir(Long contaOrigemId, Long contaDestinoId, double valor) {
        Conta contaOrigem = contaRepository.findById(contaOrigemId).orElseThrow();
        contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);
        depositar(contaDestinoId, valor, true);
        contaRepository.save(contaOrigem);

        Transacao transacao = new Transacao();
        transacao.setTipo("TRANSFERÊNCIA");
        transacao.setValor(valor);
        transacao.setData(LocalDateTime.now().format(formatter));
        transacao.setConta(contaOrigem);
        transacaoRepository.save(transacao);

        return contaOrigem;
    }
    
    @Override
    public List<Transacao> emitirExtrato(Long contaId) {
        return transacaoRepository.findByContaId(contaId);
    }
}
