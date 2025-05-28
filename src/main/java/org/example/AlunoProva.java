package org.example;

/**** @author Ronnald*/
public class AlunoProva {
    private Aluno aluno;
    private double[] notas;

    public AlunoProva(Aluno aluno, double[] notas) {
        this.aluno = aluno;
        this.notas = notas;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public double[] getNotas() {
        return notas;
    }

    public double notaTotal() {
        double total = 0.0;
        for (double nota : notas) {
            total += nota;
        }
        return total;
    }

    @Override
    public String toString() {
        return "Aluno: " + aluno.getNome() +
                " | Matrícula: " + aluno.getMat() +
                " | Nota Total: " + String.format("%.2f", notaTotal());
    }
}

