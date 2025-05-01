package presentation.router;

import java.util.Scanner;

import presentation.terminal.ClienteTerminalHandler;

public class TerminalRouter {
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1 - Clientes");
            System.out.println("2 - Oficinas");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1" -> new ClienteTerminalHandler(scanner).menu();
                case "2" -> System.out.println("Handler de oficina ainda não implementado.");
                case "0" -> {
                    System.out.println("Encerrando...");
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }
}
