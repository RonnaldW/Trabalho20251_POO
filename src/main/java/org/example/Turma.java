package org.example;

/**** @author Ronnald*/
public class Turma {
    private String nome;
    private int ano;
    private int sem;
    private Professor prof;
    private Aluno[] alunos;
    private Avaliacao[] avs;

    // Construtor
    public Turma(String nome, int ano, int sem, Professor prof, Aluno[] alunos, Avaliacao[] avs) {
        this.nome = nome;
        this.ano = ano;
        this.sem = sem;
        this.prof = prof;
        this.alunos = alunos;
        this.avs = avs;
    }

    // Metodo para exibir as médias
    public void medias() {
        System.out.println(String.format("Médias da Turma %s (%d/%d):", nome, ano, sem));

        if (alunos == null || alunos.length == 0) {
            System.out.println("Nenhum aluno matriculado nesta turma.");
            String mediaTurmaStr = String.format("%.2f", 0.0).replace(',', '.');
            System.out.println("Média da turma: " + mediaTurmaStr);
            return;
        }

        double somaTotalDaTurmaAjustada = 0;

        for (Aluno aluno : alunos) {
            double somaNotasBrutaAluno = 0;
            StringBuilder notasIndividuaisStr = new StringBuilder();

            if (avs != null) {
                for (Avaliacao av : avs) {
                    double notaAvaliacao = av.nota(aluno.getCpf());
                    if (notaAvaliacao < 0.0) {
                        notaAvaliacao = 0.0;
                    }
                    somaNotasBrutaAluno += notaAvaliacao;
                    // Formata a nota da avaliação, substitui a vírgula e anexa
                    String notaAvFormatada = String.format("%.1f ", notaAvaliacao).replace(',', '.');
                    notasIndividuaisStr.append(notaAvFormatada);
                }
            }

            double notaFinalAluno = somaNotasBrutaAluno;
            if (notaFinalAluno > 100.0) {
                notaFinalAluno = 100.0;
            }

            somaTotalDaTurmaAjustada += notaFinalAluno;

            // Formata a nota final do aluno, substituindo a vírgula
            String notaFinalAlunoFormatada = String.format("%.1f", notaFinalAluno).replace(',', '.');

            System.out.println(
                    aluno.toString() + ": " +
                            notasIndividuaisStr.toString().trim() + " = " + notaFinalAlunoFormatada);
        }

        double mediaTurma = (alunos.length > 0) ? (somaTotalDaTurmaAjustada / alunos.length) : 0.0;
        // Formata a média final da turma, substitui a vírgula e imprime
        String mediaTurmaFinalFormatada = String.format("%.2f", mediaTurma).replace(',', '.');
        System.out.println("Média da turma: " + mediaTurmaFinalFormatada);
    }

    public String getNome() {
        return nome;
    }
}

