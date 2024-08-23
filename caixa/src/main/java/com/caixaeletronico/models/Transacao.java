package com.caixaeletronico.models;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Transacao implements Serializable {	
	private static final long serialVersionUID = 1L;
	
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 private String tipo;
	 private double valor;
	 private String data;

	 @ManyToOne
	 private Conta conta;

	public Transacao(Long id, String tipo, double valor, String data, Conta conta) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.valor = valor;
		this.data = data;
		this.conta = conta;
	}
	 
	public Transacao() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	@Override
	public int hashCode() {
		return Objects.hash(conta);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transacao other = (Transacao) obj;
		return Objects.equals(conta, other.conta);
	}
	
}
