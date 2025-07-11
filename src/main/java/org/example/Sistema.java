package org.example;

/**
 *
 * @author Ronnald
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sistema {
    private List<Professor> profs;
    private List<Aluno> alunos;
    private List<Turma> turmas;

    public Sistema() {
        this.profs = new ArrayList<>();
        this.alunos = new ArrayList<>();
        this.turmas = new ArrayList<>();
    }

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
            System.out.println("--- Médias das Turmas (ordenado por semestre) ---");

            Collections.sort(this.turmas);

            for (Turma t : this.turmas) {
                t.medias();
                System.out.println();
            }
        }
    }

    public void salvarDados() {
        try (BufferedWriter buff = new BufferedWriter(new FileWriter("dados.txt"))) {
            for (Professor p : this.profs) {
                p.salvarArq(buff);
            }
            for (Aluno a : this.alunos) {
                a.salvarArq(buff);
            }
            for (Turma t : this.turmas) {
                t.salvarArq(buff);
            }
            buff.write("FIM\n");
        } catch (IOException e) {
            System.err.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }
}