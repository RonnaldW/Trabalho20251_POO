package org.example;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Classe com as rotinas de entrada e saída do projeto
 * @author Hilario Seibel Junior e Ronnald Willian
 */

public class Entrada {
    public Scanner input;

    /**
     * Construtor da classe Entrada
     * Se houver um arquivo input.txt, define que o Scanner vai ler deste arquivo.
     * Se o arquivo não existir, define que o Scanner vai ler da entrada padrão (teclado)
     */
    public Entrada() {
        try {
            // Se houver um arquivo input.txt, o Scanner vai ler dele.
            this.input = new Scanner(new FileInputStream("input.txt"));
        } catch (FileNotFoundException e) {
            // Caso contrário, vai ler do teclado.
            this.input = new Scanner(System.in);
        }
    }

    /**
     * Faz a leitura de uma linha inteira
     * Ignora linhas começando com #, que vão indicar comentários no arquivo de entrada:
     * @param msg: Mensagem que será exibida ao usuário
     * @return Uma String contendo a linha que foi lida
     */
    private String lerLinha(String msg) {
        String linha = this.input.nextLine();

        while (!linha.isEmpty() && linha.charAt(0) == '#') {
            linha = this.input.nextLine();
        }

        return linha;
    }

    /**
     * Faz a leitura de um número inteiro
     * @param msg: Mensagem que será exibida ao usuário
     * @return O número digitado pelo usuário convertido para int
     */
    private int lerInteiro(String msg) {
        String linha = this.lerLinha(msg);
        return Integer.parseInt(linha);
    }

    /**
     * Faz a leitura de um double
     * @param msg: Mensagem que será exibida ao usuário
     * @return O número digitado pelo usuário convertido para double
     */
    private double lerDouble(String msg) {
        String linha = this.lerLinha(msg);
        return Double.parseDouble(linha);
    }

    /**
     * Imprime o menu principal, lê a opção escolhida pelo usuário e retorna a opção selecionada.
     * @return Inteiro contendo a opção escolhida pelo usuário
     */
    public int menu() {
        String msg = "*********************\n" +
                "Escolha uma opção:\n" +
                "1) Cadastrar professor\n" +
                "2) Cadastrar aluno\n" +
                "3) Cadastrar turma\n" +
                "4) Listar turmas\n" +
                "0) Sair\n";

        int op = this.lerInteiro(msg);

        while (op < 0 || op > 4) {
            System.out.println("Opção inválida. Tente novamente: ");
            op = this.lerInteiro(msg);
        }

        return op;
    }

    /***************************************************/

    /**
     * Lê os dados de um novo Professor e cadastra-o no sistema.
     * @param s: Um objeto da classe Sistema
     */
    public void cadProf(Sistema s) {
        s.listarProfs();

        String nome = this.lerLinha("Digite o nome do professor: ");
        String cpf = this.lerLinha("Digite o cpf do professor: ");
        double salario = this.lerDouble("Digite o salário do professor: R$");

        if (s.encontrarProf(cpf) == null) { // Garantindo que o CPF não esteja duplicado
            Professor p = new Professor(nome, cpf, salario);
            s.novoProf(p);
            System.out.println("Professor cadastrado com sucesso.");
        } else {
            System.out.println("Erro: CPF duplicado. Professor não adicionado.");
        }
    }

    /***************************************************/

    /**
     * Lê os dados de um novo Aluno e cadastra-o no sistema.
     * @param s: Um objeto da classe Sistema
     */
    public void cadAluno(Sistema s) {
        s.listarAlunos();

        String nome = this.lerLinha("Digite o nome do aluno: ");
        String cpf = this.lerLinha("Digite o CPF do aluno: ");
        String matricula = this.lerLinha("Digite a matrícula do aluno: ");

        if (s.encontrarAluno(matricula) == null) { // Garante que a matrícula não seja duplicada
            Aluno a = new Aluno(nome, cpf, matricula);
            s.novoAluno(a);
            System.out.println("Aluno cadastrado com sucesso.");
        } else {
            System.out.println("Erro: Matrícula duplicada. Aluno não adicionado.");
        }
    }

    /***************************************************/

