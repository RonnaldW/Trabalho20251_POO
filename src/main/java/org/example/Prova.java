package org.example;

/**** @author Ronnald*/
public class Prova extends Avaliacao {
    private int nQuestoes;
    private AlunoProva[] alunoProvas;

    public Prova(String nome, Data dtAplic, double valor, int nQuestoes, AlunoProva[] alunoProvas) {
        super(nome, dtAplic, valor);
        this.nQuestoes = nQuestoes;
        this.alunoProvas = alunoProvas;
    }

    public int getnQuestoes() {
        return nQuestoes;
    }

    public AlunoProva[] getAlunoProvas() {
        return alunoProvas;
    }

    @Override
    public double nota(String cpf) {
        for (AlunoProva ap : alunoProvas) {
            if (ap.getAluno().getCpf().equals(cpf)) {
                return ap.notaTotal();
            }
        }
        return -1;
    }
}
