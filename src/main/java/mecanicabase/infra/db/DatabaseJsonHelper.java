package mecanicabase.infra.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

public class DatabaseJsonHelper {

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class,
                    (JsonSerializer<LocalDateTime>) (src, t, c)
                    -> new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
            .registerTypeAdapter(LocalDateTime.class,
                    (JsonDeserializer<LocalDateTime>) (j, t, c)
                    -> LocalDateTime.parse(j.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            .setPrettyPrinting()
            .create();

    public static Map<String, List<Map<String, Object>>> loadJson(Path path) throws IOException {
        String json = Files.readString(path);
        Map<String, Object> raw = GSON.fromJson(json, new TypeToken<Map<String, Object>>() {
        }.getType());
        return (Map<String, List<Map<String, Object>>>) raw.get("entidades");
    }

    public static void saveJson(Path path, Map<String, List<?>> entidades) throws IOException {
        String json = GSON.toJson(Map.of("entidades", entidades));
        Files.createDirectories(path.getParent());
        Files.writeString(path, json);
    }

    public static Gson getGson() {
        return GSON;
    }
}
