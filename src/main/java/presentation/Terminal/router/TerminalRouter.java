package presentation.Terminal.router;

import java.util.Scanner;

import domain.entities.usuarios.Administrador;
import infra.auth.Session;
import presentation.Terminal.AgendamentoTerminalHandler;
import presentation.Terminal.CategoriaDespesaTerminalHandler;
import presentation.Terminal.ClienteTerminalHandler;
import presentation.Terminal.ColaboradorTerminalHandler;
import presentation.Terminal.DespesaTerminalHandler;
import presentation.Terminal.GerarBalancoTerminalHandler;
import presentation.Terminal.GerarRelatorioTerminalHandler;
import presentation.Terminal.LoginTerminalHandler;
import presentation.Terminal.OficinaTerminalHandler;
import presentation.Terminal.OrdemDeServicoTerminalHandler;
import presentation.Terminal.ServicoTerminalHandler;

/**
 * Classe responsável por controlar o fluxo principal do sistema via terminal.
 * Gerencia a navegação entre os diferentes módulos (Clientes, Oficina,
 * Serviços, Agendamentos, etc.) com base no perfil do usuário autenticado
 * (Administrador ou Funcionário).
 */
public class TerminalRouter {

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Inicia o roteador principal do sistema terminal. Primeiro realiza o login
     * do usuário, e em seguida apresenta o menu principal. O menu exibido pode
     * variar dependendo se o usuário logado é um administrador.
     */
    public void start() {
        LoginTerminalHandler loginHandler = new LoginTerminalHandler(scanner);

        boolean loggedIn = false;
        while (!loggedIn) {
            loggedIn = loginHandler.login();
        }

        boolean isAdmin = Session.getPessoaLogado() instanceof Administrador;

        while (true) {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1 - Clientes");
            System.out.println("2 - Oficina");
            System.out.println("3 - Serviços");
            System.out.println("4 - Agendamentos");
            System.out.println("5 - Ordens de Serviço");
            if (isAdmin) {
                System.out.println("6 - Colaboradores");
                System.out.println("7 - Financeiro");
            }
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1" ->
                    new ClienteTerminalHandler(scanner).menu();
                case "2" ->
                    new OficinaTerminalHandler(scanner).menu();
                case "3" ->
                    new ServicoTerminalHandler(scanner).menu();
                case "4" ->
                    new AgendamentoTerminalHandler(scanner).menu();
                case "5" ->
                    new OrdemDeServicoTerminalHandler(scanner).menu();
                case "6" -> {
                    if (isAdmin) {
                        new ColaboradorTerminalHandler(scanner).menu();
                    } else {
                        System.out.println("Opção inválida.");
                    }
                }
                case "7" -> {
                    if (isAdmin) {
                        menuFinanceiro();
                    } else {
                        System.out.println("Opção inválida.");
                    }
                }
                case "0" -> {
                    System.out.println("Encerrando...");
                    return;
                }
                default ->
                    System.out.println("Opção inválida.");
            }
        }
    }

    /**
     * Exibe o submenu financeiro com opções restritas a administradores.
     */
    private void menuFinanceiro() {
        while (true) {
            System.out.println("\n=== MENU FINANCEIRO ===");
            System.out.println("1 - Categorias de Despesa");
            System.out.println("2 - Despesas");
            System.out.println("3 - Relatório de Vendas e Serviços");
            System.out.println("4 - Balanço Financeiro Mensal");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1" ->
                    new CategoriaDespesaTerminalHandler(scanner).menu();
                case "2" ->
                    new DespesaTerminalHandler(scanner).menu();
                case "3" ->
                    new GerarRelatorioTerminalHandler(scanner).menu();
                case "4" ->
                    new GerarBalancoTerminalHandler(scanner).menu();
                case "0" -> {
                    return;
                }
                default ->
                    System.out.println("Opção inválida.");
            }
        }
    }
}
