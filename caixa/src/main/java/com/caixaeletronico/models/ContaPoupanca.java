package com.caixaeletronico.models;

import jakarta.persistence.Entity;

@Entity
public class ContaPoupanca extends Conta {

	@Override
    public void exibirTipoConta() {
        System.out.println("Conta Poupança");
        
    }

}
