package com.caixaeletronico.chainOfResponsability;

public interface IHandler {
    void setNext(IHandler next);
    boolean handle(String titular, String senha);
}
