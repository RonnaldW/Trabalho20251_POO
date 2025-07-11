package org.example;

/**
 *
 * @author Ronnald
 */

import java.io.BufferedWriter;
import java.io.IOException;

public class Trabalho extends Avaliacao {
    private int nIntegrantes;
    private GrupoTrabalho[] grupos;

    public Trabalho(String nome, Data dtAplic, double valor, int nIntegrantes, GrupoTrabalho[] grupos) {
        super(nome, dtAplic, valor);
        this.nIntegrantes = nIntegrantes;
        this.grupos = grupos;
    }

    public int getnIntegrantes() {
        return nIntegrantes;
    }

    public GrupoTrabalho[] getGrupos() {
        return grupos;
    }

    @Override
    public double nota(String cpf) {
        for (GrupoTrabalho grupo : grupos) {
            if (grupo.alunoNoGrupo(cpf)) {
                return grupo.getNota();
            }
        }
        return -1;
    }

    @Override
    public void salvarArq(BufferedWriter b) throws IOException {
        b.write("TRAB\n");
        b.write(this.nome + "\n");
        b.write(this.dtAplic.getDia() + "\n");
        b.write(this.dtAplic.getMes() + "\n");
        b.write(this.dtAplic.getAno() + "\n");
        b.write(this.valor + "\n");
        b.write(this.nIntegrantes + "\n");
        b.write(this.grupos.length + "\n");

        for (GrupoTrabalho grupo : this.grupos) {
            b.write(grupo.getAlunos().length + "\n");
            for (Aluno aluno : grupo.getAlunos()) {
                b.write(aluno.getMat() + "\n");
            }
            b.write(grupo.getNota() + "\n");
        }
    }
}