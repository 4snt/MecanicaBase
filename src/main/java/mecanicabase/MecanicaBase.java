package mecanicabase;

import javax.swing.SwingUtilities;

import mecanicabase.infra.ApplicationContext;
import mecanicabase.infra.EnvConfig;
import mecanicabase.infra.db.Database;
import mecanicabase.view.swing.MainSwingView;

public class MecanicaBase {

    public static void main(String[] args) {

        Database.load();

        boolean interfaceAtiva = EnvConfig.INSTANCE.get("INTERFACE", "true").equalsIgnoreCase("true");

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
