package org.example;

import java.util.ArrayList;
import java.util.List;

public class Sistema {
    private List<Professor> profs;
    private List<Aluno> alunos;
    private List<Turma> turmas;

    // Construtor
    public Sistema() {
        this.profs = new ArrayList<>();
        this.alunos = new ArrayList<>();
        this.turmas = new ArrayList<>();
    }

    /***************************************************/

    // Métodos para Professores
    public void novoProf(Professor p) {
        this.profs.add(p);
    }

    public Professor encontrarProf(String cpf) {
        for (Professor p : this.profs) {
            if (p.getCpf().equals(cpf)) {
                return p;
            }
        }
        return null;
    }

    public void listarProfs() {
        if (this.profs.isEmpty()) {
            System.out.println("Nenhum professor cadastrado até o momento.");
        } else {
            System.out.println("Professores cadastrados:");
            for (Professor p : this.profs) {
                System.out.println("* " + p);
            }
        }
    }

    /***************************************************/

    // Métodos para Alunos

    public void novoAluno(Aluno a) {
        this.alunos.add(a);
    }

    public Aluno encontrarAluno(String mat) {
        for (Aluno a : this.alunos) {
            if (a.getMat().equals(mat)) {
                return a;
            }
        }
        return null;
    }

    public void listarAlunos() {
        if (this.alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado até o momento.");
        } else {
            System.out.println("Alunos cadastrados:");
            for (Aluno a : this.alunos) {
                System.out.println("* " + a);
            }
        }
    }

    /***************************************************/

    // Metodos para Turmas
    public void novaTurma(Turma t) {
        this.turmas.add(t);
    }

    public Turma encontrarTurma(String nome) {
        for (Turma t : this.turmas) {
            if (t.getNome().equals(nome)) {
                return t;
            }
        }
        return null;
    }

    public void listarTurmas() {
        if (this.turmas.isEmpty()) {
            System.out.println("Nenhuma turma cadastrada até o momento.");
        } else {
            System.out.println("Médias das turmas:");
            for (Turma t : this.turmas) {
                t.medias();
                System.out.println(); // Linha em branco entre turmas
            }
        }
    }
}