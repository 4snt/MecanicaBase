package mecanicabase.infra.db;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
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
import com.google.gson.JsonSyntaxException;
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
 * Classe responsável pela persistência de dados no sistema. Gerencia a leitura
 * e escrita das entidades para o arquivo JSON, incluindo as entidades do
 * sistema, como Agendamento, Cliente, Funcionario, entre outras. Utiliza a
 * biblioteca Gson para realizar a conversão entre objetos Java e formato JSON.
 *
 * O arquivo JSON utilizado para armazenar as entidades é denominado
 * "database.json".
 *
 * A classe oferece duas funcionalidades principais: 1. Carregar os dados do
 * arquivo JSON e popular as instâncias das entidades no sistema. 2. Salvar as
 * instâncias das entidades no formato JSON no arquivo "database.json".
 */
public class Database {

    // Lista de classes de entidades que são salvas e carregadas do banco de dados
    private static final List<Class<? extends Entity>> entities = List.of(
            Agendamento.class, CategoriaDespesa.class, Despesa.class, PecaItem.class, ServicoItem.class,
            Elevador.class, Peca.class, Servico.class, Veiculo.class, Administrador.class, Funcionario.class, Cliente.class
    );

    // Instância do Gson para serialização e desserialização, incluindo a customização para LocalDateTime
    private static final Gson gson = new GsonBuilder()
            // Serializa LocalDateTime para formato ISO (yyyy-MM-dd'T'HH:mm:ss)
            .registerTypeAdapter(LocalDateTime.class,
                    (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context)
                    -> new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            )
            // Desserializa LocalDateTime a partir do formato ISO
            .registerTypeAdapter(LocalDateTime.class,
                    (JsonDeserializer<LocalDateTime>) (json, typeOfT, context)
                    -> LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            )
            // Habilita a impressão formatada do JSON
            .setPrettyPrinting()
            .create();

    // Caminho para o arquivo onde os dados do banco são armazenados
    private static String getDatabasePath() {
        return isRunningFromJar()
                ? "data/database.json" // fora do .jar (para escrita)
                : "src/main/resources/data/database.json";  // usado no NetBeans
    }

    private static boolean isRunningFromJar() {
        String className = Database.class.getName().replace('.', '/') + ".class";
        String classPath = Database.class.getClassLoader().getResource(className).toString();
        return classPath.startsWith("jar:");
    }

    /**
     * Carrega os dados de todas as entidades persistidas a partir do arquivo
     * JSON. Se o arquivo não for encontrado, inicializa com dados fixos de
     * entidades como Elevador e Administrador.
     *
     * As instâncias das entidades são carregadas e mapeadas a partir dos dados
     * do arquivo JSON. Após o carregamento, o total de veículos é atualizado no
     * sistema.
     */
    public static void load() {
        try {
            // Verifica se o arquivo de persistência existe
            if (!Files.exists(Paths.get(getDatabasePath()))) {
                System.out.println("Arquivo não encontrado, iniciando com dados fixos.");
                Elevador.init(); // Inicializa dados fixos de Elevadores
                Administrador.init(); // Inicializa dados fixos de Administradores
                return;
            }

            // Lê o conteúdo do arquivo JSON
            String json = Files.readString(Paths.get(getDatabasePath())
            );
            // Converte o conteúdo JSON em um mapa de dados
            Map<String, Object> rawData = gson.fromJson(json, new TypeToken<Map<String, Object>>() {
            }.getType());

            // Mapeia as entidades para serem carregadas
            Map<String, List<Map<String, Object>>> entidades = (Map<String, List<Map<String, Object>>>) rawData.get("entidades");

            // Itera sobre as classes das entidades para carregar seus dados
            if (entidades != null) {
                for (Class<? extends Entity> clazz : entities) {
                    String className = clazz.getName();
                    List<Map<String, Object>> objetos = entidades.get(className);
                    if (objetos == null) {
                        continue;
                    }

                    // Para cada classe, cria instâncias dos objetos a partir dos dados do JSON
                    List<Entity> carregadas = new ArrayList<>();
                    for (Map<String, Object> obj : objetos) {
                        String objJson = gson.toJson(obj);
                        Entity entidade = gson.fromJson(objJson, clazz);
                        carregadas.add(entidade);
                    }

                    // Preenche a lista de instâncias da entidade
                    try {
                        Field field = clazz.getDeclaredField("instances");
                        field.setAccessible(true);
                        List<Entity> lista = (List<Entity>) field.get(null);
                        lista.clear();
                        lista.addAll(carregadas);
                    } catch (IllegalAccessException | NoSuchFieldException | SecurityException e) {
                        System.out.println("Erro ao preencher instâncias de " + className);
                    }
                }
            }

            // Atualiza o total de veículos no sistema com base nas instâncias carregadas
            Sistema.setTotalVeiculos(Veiculo.instances.size());

        } catch (JsonSyntaxException | IOException e) {
            System.out.println("Erro ao carregar banco de dados:");
        }
    }

    /**
     * Salva as instâncias de todas as entidades no formato JSON no arquivo de
     * persistência "database.json".
     *
     * O método converte as instâncias de todas as entidades para o formato JSON
     * e as escreve no arquivo. O arquivo contém os dados atualizados de todas
     * as entidades presentes no sistema.
     */
    public static void save() {
        try {
            Map<String, Object> exportData = new HashMap<>();
            Map<String, List<? extends Entity>> entidades = new HashMap<>();

            // Itera sobre as classes de entidades para coletar as instâncias das mesmas
            for (Class<? extends Entity> clazz : entities) {
                try {
                    Field field = clazz.getDeclaredField("instances");
                    field.setAccessible(true);
                    List<? extends Entity> lista = (List<? extends Entity>) field.get(null);
                    entidades.put(clazz.getName(), lista);
                } catch (IllegalAccessException | NoSuchFieldException | SecurityException e) {
                    System.out.println("Erro ao acessar instâncias de " + clazz.getSimpleName());
                }
            }

            // Preenche o mapa com os dados das entidades
            exportData.put("entidades", entidades);

            // Converte o mapa de entidades para JSON
            String json = gson.toJson(exportData);

            // Escreve o JSON no arquivo de persistência
            Files.write(Paths.get(getDatabasePath()), json.getBytes());

        } catch (IOException e) {
            System.out.println("Erro ao salvar banco de dados:");
        }
    }
}
