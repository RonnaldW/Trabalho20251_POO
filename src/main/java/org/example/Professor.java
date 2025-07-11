package org.example;

/**
 *
 * @author Ronnald
 */

import java.io.BufferedWriter;
import java.io.IOException;

public class Professor extends Pessoa {
    private double salario;

    public Professor(String nome, String cpf, double salario) {
        super(nome, cpf);
        this.salario = salario;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    @Override
    public String toString() {
        return getNome() + " (Salário: R$" + String.format("%.2f", salario) + ")";
    }


    public void salvarArq(BufferedWriter b) throws IOException {
        b.write("PROF\n");
        b.write(this.nome + "\n");
        b.write(this.cpf + "\n");
        b.write(this.salario + "\n");
    }
}