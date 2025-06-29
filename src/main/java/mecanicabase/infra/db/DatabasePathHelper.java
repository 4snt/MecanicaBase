package mecanicabase.infra.db;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DatabasePathHelper {

    private static final String DB_NAME = "database.json";

    public static Path getDatabasePath() {
        Path external = Paths.get(System.getProperty("user.dir"), "data", DB_NAME);
        try {
            Files.createDirectories(external.getParent());
        } catch (Exception ignored) {
        }
        return external;
    }
}
