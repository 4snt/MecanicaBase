package mecanicabase;

import javax.swing.SwingUtilities;

import mecanicabase.infra.ApplicationContext;
import mecanicabase.infra.db.Database;
import mecanicabase.view.swing.MainSwingView;

public class MecanicaBase {

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
