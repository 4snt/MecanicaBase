package infra.db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import core.*;
import domain.entities.financeiro.*;
import domain.entities.operacao.*;
import domain.entities.usuarios.*;

public class Database {
    private static final List<Class<? extends Entity>> entities = Arrays.asList(
            Agendamento.class,
            Caixa.class,
            Despesa.class,
            PecaItem.class,
            ServicoItem.class,
            Elevador.class,
            Oficina.class,
            Peca.class,
            Servico.class,
            Veiculo.class,
            Administrador.class,
            Funcionario.class,
            Cliente.class
    );

    private static final Gson gson = new Gson();
    private static final String FILE_PATH = "database.json";

    public static void load() {
        try {
            if (!Files.exists(Paths.get(FILE_PATH))) {
                System.out.println("Arquivo não encontrado, iniciando com dados fixos.");
                Elevador.init();
                Oficina.init();
                return;
            }

            String json = Files.readString(Paths.get(FILE_PATH));
            Map<String, Object> rawData = gson.fromJson(json, new TypeToken<Map<String, Object>>() {
            }.getType());

            Map<String, List<Map<String, Object>>> entidades = (Map<String, List<Map<String, Object>>>) rawData
                    .get("entidades");

            if (entidades != null) {
                for (Class<? extends Entity> clazz : entities) {
                    String className = clazz.getName();
                    List<Map<String, Object>> objetos = entidades.get(className);
                    if (objetos == null)
                        continue;

                    List<Entity> carregadas = new ArrayList<>();
                    for (Map<String, Object> obj : objetos) {
                        String objJson = gson.toJson(obj);
                        Entity entidade = gson.fromJson(objJson, clazz);
                        carregadas.add(entidade);
                    }

                    try {
                        Field field = clazz.getDeclaredField("instances");
                        field.setAccessible(true);
                        List<Entity> lista = (List<Entity>) field.get(null);
                        lista.clear();
                        lista.addAll(carregadas);
                    } catch (Exception e) {
                        System.out.println("Erro ao preencher instâncias de " + className);
                        e.printStackTrace();
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Erro ao carregar banco de dados:");
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            Map<String, Object> exportData = new HashMap<>();
            Map<String, List<? extends Entity>> entidades = new HashMap<>();

            for (Class<? extends Entity> clazz : entities) {
                try {
                    Field field = clazz.getDeclaredField("instances");
                    field.setAccessible(true);
                    List<? extends Entity> lista = (List<? extends Entity>) field.get(null);
                    entidades.put(clazz.getName(), lista);
                } catch (Exception e) {
                    System.out.println("Erro ao acessar instâncias de " + clazz.getSimpleName());
                    e.printStackTrace();
                }
            }

            exportData.put("entidades", entidades);

            String json = gson.toJson(exportData);
            Files.write(Paths.get(FILE_PATH), json.getBytes());

        } catch (Exception e) {
            System.out.println("Erro ao salvar banco de dados:");
            e.printStackTrace();
        }
    }
}