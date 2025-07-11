package org.example;

/**
 *
 * @author Ronnald
 */

import java.io.BufferedWriter;
import java.io.IOException;

public abstract class Avaliacao {
    protected String nome;
    protected Data dtAplic;
    protected double valor;

    public Avaliacao(String nome, Data dtAplic, double valor) {
        this.nome = nome;
        this.dtAplic = dtAplic;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public Data getDataAplicacao() {
        return dtAplic;
    }

    public double getValor() {
        return valor;
    }

    public abstract double nota(String cpf);

    public abstract void salvarArq(BufferedWriter b) throws IOException;
}