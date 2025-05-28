package org.example;

/**** @author Ronnald*/
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
}
