package mecanicabase.infra.db;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import mecanicabase.core.Entity;
import mecanicabase.model.financeiro.Agendamento;
import mecanicabase.model.financeiro.CategoriaDespesa;
import mecanicabase.model.financeiro.Despesa;
import mecanicabase.model.financeiro.PecaItem;
import mecanicabase.model.financeiro.ServicoItem;
import mecanicabase.model.operacao.Elevador;
import mecanicabase.model.operacao.Peca;
import mecanicabase.model.operacao.Servico;
import mecanicabase.model.operacao.Sistema;
import mecanicabase.model.operacao.Veiculo;
import mecanicabase.model.usuarios.Administrador;
import mecanicabase.model.usuarios.Cliente;
import mecanicabase.model.usuarios.Funcionario;

/**
 * Persistência de dados em JSON.
 *
 * • Em **dev** (NetBeans) o arquivo fica em
 * <pre>target/data/database.json</pre><br>
 * • Em **produção** (JAR) fica ao lado do JAR, em
 * <pre>./data/database.json</pre><br>
 * Se não existir, copia um <code>/data/database.json</code> empacotado no JAR
 * ou cria dados-padrão (Elevador.init / Administrador.init).
 */
public class Database {

    /* ---------- CONFIG ----------- */
    private static final String DB_NAME = "database.json";
    private static final String DB_FOLDER = "data";

    /* ---------- ENTIDADES ---------- */
    private static final List<Class<? extends Entity>> ENTITIES = List.of(
            Agendamento.class, CategoriaDespesa.class, Despesa.class,
            PecaItem.class, ServicoItem.class,
            Elevador.class, Peca.class, Servico.class, Veiculo.class,
            Administrador.class, Funcionario.class, Cliente.class
    );

    /* ---------- JSON (Gson) -------- */
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class,
                    (JsonSerializer<LocalDateTime>) (src, t, c)
                    -> new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
            .registerTypeAdapter(LocalDateTime.class,
                    (JsonDeserializer<LocalDateTime>) (j, t, c)
                    -> LocalDateTime.parse(j.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            .setPrettyPrinting()
            .create();

    /* ---------- PATH ÚNICO -------- */
    private static Path dbPath() {
        // Tenta: <user.dir>/data/database.json
        Path external = Paths.get(System.getProperty("user.dir"), "data", "database.json");
        if (Files.exists(external) || Files.notExists(external.getParent())) {
            // (cria a pasta depois, se precisar)
            return external;
        }

        // Fallback: pasta do JAR (quando programaticamente for diferente de user.dir)
        try {
            Path jarDir = Paths
                    .get(Database.class.getProtectionDomain().getCodeSource().getLocation().toURI())
                    .getParent();
            return jarDir.resolve("data").resolve("database.json");
        } catch (Exception e) {
            return external; // último recurso
        }
    }

    /* ---------- LOAD --------------- */
    @SuppressWarnings("unchecked")
    public static void load() {
        Path path = dbPath();
        try {
            // 1) se não existir, tenta copiar um seed embutido
            if (Files.notExists(path)) {
                copySeedIfExists(path);
            }

            if (Files.notExists(path)) {
                System.out.println("Primeira execução: criando dados padrão.");
                Elevador.init();
                Administrador.init();
                return;
            }

            String json = Files.readString(path);
            Map<String, Object> raw = GSON.fromJson(json,
                    new TypeToken<Map<String, Object>>() {
                    }.getType());
            Map<String, List<Map<String, Object>>> entidades
                    = (Map<String, List<Map<String, Object>>>) raw.get("entidades");

            if (entidades != null) {
                for (Class<? extends Entity> clazz : ENTITIES) {
                    List<Map<String, Object>> objetos = entidades.get(clazz.getName());
                    if (objetos == null) {
                        continue;
                    }

                    List<Entity> instancias = new ArrayList<>();
                    for (Map<String, Object> o : objetos) {
                        instancias.add(GSON.fromJson(GSON.toJson(o), clazz));
                    }

                    Field field = clazz.getDeclaredField("instances");
                    field.setAccessible(true);
                    @SuppressWarnings("unchecked")
                    List<Entity> lista = (List<Entity>) field.get(null);
                    lista.clear();
                    lista.addAll(instancias);
                }
            }
            Sistema.setTotalVeiculos(Veiculo.instances.size());

        } catch (Exception e) {
            System.err.println("Erro ao carregar banco:");
            e.printStackTrace();
        }
    }

    /* ---------- SAVE --------------- */
    public static void save() {
        Path path = dbPath();
        try {
            Map<String, List<? extends Entity>> entidades = new HashMap<>();
            for (Class<? extends Entity> clazz : ENTITIES) {
                Field f = clazz.getDeclaredField("instances");
                f.setAccessible(true);
                @SuppressWarnings("unchecked")
                List<? extends Entity> lista = (List<? extends Entity>) f.get(null);
                entidades.put(clazz.getName(), lista);
            }

            String json = GSON.toJson(Map.of("entidades", entidades));

            Files.createDirectories(path.getParent());
            Files.writeString(path, json);

        } catch (Exception e) {
            System.err.println("Erro ao salvar banco:");
            e.printStackTrace();
        }
    }

    /* ---------- HELPERS ------------ */
    /**
     * Copia /data/database.json (dentro do JAR) para o caminho externo se
     * existir.
     */
    private static void copySeedIfExists(Path target) throws IOException {
        try (InputStream in = Database.class.getResourceAsStream("/data/" + DB_NAME)) {
            if (in != null) {
                Files.createDirectories(target.getParent());
                Files.copy(in, target);
                System.out.println("Seed database copiado para: " + target);
            }
        }
    }
}
