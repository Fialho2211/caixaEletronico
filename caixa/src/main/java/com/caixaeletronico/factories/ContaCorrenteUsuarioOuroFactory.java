package com.caixaeletronico.factories;

import com.caixaeletronico.models.Conta;
import com.caixaeletronico.models.ContaCorrente;
import com.caixaeletronico.models.Usuario;
import com.caixaeletronico.models.UsuarioOuro;

public class ContaCorrenteUsuarioOuroFactory implements UsuarioContaFactory {

	@Override
	public Conta criarConta() {
		return new ContaCorrente();
	}

	@Override
	public Usuario criarUsuario() {
		return new UsuarioOuro();
	}

}
