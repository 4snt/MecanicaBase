package mecanicabase.infra.db;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DatabasePathHelper {

    private static final String DB_NAME = "database.json";

    public static Path getDatabasePath() {
        Path external = Paths.get(System.getProperty("user.dir"), "data", DB_NAME);
        if (Files.exists(external) || Files.notExists(external.getParent())) {
            return external;
        }

        try {
            Path jarDir = Paths
                    .get(Database.class.getProtectionDomain().getCodeSource().getLocation().toURI())
                    .getParent();
            return jarDir.resolve("data").resolve(DB_NAME);
        } catch (Exception e) {
            return external;
        }
    }
}