    /**
     * Lê os dados de uma nova Turma e cadastra-a no sistema.
     * Inclui a leitura das avaliações (Prova ou Trabalho).
     * @param s: Um objeto da classe Sistema
     */
    /**
     * Lê os dados de uma nova Turma e cadastra-a no sistema.
     * Inclui a leitura das avaliações (Prova ou Trabalho).
     * @param s: Um objeto da classe Sistema
     */
    public void cadTurma(Sistema s) {
        s.listarTurmas();

        String nome = this.lerLinha("Nome da turma: ");
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
                i--; // repete a iteração
            }
        }

        Turma t = new Turma(nome, ano, semestre, prof, alunos, avaliacoes);
        s.novaTurma(t);
        System.out.println("Turma cadastrada com sucesso.\n");
    }


    /***************************************************/

    /**
     * Seleciona um professor já cadastrado no sistema
     * @param s: Um objeto da classe Sistema
     * @return Objeto da classe Professor
     */
    private Professor lerProf(Sistema s) {
        s.listarProfs();
        String cpf = this.lerLinha("Digite o CPF do professor: ");
        return s.encontrarProf(cpf);
    }

    /**
     * Seleciona múltiplos alunos já cadastrados no sistema
     * @param s: Um objeto da classe Sistema
     * @return Array de objetos da classe Aluno
     */
    private Aluno[] lerAlunos(Sistema s) {
        int qtd = this.lerInteiro("Quantos alunos farão parte da turma? ");
        Aluno[] alunos = new Aluno[qtd];

        for (int i = 0; i < qtd; i++) {
            String matricula = this.lerLinha("Matrícula do aluno: ");
            alunos[i] = s.encontrarAluno(matricula);

            if (alunos[i] == null) {
                System.out.println("Aluno não encontrado. Tente novamente.");
                i--; // repete esta iteração
            }
        }

        return alunos;
    }

    /**
     * Lê as notas por questão de um aluno em uma prova
     * @param s: Um objeto da classe Sistema
     * @param a: Objeto da classe Aluno
     * @param nQuestoes: Número de questões da prova
     * @return Objeto da classe AlunoProva
     */
    private AlunoProva lerAlunoProva(Sistema s, Aluno a, int nQuestoes) {
        System.out.println("Notas para o aluno: " + a.getNome());

        double[] notas = new double[nQuestoes];
        for (int i = 0; i < nQuestoes; i++) {
            notas[i] = this.lerDouble("Nota da questão " + (i + 1) + ": ");
        }

        return new AlunoProva(a, notas);
    }

    /**
     * Lê os dados de uma Prova e suas notas por aluno
     * @param s: Um objeto da classe Sistema
     * @param alunos: Array de alunos da turma
     * @return Objeto da classe Prova
     */
    private Prova lerProva(Sistema s, Aluno[] alunos) {
        String nome = this.lerLinha("Nome da prova: ");
        Data dtAplic = new Data(
                this.lerInteiro("Dia da aplicação: "),
                this.lerInteiro("Mês da aplicação: "),
                this.lerInteiro("Ano da aplicação: ")
        );
        double valor = this.lerDouble("Valor total da prova: ");
        int nQuestoes = this.lerInteiro("Número de questões da prova: ");

        AlunoProva[] alunoProvas = new AlunoProva[alunos.length];
        for (int i = 0; i < alunos.length; i++) {
            alunoProvas[i] = this.lerAlunoProva(s, alunos[i], nQuestoes);
        }

        return new Prova(nome, dtAplic, valor, nQuestoes, alunoProvas);
    }

    /**
     * Lê os dados de um GrupoTrabalho
     * @param s: Um objeto da classe Sistema
     * @return Objeto da classe GrupoTrabalho
     */
    private GrupoTrabalho lerGrupoTrabalho(Sistema s) {
        int qtd = this.lerInteiro("Quantos alunos no grupo de trabalho? ");
        Aluno[] alunos = new Aluno[qtd];

        for (int i = 0; i < qtd; i++) {
            String matricula = this.lerLinha("Matrícula do aluno: ");
            alunos[i] = s.encontrarAluno(matricula);
            if (alunos[i] == null) {
                System.out.println("Aluno não encontrado. Tente novamente.");
                i--;
            }
        }

        double nota = this.lerDouble("Nota do grupo: ");
        return new GrupoTrabalho(alunos, nota);
    }

    /**
     * Lê os dados de um Trabalho e seus grupos
     * @param s: Um objeto da classe Sistema
     * @param alunos: Array de alunos da turma
     * @return Objeto da classe Trabalho
     */
    private Trabalho lerTrabalho(Sistema s, Aluno[] alunos) {
        String nome = this.lerLinha("Nome do trabalho: ");
        Data dtAplic = new Data(
                this.lerInteiro("Dia da entrega: "),
                this.lerInteiro("Mês da entrega: "),
                this.lerInteiro("Ano da entrega: ")
        );
        double valor = this.lerDouble("Valor total do trabalho: ");
        int nIntegrantes = this.lerInteiro("Número máximo de integrantes por grupo: ");
        int nGrupos = this.lerInteiro("Quantos grupos serão criados? ");

        GrupoTrabalho[] grupos = new GrupoTrabalho[nGrupos];
        for (int i = 0; i < nGrupos; i++) {
            grupos[i] = this.lerGrupoTrabalho(s);
        }

        return new Trabalho(nome, dtAplic, valor, nIntegrantes, grupos);
    }

    /*** MAIN*/
    public static void main(String[] args) {
        Sistema sistema = new Sistema();
        Entrada entrada = new Entrada();

        int opcao;
        do {
            opcao = entrada.menu();  // Vai ler tudo do input.txt

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
                    sistema.listarTurmas();  // Imprime as médias
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }
}