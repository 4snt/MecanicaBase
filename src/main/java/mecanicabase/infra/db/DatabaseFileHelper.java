package mecanicabase.infra.db;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import mecanicabase.model.operacao.Elevador;
import mecanicabase.model.usuarios.Administrador;

public class DatabaseFileHelper {

    public static boolean ensureDatabaseExists(Path target) throws IOException {
        if (Files.notExists(target)) {
            copySeedIfExists(target);
        }

        if (Files.notExists(target)) {
            System.out.println("Primeira execução: criando dados padrão.");
            Elevador.init();
            Administrador.init();
            return true;
        }
        return false;
    }

    private static void copySeedIfExists(Path target) throws IOException {
        try (InputStream in = Database.class.getResourceAsStream("/data/database.json")) {
            if (in != null) {
                Files.createDirectories(target.getParent());
                Files.copy(in, target);
                System.out.println("Seed database copiado para: " + target);
            }
        }
    }
}
