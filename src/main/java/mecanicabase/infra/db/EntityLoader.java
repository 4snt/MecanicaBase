package mecanicabase.infra.db;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import mecanicabase.core.Entity;

public class EntityLoader {

    private static final List<Class<? extends Entity>> ENTITIES = EntityRegistry.getEntities();
    private static final Gson GSON = DatabaseJsonHelper.getGson();

    public static void loadEntities(Map<String, List<Map<String, Object>>> entidades) throws Exception {
        if (entidades == null) {
            return;
        }

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

    public static Map<String, List<? extends Entity>> dumpEntities() throws Exception {
        Map<String, List<? extends Entity>> entidades = new HashMap<>();
        for (Class<? extends Entity> clazz : ENTITIES) {
            Field f = clazz.getDeclaredField("instances");
            f.setAccessible(true);
            @SuppressWarnings("unchecked")
            List<? extends Entity> lista = (List<? extends Entity>) f.get(null);
            entidades.put(clazz.getName(), lista);
        }
        return entidades;
    }
}
