package mecanicabase.view.Terminal.router;

import java.util.Scanner;
import mecanicabase.infra.ApplicationContext;
import mecanicabase.infra.auth.Session;
import mecanicabase.model.usuarios.Administrador;
import mecanicabase.view.Terminal.LoginTerminalHandler;

/**
 * Classe responsável por controlar o fluxo principal do sistema via terminal.
 * Gerencia a navegação entre os diferentes módulos (Clientes, Oficina,
 * Serviços, Agendamentos, etc.) com base no perfil do usuário autenticado
 * (Administrador ou Funcionário).
 */
public class TerminalRouter {

    private final Scanner scanner;
    private final ApplicationContext context;

    public TerminalRouter(ApplicationContext context) {
        this.context = context;
        this.scanner = context.scanner;
    }

    public void start() {
        LoginTerminalHandler loginHandler = context.loginHandler;

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
            System.out.println("6 - Executar benchmark de 100.000 OS");
            if (isAdmin) {
                System.out.println("7 - Colaboradores");
                System.out.println("8 - Financeiro");
            }
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1" ->
                    context.clienteHandler.menu();
                case "2" ->
                    context.oficinaHandler.menu();
                case "3" ->
                    context.servicoHandler.menu();
                case "4" ->
                    context.agendamentoHandler.menu();
                case "5" ->
                    context.ordemHandler.menu();
                case "6" ->
                    executarBenchmark();
                case "7" -> {
                    if (isAdmin) {
                        context.colaboradorHandler.menu();
                    } else {
                        System.out.println("Opção inválida.");
                    }
                }
                case "8" -> {
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

    private void executarBenchmark() {
        System.out.println("⏳ Iniciando benchmark realista com 10k, 50k e 100k OS usando as peças já cadastradas...");

        try {
            mecanicabase.infra.benchmark.BenchmarkPecasAplicadas.executarTodosBenchmarks();
            System.out.println("✅ Benchmarks concluídos com sucesso!");
            System.out.println("📄 Resultados salvos em: ./data/medicoes_aplicadas.txt");
        } catch (Exception e) {
            System.out.println("❌ Erro ao executar benchmarks: " + e.getMessage());
        }
    }

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
                    context.categoriaHandler.menu();
                case "2" ->
                    context.despesaHandler.menu();
                case "3" ->
                    context.relatorioHandler.menu();
                case "4" ->
                    context.balancoHandler.menu();
                case "0" -> {
                    return;
                }
                default ->
                    System.out.println("Opção inválida.");
            }
        }
    }
}
