package org.example;

/**
 *
 * @author Ronnald
 */

import java.io.BufferedWriter;
import java.io.IOException;

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

    @Override
    public void salvarArq(BufferedWriter b) throws IOException {
        b.write("PROV\n");
        b.write(this.nome + "\n");
        b.write(this.dtAplic.getDia() + "\n");
        b.write(this.dtAplic.getMes() + "\n");
        b.write(this.dtAplic.getAno() + "\n");
        b.write(this.valor + "\n");
        b.write(this.nQuestoes + "\n");

        for (AlunoProva ap : this.alunoProvas) {
            for (double notaQuestao : ap.getNotas()) {
                b.write(notaQuestao + "\n");
            }
        }
    }
}