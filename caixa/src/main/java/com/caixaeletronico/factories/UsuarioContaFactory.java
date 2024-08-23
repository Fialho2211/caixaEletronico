package com.caixaeletronico.factories;

import com.caixaeletronico.models.Conta;
import com.caixaeletronico.models.Usuario;

public interface UsuarioContaFactory {
	
	Conta criarConta();
    Usuario criarUsuario();

}
