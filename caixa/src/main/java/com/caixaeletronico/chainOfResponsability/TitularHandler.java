package com.caixaeletronico.chainOfResponsability;

public class TitularHandler implements IHandler{
    private IHandler next;    

    @Override
    public void setNext(IHandler next) {
        this.next = next;
    }

    @Override
    public boolean handle(String titular, String senha) {
        if (titular != null && !titular.equals("")) {
            return true;
        }
        if (next != null) {
            next.handle(senha, titular);
        }        
        return false;
    }
}
