package mecanicabase.infra;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvConfig {

    public static final EnvConfig INSTANCE = new EnvConfig();

    private final Dotenv dotenv;
    private final File workingDir;

    private EnvConfig() {
        this.workingDir = new File(System.getProperty("user.dir"));
        System.out.println("[EnvConfig] Working dir: " + workingDir.getAbsolutePath());

        File envFile = new File(workingDir, ".env");
        System.out.println("[EnvConfig] Procurando .env em: " + envFile.getAbsolutePath());

        if (!envFile.exists()) {
            createDefaultEnvFile(envFile);
        }

        this.dotenv = Dotenv.configure()
                .directory(workingDir.getAbsolutePath())
                .filename(".env") // explicitamente define o nome
                .ignoreIfMalformed() // loga erros de parsing
                .ignoreIfMissing() // ignora se .env estiver ausente
                .load();
    }

    private void createDefaultEnvFile(File envFile) {
        try (FileWriter writer = new FileWriter(envFile)) {
            writer.write("# .env criado automaticamente\n");
            writer.write("MODO=PRODUCAO\n");
        } catch (IOException e) {
            System.err.println("[EnvConfig] Falha ao criar .env: " + e.getMessage());
        }
    }

    public String get(String key) {
        String value = dotenv.get(key);
        System.out.println("[EnvConfig] " + key + " = " + value);
        return value;
    }

    public String get(String key, String defaultValue) {
        String value = dotenv.get(key, defaultValue);
        System.out.println("[EnvConfig] " + key + " = " + value + " (default: " + defaultValue + ")");
        return value;
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        String value = dotenv.get(key);
        if (value == null) {
            return defaultValue;
        }
        boolean result = value.equalsIgnoreCase("true") || value.equalsIgnoreCase("1");
        System.out.println("[EnvConfig] " + key + " = " + value + " (interpreted as " + result + ")");
        return result;
    }

    public boolean isRunningFromJar() {
        String protocol = EnvConfig.class.getResource("").getProtocol();
        return protocol.equals("jar");
    }

    public File getWorkingDir() {
        return workingDir;
    }
}
