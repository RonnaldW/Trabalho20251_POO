package org.example;

/**** @author Ronnald*/
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
}
