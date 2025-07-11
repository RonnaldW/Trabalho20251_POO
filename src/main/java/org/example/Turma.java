package org.example;

/**
 *
 * @author Ronnald
 */

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Turma implements Comparable<Turma> {
    private String nome;
    private int ano;
    private int sem;
    private Professor prof;
    private Aluno[] alunos;
    private Avaliacao[] avs;

    public Turma(String nome, int ano, int sem, Professor prof, Aluno[] alunos, Avaliacao[] avs) {
        this.nome = nome;
        this.ano = ano;
        this.sem = sem;
        this.prof = prof;
        this.alunos = alunos;
        this.avs = avs;
    }

    // define a ordem de classificação das turmas.
    @Override
    public int compareTo(Turma outra) {

        if (this.ano != outra.ano) {
            return Integer.compare(outra.ano, this.ano);
        }

        if (this.sem != outra.sem) {
            return Integer.compare(outra.sem, this.sem);
        }

        int compNome = this.nome.compareTo(outra.nome);
        if (compNome != 0) {
            return compNome;
        }

        return this.prof.getNome().compareTo(outra.prof.getNome());
    }

    /**
     * Calcula a nota final para um aluno específico na turma.
     * @param aluno O aluno para o qual a nota será calculada.
     * @return A nota final do aluno, limitada a 100.0.
     */
    private double calcularNotaFinal(Aluno aluno) {
        double somaNotasBrutaAluno = 0;
        if (avs != null) {
            for (Avaliacao av : avs) {
                double notaAvaliacao = av.nota(aluno.getCpf());
                somaNotasBrutaAluno += (notaAvaliacao < 0.0) ? 0.0 : notaAvaliacao;
            }
        }
        return Math.min(somaNotasBrutaAluno, 100.0);
    }

    /**
     * Calcula e exibe as médias da turma.
     * Os alunos são ordenados pela nota final (maior para menor), e depois por nome e matrícula.
     */
    public void medias() {
        System.out.println(String.format("Médias da Turma %s (%d/%d):", nome, ano, sem));

        if (alunos == null || alunos.length == 0) {
            System.out.println("Nenhum aluno matriculado nesta turma.");
            System.out.println("Média da turma: 0.00");
            return;
        }


        Map<String, Double> notasFinais = new HashMap<>();
        for (Aluno aluno : alunos) {
            notasFinais.put(aluno.getMat(), calcularNotaFinal(aluno));
        }

        List<Aluno> listaAlunos = Arrays.asList(this.alunos);


        listaAlunos.sort(new Comparator<Aluno>() {
            @Override
            public int compare(Aluno a1, Aluno a2) {
                double nota1 = notasFinais.get(a1.getMat());
                double nota2 = notasFinais.get(a2.getMat());


                int compNota = Double.compare(nota2, nota1);
                if (compNota != 0) {
                    return compNota;
                }

                int compNome = a1.getNome().compareTo(a2.getNome());
                if (compNome != 0) {
                    return compNome;
                }

                return a1.getMat().compareTo(a2.getMat());
            }
        });


        double somaTotalDaTurmaAjustada = 0;
        for (Aluno aluno : listaAlunos) {
            StringBuilder notasIndividuaisStr = new StringBuilder();
            if (avs != null) {
                for (Avaliacao av : avs) {
                    double notaAvaliacao = av.nota(aluno.getCpf());
                    notaAvaliacao = (notaAvaliacao < 0.0) ? 0.0 : notaAvaliacao;
                    String notaAvFormatada = String.format("%.1f ", notaAvaliacao).replace(',', '.');
                    notasIndividuaisStr.append(notaAvFormatada);
                }
            }

            double notaFinalAluno = notasFinais.get(aluno.getMat());
            somaTotalDaTurmaAjustada += notaFinalAluno;
            String notaFinalAlunoFormatada = String.format("%.1f", notaFinalAluno).replace(',', '.');

            System.out.println(aluno.toString() + ": " + notasIndividuaisStr.toString().trim() + " = " + notaFinalAlunoFormatada);
        }

        double mediaTurma = (alunos.length > 0) ? (somaTotalDaTurmaAjustada / alunos.length) : 0.0;
        String mediaTurmaFinalFormatada = String.format("%.2f", mediaTurma).replace(',', '.');
        System.out.println("Média da turma: " + mediaTurmaFinalFormatada);
    }


    public void salvarArq(BufferedWriter b) throws IOException {
        b.write("TUR\n");
        b.write(this.nome + "\n");
        b.write(this.ano + "\n");
        b.write(this.sem + "\n");
        b.write(this.prof.getCpf() + "\n");
        b.write(this.alunos.length + "\n");
        for (Aluno aluno : this.alunos) {
            b.write(aluno.getMat() + "\n");
        }
        b.write(this.avs.length + "\n");
        for (Avaliacao av : this.avs) {
            av.salvarArq(b);
        }
    }

    public String getNome() { return nome; }
    public int getAno() { return ano; }
    public int getSem() { return sem; }
    public Professor getProf() { return prof; }
    public Aluno[] getAlunos() { return alunos; }
    public Avaliacao[] getAvs() { return avs; }
}