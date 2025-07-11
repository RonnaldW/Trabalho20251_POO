package org.example;

/**
 *
 * @author Ronnald
 */

import java.io.BufferedWriter;
import java.io.IOException;

public class Aluno extends Pessoa {
    private String mat;

    public Aluno(String nome, String cpf, String mat) {
        super(nome, cpf);
        this.mat = mat;
    }

    public String getMat() {
        return mat;
    }

    public void setMat(String mat) {
        this.mat = mat;
    }

    @Override
    public String toString() {
        return getNome() + " (Matrícula: " + this.mat + ")";
    }


    public void salvarArq(BufferedWriter b) throws IOException {
        b.write("ALU\n");
        b.write(this.nome + "\n");
        b.write(this.cpf + "\n");
        b.write(this.mat + "\n");
    }
}