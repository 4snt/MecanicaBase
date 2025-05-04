package presentation.Terminal;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import domain.usecases.financeiro.relatorios.GerarBalancoUseCase;

/**
 * Handler responsável por interagir com o usuário via terminal para gerar o
 * balanço financeiro e operacional do sistema com base em um intervalo de
 * datas.
 */
public class GerarBalancoTerminalHandler {

    private final Scanner scanner;
    private final GerarBalancoUseCase gerarBalanco = new GerarBalancoUseCase();

    /**
     * Construtor que recebe o scanner do terminal.
     *
     * @param scanner Scanner usado para leitura da entrada do usuário.
     */
    public GerarBalancoTerminalHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Inicia o processo de geração do balanço solicitando ao usuário o
     * intervalo de datas desejado e exibindo o relatório gerado.
     */
    public void menu() {
        System.out.println("\n=== GERAR BALANÇO ===");

        try {
            System.out.print("Data de início (formato yyyy-MM-dd): ");
            String inicioStr = scanner.nextLine();
            LocalDateTime inicio = LocalDateTime.parse(inicioStr + "T00:00:00");

            System.out.print("Data final (formato yyyy-MM-dd): ");
            String fimStr = scanner.nextLine();
            LocalDateTime fim = LocalDateTime.parse(fimStr + "T23:59:59");

            System.out.println("\n🔍 Gerando balanço, aguarde...\n");
            String relatorio = gerarBalanco.use(inicio, fim);
            System.out.println(relatorio);

        } catch (DateTimeParseException e) {
            System.out.println("❌ Erro: formato de data inválido. Use yyyy-MM-dd.");
        } catch (Exception e) {
            System.out.println("❌ Erro ao gerar o balanço: " + e.getMessage());
        }
    }
}
