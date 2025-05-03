package infra.db;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import core.Entity;
import domain.entities.financeiro.Agendamento;
import domain.entities.financeiro.CategoriaDespesa;
import domain.entities.financeiro.Despesa;
import domain.entities.financeiro.PecaItem;
import domain.entities.financeiro.ServicoItem;
import domain.entities.operacao.Elevador;
import domain.entities.operacao.Oficina;
import domain.entities.operacao.Peca;
import domain.entities.operacao.Servico;
import domain.entities.operacao.Veiculo;
import domain.entities.usuarios.Administrador;
import domain.entities.usuarios.Cliente;
import domain.entities.usuarios.Funcionario;

public class Database {

    private static final List<Class<? extends Entity>> entities = List.of((Class<? extends Entity>) (Class<? extends Entity>) Agendamento.class,
            (Class<? extends Entity>) CategoriaDespesa.class,
            (Class<? extends Entity>) Despesa.class,
            (Class<? extends Entity>) PecaItem.class,
            (Class<? extends Entity>) ServicoItem.class,
            (Class<? extends Entity>) Elevador.class,
            //    (Class<? extends Entity>) Oficina.class,
            (Class<? extends Entity>) Peca.class,
            (Class<? extends Entity>) Servico.class,
            (Class<? extends Entity>) Veiculo.class,
            (Class<? extends Entity>) Administrador.class,
            (Class<? extends Entity>) Funcionario.class,
            (Class<? extends Entity>) Cliente.class
    );

    private static final Gson gson = new Gson();
    private static final String FILE_PATH = "database.json";

    public static void load() {
        try {
            if (!Files.exists(Paths.get(FILE_PATH))) {
                System.out.println("Arquivo não encontrado, iniciando com dados fixos.");
                Elevador.init();
                Oficina.init();
                Administrador.init();
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
                    if (objetos == null) {
                        continue;
                    }

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
                    } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
                        System.out.println("Erro ao preencher instâncias de " + className);
                    }
                }
            }

        } catch (JsonSyntaxException | IOException e) {
            System.out.println("Erro ao carregar banco de dados:");
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
                } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
                    System.out.println("Erro ao acessar instâncias de " + clazz.getSimpleName());
                }
            }

            exportData.put("entidades", entidades);

            String json = gson.toJson(exportData);
            Files.write(Paths.get(FILE_PATH), json.getBytes());

        } catch (IOException e) {
            System.out.println("Erro ao salvar banco de dados:");
        }
    }
}
