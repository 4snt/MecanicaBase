package mecanicabase.view.Terminal;

import java.util.Scanner;

/**
 * Classe responsável por exibir o menu principal da seção Oficina no terminal.
 * Permite navegar entre os menus de veículos e peças (estoque).
 */
public class OficinaTerminalHandler {

    // Scanner para entrada de dados do usuário
    private final Scanner scanner;

    // Handler responsável pelas interações com veículos
    private final VeiculoTerminalHandler veiculoTerminalHandler;

    // Handler responsável pelas interações com peças (estoque)
    private final PecaTerminalHandler pecaTerminalHandler;

    /**
     * Construtor do handler de oficina. Inicializa os sub-handlers.
     *
     * @param scanner Scanner compartilhado com o sistema.
     * @param veiculoHandler Handler de veículos
     * @param pecaHandler Handler de peças (estoque)
     */
    public OficinaTerminalHandler(Scanner scanner,
            VeiculoTerminalHandler veiculoHandler,
            PecaTerminalHandler pecaHandler) {
        this.scanner = scanner;
        this.veiculoTerminalHandler = veiculoHandler;
        this.pecaTerminalHandler = pecaHandler;
    }

    /**
     * Exibe o menu principal da oficina e delega ações com base na escolha do
     * usuário.
     */
    public void menu() {
        while (true) {
            System.out.println("\n=== MENU PRINCIPAL - OFICINA ===");
            System.out.println("1 - Veículos");
            System.out.println("2 - Estoque");
            System.out.println("0 - Sair");

            System.out.print("Escolha: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1" ->
                    veiculoTerminalHandler.menu();
                case "2" ->
                    pecaTerminalHandler.menu();
                case "0" -> {
                    System.out.println("Saindo do sistema...");
                    return;
                }
                default ->
                    System.out.println("Opção inválida.");
            }
        }
    }
}
