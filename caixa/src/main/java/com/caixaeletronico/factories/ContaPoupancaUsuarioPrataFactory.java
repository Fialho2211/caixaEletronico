package com.caixaeletronico.factories;

import com.caixaeletronico.models.Conta;
import com.caixaeletronico.models.ContaPoupanca;
import com.caixaeletronico.models.Usuario;
import com.caixaeletronico.models.UsuarioPrata;

public class ContaPoupancaUsuarioPrataFactory implements UsuarioContaFactory {

	@Override
	public Conta criarConta() {
		return new ContaPoupanca();
	}

	@Override
	public Usuario criarUsuario() {
		return new UsuarioPrata();
	}

}
