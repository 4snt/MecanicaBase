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
     * Método principal de entrada da aplicação.
     *
     * @param args argumentos de linha de comando
     */
    public static void main(String[] args) {

        Database.load();

        boolean interfaceAtiva = mecanicabase.infra.EnvConfig.INSTANCE.getBoolean("INTERFACE", false);

        ApplicationContext context = new ApplicationContext();
        if (interfaceAtiva) {
            SwingUtilities.invokeLater(() -> {
                new MainSwingView(context).setVisible(true);
            });
        } else {
            context.router.start();
        }

        Database.save();
    }
}
