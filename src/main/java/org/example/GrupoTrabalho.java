package org.example;

/**
 *
 * @author Ronnald
 */
public class GrupoTrabalho {
    private Aluno[] alunos;
    private double nota;

    public GrupoTrabalho(Aluno[] alunos, double nota) {
        this.alunos = alunos;
        this.nota = nota;
    }

    public boolean alunoNoGrupo(String cpf) {
        for (Aluno aluno : alunos) {
            if (aluno.getCpf().equals(cpf)) {
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
        for (int i = 0; i < alunos.length; i++) {
            sb.append(alunos[i].getNome());
            if (i < alunos.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
