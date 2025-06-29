package mecanicabase.infra.db;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DatabasePathHelper {

    private static final String DB_NAME = "database.json";

    /**
     * Retorna o caminho absoluto do arquivo de banco de dados JSON.
     * <p>
     * Garante que o diretório 'data' exista, criando-o se necessário.
     * </p>
     *
     * @return Caminho absoluto do arquivo database.json
     */
    public static Path getDatabasePath() {
        Path external = Paths.get(System.getProperty("user.dir"), "data", DB_NAME);
        try {
            Files.createDirectories(external.getParent());
        } catch (java.io.IOException ignored) {
            // Ignora falha ao criar diretório, pois pode já existir ou não ser crítico
        }
        return external;
    }
}
