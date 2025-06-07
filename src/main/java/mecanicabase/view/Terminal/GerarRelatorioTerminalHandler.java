package mecanicabase.view.Terminal;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import mecanicabase.service.financeiro.relatorios.GerarRelatorioUseCase;

/**
 * Handler responsável por interagir com o usuário via terminal para gerar um
 * relatório de vendas e serviços com base em um intervalo de datas.
 */
public class GerarRelatorioTerminalHandler {

    private final Scanner scanner;
    private final GerarRelatorioUseCase gerarRelatorio = new GerarRelatorioUseCase();

    /**
     * Construtor que recebe o scanner utilizado para leitura da entrada do
     * usuário.
     *
     * @param scanner Scanner compartilhado do sistema.
     */
    public GerarRelatorioTerminalHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Exibe o menu para o usuário informar o período desejado e imprime o
     * relatório gerado.
     */
    public void menu() {
        System.out.println("\n=== GERAR RELATÓRIO DE VENDAS E SERVIÇOS ===");

        try {
            System.out.print("Data de início (formato yyyy-MM-dd): ");
            String inicioStr = scanner.nextLine();
            LocalDateTime inicio = LocalDateTime.parse(inicioStr + "T00:00:00");

            System.out.print("Data final (formato yyyy-MM-dd): ");
            String fimStr = scanner.nextLine();
            LocalDateTime fim = LocalDateTime.parse(fimStr + "T23:59:59");

            System.out.println("\n🔍 Gerando relatório, aguarde...\n");
            String relatorio = gerarRelatorio.use(inicio, fim);
            System.out.println(relatorio);

        } catch (DateTimeParseException e) {
            System.out.println("❌ Erro: formato de data inválido. Use yyyy-MM-dd.");
        } catch (Exception e) {
            System.out.println("❌ Erro ao gerar o relatório: " + e.getMessage());
        }
    }
}
