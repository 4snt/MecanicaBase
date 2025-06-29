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

    /**
     * Carrega as entidades a partir do mapa de dados lidos do banco.
     * <p>
     * Utiliza reflexão para popular as listas estáticas de instâncias de cada
     * entidade. Ignora entidades não presentes no mapa.
     * </p>
     *
     * @param entidades Mapa de entidades lidas do banco
     */
    public static void loadEntities(Map<String, List<Map<String, Object>>> entidades) {
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

            try {
                Field field = clazz.getDeclaredField("instances");
                field.setAccessible(true);
                @SuppressWarnings("unchecked")
                List<Entity> lista = (List<Entity>) field.get(null);
                lista.clear();
                lista.addAll(instancias);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                System.err.println("Erro ao carregar instâncias para " + clazz.getName() + ": " + e.getMessage());
            }
        }
    }

    /**
     * Exporta as listas de instâncias de cada entidade para um mapa
     * serializável.
     * <p>
     * Utiliza reflexão para acessar as listas estáticas de cada entidade.
     * </p>
     *
     * @return Mapa de entidades para serialização
     */
    public static Map<String, List<? extends Entity>> dumpEntities() {
        Map<String, List<? extends Entity>> entidades = new HashMap<>();
        for (Class<? extends Entity> clazz : ENTITIES) {
            try {
                Field f = clazz.getDeclaredField("instances");
                f.setAccessible(true);
                @SuppressWarnings("unchecked")
                List<? extends Entity> lista = (List<? extends Entity>) f.get(null);
                entidades.put(clazz.getName(), lista);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                System.err.println("Erro ao exportar instâncias de " + clazz.getName() + ": " + e.getMessage());
            }
        }
        return entidades;
    }
}
