package mecanicabase.view.Terminal;

import java.util.Scanner;
import mecanicabase.RequisitosImplementadosTest;

public class TestTerminalHandler {

    private final Scanner scanner;

    public TestTerminalHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public void menu() {
        while (true) {
            System.out.println("\n=== Modo Teste: Menu de Testes ===");
            System.out.println("1. Questão 1: Cliente");
            System.out.println("2.3. Questão 2/3: Cria, edita, muda senha, e exclui funcionario/administrador");
            System.out.println("4. Questão 4: Peça/Estoque");
            System.out.println("5.7 Questão 5/7: Agendamento com cancelamento + exibição (Questão 14)");
            System.out.println("6. Questão 6: Entrada de Peça");
            System.out.println("7.5 Questão 7/5: Agendamento com conclusão + exibição (Questão 14)");
            System.out.println("8. Questão 8: Relatório de Vendas e Serviços");
            System.out.println("9. Questão 9: Balanço Mensal");
            System.out.println("15. Questão 15: Iterator e foreach");
            System.out.println("16. Questão 16: Comparator + Collections.sort()");
            System.out.println("17. Questão 17: find com Iterator + Comparator + binarySearch()");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            String op = scanner.nextLine();
            switch (op) {
                case "1" ->
                    RequisitosImplementadosTest.testeQuestao1_Cliente();
                case "2", "3" ->
                    RequisitosImplementadosTest.testeQuestao2e3_FuncionarioEAdministrador();
                case "4" ->
                    RequisitosImplementadosTest.testeQuestao4_PecaEstoque();
                case "5", "7", "14" ->
                    RequisitosImplementadosTest.testeQuestao5_7_Agendamento();
                case "6" ->
                    RequisitosImplementadosTest.testeQuestao6_EntradaPeca();
                case "8" ->
                    RequisitosImplementadosTest.testeQuestao8_RelatorioVendasServicos();
                case "9" ->
                    RequisitosImplementadosTest.testeQuestao9_BalancoMensal();
                case "15" ->
                    RequisitosImplementadosTest.questao15();
                case "16" ->
                    RequisitosImplementadosTest.questao16();
                case "17" ->
                    RequisitosImplementadosTest.questao17();
                case "0" -> {
                    System.out.println("Saindo do modo teste.");
                    return;
                }
                default ->
                    System.out.println("Opção inválida!");
            }
        }
    }
}
