package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;


/**
 * Classe com as rotinas de entrada e saída do projeto
 * @author Hilario Seibel Junior e Ronnald Willian
 */

public class Entrada {
    private Scanner teclado = new Scanner(System.in);

    private String lerLinhaTeclado(String msg) {
        System.out.print(msg);
        return this.teclado.nextLine();
    }

    private int lerInteiro(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return Integer.parseInt(this.teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Erro: Entrada inválida. Por favor, digite um número inteiro.");
            }
        }
    }

    private double lerDouble(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return Double.parseDouble(this.teclado.nextLine().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Erro: Entrada inválida. Por favor, digite um número.");
            }
        }
    }

    public int menu() {
        String msg = "*********************\n" +
                "Escolha uma opção:\n" +
                "1) Cadastrar professor\n" +
                "2) Cadastrar aluno\n" +
                "3) Cadastrar turma\n" +
                "4) Listar turmas\n" +
                "0) Sair\n";

        System.out.println(msg);
        int op = this.lerInteiro("Sua opção: ");

        while (op < 0 || op > 4) {
            System.out.println("Opção inválida.");
            op = this.lerInteiro("Tente novamente: ");
        }
        return op;
    }

    public void cadProf(Sistema s) {
        s.listarProfs();
        String nome = this.lerLinhaTeclado("Digite o nome do professor: ");
        String cpf = this.lerLinhaTeclado("Digite o cpf do professor: ");
        double salario = this.lerDouble("Digite o salário do professor: R$");

        if (s.encontrarProf(cpf) == null) {
            Professor p = new Professor(nome, cpf, salario);
            s.novoProf(p);
            System.out.println("Professor cadastrado com sucesso.");
        } else {
            System.out.println("Erro: CPF duplicado. Professor não adicionado.");
        }
    }

    public void cadAluno(Sistema s) {
        s.listarAlunos();
        String nome = this.lerLinhaTeclado("Digite o nome do aluno: ");
        String cpf = this.lerLinhaTeclado("Digite o CPF do aluno: ");
        String matricula = this.lerLinhaTeclado("Digite a matrícula do aluno: ");

        if (s.encontrarAluno(matricula) == null) {
            Aluno a = new Aluno(nome, cpf, matricula);
            s.novoAluno(a);
            System.out.println("Aluno cadastrado com sucesso.");
        } else {
            System.out.println("Erro: Matrícula duplicada. Aluno não adicionado.");
        }
    }

    public void cadTurma(Sistema s) {
        s.listarTurmas();
        String nome = this.lerLinhaTeclado("Nome da turma: ");
        int ano = this.lerInteiro("Ano: ");
        int semestre = this.lerInteiro("Semestre: ");

        Professor prof = this.lerProf(s);
        if (prof == null) {
            System.out.println("Professor não encontrado. Turma não pode ser cadastrada.");
            return;
        }

        Aluno[] alunos = this.lerAlunos(s);
        int nAvaliacoes = this.lerInteiro("Número de avaliações: ");
        Avaliacao[] avaliacoes = new Avaliacao[nAvaliacoes];

        for (int i = 0; i < nAvaliacoes; i++) {
            int tipo = this.lerInteiro("Tipo de avaliação (1-Prova / 2-Trabalho): ");
            if (tipo == 1) {
                avaliacoes[i] = this.lerProva(s, alunos);
            } else if (tipo == 2) {
                avaliacoes[i] = this.lerTrabalho(s, alunos);
            } else {
                System.out.println("Tipo de avaliação inválido.");
                i--;
            }
        }
        Turma t = new Turma(nome, ano, semestre, prof, alunos, avaliacoes);
        s.novaTurma(t);
        System.out.println("Turma cadastrada com sucesso.\n");
    }

    private Professor lerProf(Sistema s) {
        s.listarProfs();
        String cpf = this.lerLinhaTeclado("Digite o CPF do professor: ");
        return s.encontrarProf(cpf);
    }

    private Aluno[] lerAlunos(Sistema s) {
        int qtd = this.lerInteiro("Quantos alunos farão parte da turma? ");
        Aluno[] alunos = new Aluno[qtd];
        for (int i = 0; i < qtd; i++) {
            String matricula = this.lerLinhaTeclado("Matrícula do aluno " + (i+1) + ": ");
            alunos[i] = s.encontrarAluno(matricula);
            if (alunos[i] == null) {
                System.out.println("Aluno não encontrado. Tente novamente.");
                i--;
            }
        }
        return alunos;
    }

    private AlunoProva lerAlunoProva(Sistema s, Aluno a, int nQuestoes) {
        System.out.println("Notas para o aluno: " + a.getNome());
        double[] notas = new double[nQuestoes];
        for (int i = 0; i < nQuestoes; i++) {
            notas[i] = this.lerDouble("Nota da questão " + (i + 1) + ": ");
        }
        return new AlunoProva(a, notas);
    }

    private Prova lerProva(Sistema s, Aluno[] alunos) {
        String nome = this.lerLinhaTeclado("Nome da prova: ");
        Data dtAplic = new Data(this.lerInteiro("Dia da aplicação: "), this.lerInteiro("Mês da aplicação: "), this.lerInteiro("Ano da aplicação: "));
        double valor = this.lerDouble("Valor total da prova: ");
        int nQuestoes = this.lerInteiro("Número de questões da prova: ");
        AlunoProva[] alunoProvas = new AlunoProva[alunos.length];
        for (int i = 0; i < alunos.length; i++) {
            alunoProvas[i] = this.lerAlunoProva(s, alunos[i], nQuestoes);
        }
        return new Prova(nome, dtAplic, valor, nQuestoes, alunoProvas);
    }

    private GrupoTrabalho lerGrupoTrabalho(Sistema s) {
        int qtd = this.lerInteiro("Quantos alunos no grupo de trabalho? ");
        Aluno[] alunos = new Aluno[qtd];
        for (int i = 0; i < qtd; i++) {
            String matricula = this.lerLinhaTeclado("Matrícula do aluno " + (i+1) + ": ");
            alunos[i] = s.encontrarAluno(matricula);
            if (alunos[i] == null) {
                System.out.println("Aluno não encontrado. Tente novamente.");
                i--;
            }
        }
        double nota = this.lerDouble("Nota do grupo: ");
        return new GrupoTrabalho(alunos, nota);
    }

    private Trabalho lerTrabalho(Sistema s, Aluno[] alunos) {
        String nome = this.lerLinhaTeclado("Nome do trabalho: ");
        Data dtAplic = new Data(this.lerInteiro("Dia da entrega: "), this.lerInteiro("Mês da entrega: "), this.lerInteiro("Ano da entrega: "));
        double valor = this.lerDouble("Valor total do trabalho: ");
        int nIntegrantes = this.lerInteiro("Número máximo de integrantes por grupo: ");
        int nGrupos = this.lerInteiro("Quantos grupos serão criados? ");
        GrupoTrabalho[] grupos = new GrupoTrabalho[nGrupos];
        for (int i = 0; i < nGrupos; i++) {
            System.out.println("--- Grupo " + (i+1) + " ---");
            grupos[i] = this.lerGrupoTrabalho(s);
        }
        return new Trabalho(nome, dtAplic, valor, nIntegrantes, grupos);
    }

    private void carregarProfessor(BufferedReader buff, Sistema s) throws IOException {
        String nome = buff.readLine();
        String cpf = buff.readLine();
        double salario = Double.parseDouble(buff.readLine());
        s.novoProf(new Professor(nome, cpf, salario));
    }

    private void carregarAluno(BufferedReader buff, Sistema s) throws IOException {
        String nome = buff.readLine();
        String cpf = buff.readLine();
        String matricula = buff.readLine();
        s.novoAluno(new Aluno(nome, cpf, matricula));
    }

    private Prova carregarProva(BufferedReader buff, Aluno[] alunosDaTurma) throws IOException {
        String nome = buff.readLine();
        Data dt = new Data(Integer.parseInt(buff.readLine()), Integer.parseInt(buff.readLine()), Integer.parseInt(buff.readLine()));
        double valor = Double.parseDouble(buff.readLine());
        int nQuestoes = Integer.parseInt(buff.readLine());
        AlunoProva[] aps = new AlunoProva[alunosDaTurma.length];
        for (int i = 0; i < alunosDaTurma.length; i++) {
            double[] notas = new double[nQuestoes];
            for (int j = 0; j < nQuestoes; j++) {
                notas[j] = Double.parseDouble(buff.readLine());
            }
            aps[i] = new AlunoProva(alunosDaTurma[i], notas);
        }
        return new Prova(nome, dt, valor, nQuestoes, aps);
    }

    private Trabalho carregarTrabalho(BufferedReader buff, Sistema s) throws IOException {
        String nome = buff.readLine();
        Data dt = new Data(Integer.parseInt(buff.readLine()), Integer.parseInt(buff.readLine()), Integer.parseInt(buff.readLine()));
        double valor = Double.parseDouble(buff.readLine());
        int nIntegrantes = Integer.parseInt(buff.readLine());
        int nGrupos = Integer.parseInt(buff.readLine());
        GrupoTrabalho[] grupos = new GrupoTrabalho[nGrupos];
        for (int i = 0; i < nGrupos; i++) {
            int nAlunosGrupo = Integer.parseInt(buff.readLine());
            Aluno[] alunosGrupo = new Aluno[nAlunosGrupo];
            for (int j = 0; j < nAlunosGrupo; j++) {
                String mat = buff.readLine();
                alunosGrupo[j] = s.encontrarAluno(mat);
            }
            double nota = Double.parseDouble(buff.readLine());
            grupos[i] = new GrupoTrabalho(alunosGrupo, nota);
        }
        return new Trabalho(nome, dt, valor, nIntegrantes, grupos);
    }

    private void carregarTurma(BufferedReader buff, Sistema s) throws IOException {
        String nome = buff.readLine();
        int ano = Integer.parseInt(buff.readLine());
        int sem = Integer.parseInt(buff.readLine());
        Professor prof = s.encontrarProf(buff.readLine());
        int nAlunos = Integer.parseInt(buff.readLine());
        Aluno[] alunos = new Aluno[nAlunos];
        for (int i = 0; i < nAlunos; i++) {
            alunos[i] = s.encontrarAluno(buff.readLine());
        }
        int nAvs = Integer.parseInt(buff.readLine());
        Avaliacao[] avs = new Avaliacao[nAvs];
        for (int i = 0; i < nAvs; i++) {
            String tipoAv = buff.readLine();
            if (tipoAv.equals("PROV")) {
                avs[i] = carregarProva(buff, alunos);
            } else if (tipoAv.equals("TRAB")) {
                avs[i] = carregarTrabalho(buff, s);
            }
        }
        s.novaTurma(new Turma(nome, ano, sem, prof, alunos, avs));
    }

    public void carregarDados(Sistema s) {
        try (BufferedReader buff = new BufferedReader(new FileReader("dados.txt"))) {
            System.out.println("Carregando dados do arquivo...");
            String linha;
            while ((linha = buff.readLine()) != null) {
                if (linha.equals("FIM")) break;
                switch (linha) {
                    case "PROF":
                        carregarProfessor(buff, s);
                        break;
                    case "ALU":
                        carregarAluno(buff, s);
                        break;
                    case "TUR":
                        carregarTurma(buff, s);
                        break;
                }
            }
            System.out.println("Dados carregados com sucesso.");
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo 'dados.txt' não encontrado. Iniciando com sistema vazio.");
        } catch (IOException | NumberFormatException e) {
            System.err.println("Erro ao ler ou processar o arquivo de dados. Verifique o formato do arquivo. Erro: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Sistema sistema = new Sistema();
        Entrada entrada = new Entrada();

        entrada.carregarDados(sistema);

        int opcao;
        do {
            opcao = entrada.menu();
            switch (opcao) {
                case 1:
                    entrada.cadProf(sistema);
                    break;
                case 2:
                    entrada.cadAluno(sistema);
                    break;
                case 3:
                    entrada.cadTurma(sistema);
                    break;
                case 4:
                    sistema.listarTurmas();
                    break;
                case 0:
                    break; // Apenas sai do switch, o salvamento ocorre após o loop.
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);

        System.out.println("Saindo e salvando dados...");
        sistema.salvarDados();
        entrada.teclado.close();
        System.out.println("Programa encerrado.");
    }
}