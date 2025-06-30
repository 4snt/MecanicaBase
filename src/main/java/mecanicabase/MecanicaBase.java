package mecanicabase;

import javax.swing.SwingUtilities;
import mecanicabase.infra.ApplicationContext;
import mecanicabase.infra.db.Database;
import mecanicabase.view.swing.MainSwingView;

/**
 * Classe principal para inicialização da aplicação MecanicaBase. Responsável
 * por carregar o banco de dados, iniciar a interface gráfica ou o roteador, e
 * salvar o banco de dados ao final da execução.
 */
public class MecanicaBase {

    /**
     * Método principal de entrada da aplicação.tenh
     *
     * @param args argumentos de linha de comando
     */
    public static void main(String[] args) {
        Database.load();
        // Carrega variáveis do .env
        try {
            io.github.cdimascio.dotenv.Dotenv dotenv = io.github.cdimascio.dotenv.Dotenv.configure().ignoreIfMissing().load();
            dotenv.entries().forEach(entry -> {
                if (System.getenv(entry.getKey()) == null) {
                    System.setProperty(entry.getKey(), entry.getValue());
                }
            });
        } catch (Exception e) {
            System.out.println("[AVISO] Não foi possível carregar o arquivo .env: " + e.getMessage());
        }

        // Verifica se está em modo teste
        boolean modoTeste = Boolean.parseBoolean(System.getProperty("TEST_MODE", "false"));
        if (modoTeste) {
            System.out.println("Iniciando em modo teste...");
            java.util.Scanner scanner = new java.util.Scanner(System.in);
            new mecanicabase.view.Terminal.TestTerminalHandler(scanner).menu();
            Database.save();
            return;
        }

        // Carrega o banco de dados
        boolean interfaceAtiva = Boolean.parseBoolean(System.getProperty("INTERFACE", "false"));
        boolean usarFlyweight = Boolean.parseBoolean(System.getProperty("USE_FLYWEIGHT", "false"));

        System.out.println("Iniciando aplicação:");
        System.out.println("- Interface ativa: " + interfaceAtiva);
        System.out.println("- Flyweight ativado: " + usarFlyweight);

        ApplicationContext context = new ApplicationContext();

        if (interfaceAtiva) {
            SwingUtilities.invokeLater(() -> new MainSwingView(context).setVisible(true));
        } else {
            context.router.start();
        }

        // Salva o banco ao sair
        Database.save();
    }

}
