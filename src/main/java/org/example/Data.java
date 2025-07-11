package org.example;

/**
 *
 * @author Ronnald
 */

public class Data {
    private int dia;
    private int mes;
    private int ano;

    public Data(int dia, int mes, int ano) {
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
    }

    public int getDia() {
        return dia;
    }

    public int getMes() {
        return mes;
    }

    public int getAno() {
        return ano;
    }

    public boolean posterior(Data d2) {
        if (this.ano > d2.ano) {
            return true;
        } else if (this.ano < d2.ano) {
            return false;
        }

        if (this.mes > d2.mes) {
            return true;
        } else if (this.mes < d2.mes) {
            return false;
        }

        return this.dia > d2.dia;
    }

    @Override
    public String toString() {
        return String.format("%02d/%02d/%04d", dia, mes, ano);
    }
}