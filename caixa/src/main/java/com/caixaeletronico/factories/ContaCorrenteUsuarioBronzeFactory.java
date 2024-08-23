package com.caixaeletronico.factories;

import com.caixaeletronico.models.Conta;
import com.caixaeletronico.models.ContaCorrente;
import com.caixaeletronico.models.Usuario;
import com.caixaeletronico.models.UsuarioBronze;

public class ContaCorrenteUsuarioBronzeFactory implements UsuarioContaFactory {

	@Override
	public Conta criarConta() {
		return new ContaCorrente();
	}

	@Override
	public Usuario criarUsuario() {
		return new UsuarioBronze();
	}

}
