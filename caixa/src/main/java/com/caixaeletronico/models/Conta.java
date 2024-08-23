package com.caixaeletronico.models;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Conta {
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titular;
	private String senha;
    private double saldo;
	public Long getId() {
		return id;
	}
	
	public Conta() {}	
		
	public Conta(Long id, String titular, String senha, double saldo) {
		super();
		this.id = id;
		this.titular = titular;
		this.senha = senha;
		this.saldo = saldo;
	}
	
	public abstract void exibirTipoConta();

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitular() {
		return titular;
	}


	public String getSenha(){
		return senha;
	}
	
	public void setTitular(String titular) {
		this.titular = titular;
	}

	public void setSenha(String senha) {
        this.senha = senha;
    }
	
	public double getSaldo() {
		return saldo;
	}
	
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Conta other = (Conta) obj;
		return Objects.equals(id, other.id);
	}
	   
}
