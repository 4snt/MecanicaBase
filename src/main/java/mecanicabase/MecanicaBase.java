package mecanicabase;

import javax.swing.SwingUtilities;
import mecanicabase.infra.ApplicationContext;
import mecanicabase.infra.db.Database;
import mecanicabase.view.swing.MainSwingView;

/**
 * Classe principal responsável pela inicialização da aplicação MecanicaBase.
 * <p>
 * Esta classe realiza as seguintes funções:
 * <ul>
 *   <li>Carrega o banco de dados e as variáveis de ambiente do arquivo .env.</li>
 *   <li>Verifica o modo de execução (teste, interface gráfica ou terminal).</li>
 *   <li>Inicia a interface gráfica Swing ou o roteador de terminal conforme configuração.</li>
 *   <li>Garante a persistência dos dados ao final da execução.</li>
 * </ul>
 * </p>
 *
 * @author Murilo Santiago, Pavila Miranda, Luis Claudio Prado
 * @since 1.0
 */
public class MecanicaBase {

    /**
     * Método principal de entrada da aplicação.
     * <p>
     * Executa a sequência de inicialização:
     * <ol>
     *   <li>Carrega o banco de dados.</li>
     *   <li>Carrega variáveis de ambiente do arquivo .env, se disponível.</li>
     *   <li>Verifica se está em modo teste e executa o menu de testes, se necessário.</li>
     *   <li>Verifica se a interface gráfica deve ser ativada ou se deve iniciar o roteador de terminal.</li>
     *   <li>Salva o banco de dados ao final da execução.</li>
     * </ol>
     * </p>
     *
     * @param args argumentos de linha de comando (não utilizados diretamente)
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
