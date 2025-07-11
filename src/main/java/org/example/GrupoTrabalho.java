package org.example;

/**
 * @author Ronnald
 */

public class GrupoTrabalho {
    private Aluno[] alunos;
    private double nota;

    public GrupoTrabalho(Aluno[] alunos, double nota) {
        this.alunos = alunos;
        this.nota = nota;
    }

    public Aluno[] getAlunos() {
        return alunos;
    }

    public boolean alunoNoGrupo(String cpf) {
        for (Aluno aluno : alunos) {
            // Verifica se o aluno não é nulo antes de chamar o método
            if (aluno != null && aluno.getCpf().equals(cpf)) {
                return true;
            }
        }
        return false;
    }

    public double getNota() {
        return nota;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("GrupoTrabalho [nota=").append(nota).append(", Alunos=");
        if (alunos != null) {
            for (int i = 0; i < alunos.length; i++) {
                if (alunos[i] != null) {
                    sb.append(alunos[i].getNome());
                    if (i < alunos.length - 1) {
                        sb.append(", ");
                    }
                }
            }
        }
        sb.append("]");
        return sb.toString();
    }
}