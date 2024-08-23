package com.caixaeletronico.chainOfResponsability;

public class SenhaHandler implements IHandler {
    private IHandler next;

    @Override
    public void setNext(IHandler next) {
        this.next = next;
    }
    @Override
    public boolean handle(String titular, String senha) {
        if (senha != null && senha.length() >= 4 && !senha.equals("")) {
           return true;
        }
        if (next != null) {
            next.handle(titular, senha);
        }

        return false;
    }    
}
