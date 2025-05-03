package presentation.terminal;

import java.util.Scanner;

import domain.entities.operacao.StatusVeiculo;
import domain.entities.operacao.Veiculo;
import domain.usecases.operacao.veiculo.AtualizaVeiculoUseCase;

public class OficinaTerminalHandler {

    private final Scanner scanner;

    public OficinaTerminalHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public void menu() {
        while (true) {
            System.out.println("\n=== MENU OFICINA ===");
            System.out.println("1 - Atualizar Veículo");
            System.out.println("0 - Voltar");

            System.out.print("Escolha: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1" -> atualizarVeiculo();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void atualizarVeiculo() {
        System.out.print("ID do veículo: ");
        String id = scanner.nextLine();

        System.out.print("Novo modelo (ou ENTER): ");
        String modelo = scanner.nextLine();

        System.out.print("Nova placa (ou ENTER): ");
        String placa = scanner.nextLine();

        System.out.print("Ano de fabricação (ou ENTER): ");
        String anoInput = scanner.nextLine();
        Integer ano = anoInput.isEmpty() ? null : Integer.parseInt(anoInput);

        System.out.print("Nova cor (ou ENTER): ");
        String cor = scanner.nextLine();

        System.out.print("Novo status (ATIVO/INATIVO): ");
        String statusInput = scanner.nextLine();
        StatusVeiculo status = statusInput.isEmpty() ? null : StatusVeiculo.valueOf(statusInput.toUpperCase());

        AtualizaVeiculoUseCase useCase = new AtualizaVeiculoUseCase();
        Veiculo atualizado = useCase.use(id, null, modelo, placa, ano, cor, status);

        if (atualizado != null) {
            System.out.println("Veículo atualizado: " + atualizado);
        } else {
            System.out.println("Veículo não encontrado.");
        }
    }
}
