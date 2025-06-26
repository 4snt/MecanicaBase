package mecanicabase.infra;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import io.github.cdimascio.dotenv.Dotenv;

public class EnvConfig {

    public static final EnvConfig INSTANCE = new EnvConfig();

    private final Dotenv dotenv;
    private final File workingDir;

    private EnvConfig() {
        this.workingDir = detectWorkingDir();
        ensureEnvFile();
        this.dotenv = Dotenv.configure()
                .directory(workingDir.getAbsolutePath())
                .ignoreIfMissing()
                .load();
    }

    private File detectWorkingDir() {
        try {
            String path = Paths.get(
                    EnvConfig.class.getProtectionDomain().getCodeSource().getLocation().toURI()
            ).toFile().getParentFile().getAbsolutePath();
            return new File(path);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private void ensureEnvFile() {
        File envFile = new File(workingDir, ".env");
        if (!envFile.exists()) {
            try (FileWriter writer = new FileWriter(envFile)) {
                writer.write("# Arquivo .env criado automaticamente\n");
                writer.write("MODO=PRODUCAO\n");
            } catch (IOException ignored) {
            }
        }
    }

    public String get(String key) {
        return dotenv.get(key);
    }

    public String get(String key, String defaultValue) {
        return dotenv.get(key, defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        String value = dotenv.get(key);
        if (value == null) {
            return defaultValue;
        }
        return value.equalsIgnoreCase("true") || value.equalsIgnoreCase("1");
    }

    public boolean isRunningFromJar() {
        String protocol = EnvConfig.class.getResource("").getProtocol();
        return protocol.equals("jar");
    }

    public File getWorkingDir() {
        return workingDir;
    }
}
