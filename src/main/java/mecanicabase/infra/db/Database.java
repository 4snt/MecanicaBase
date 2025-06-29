package mecanicabase.infra.db;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import mecanicabase.model.operacao.Sistema;
import mecanicabase.model.operacao.Veiculo;

/**
 * Classe utilitária para persistência e carregamento dos dados da aplicação.
 * Responsável por ler e gravar o banco de dados em formato JSON, além de
 * inicializar entidades e atualizar contadores globais.
 */
public class Database {

    /**
     * Carrega os dados do banco de dados JSON para a memória da aplicação.
     * <p>
     * Se o banco não existir, cria com dados padrões. Atualiza o contador
     * global de veículos. Em caso de erro, exibe mensagem no stderr.
     * </p>
     */
    public static void load() {
        Path path = DatabasePathHelper.getDatabasePath();

        try {
            if (DatabaseFileHelper.ensureDatabaseExists(path)) {
                return; // Se for primeira execução, já criou dados padrões
            }

            var entidades = DatabaseJsonHelper.loadJson(path);

            EntityLoader.loadEntities(entidades);

            Sistema.setTotalVeiculos(Veiculo.instances.size());

        } catch (Exception e) {
            System.err.println("❌ Erro ao carregar banco: " + e.getMessage());
            // Para detalhes do erro, descomente a linha abaixo:
            // e.printStackTrace();
        }
    }

    /**
     * Salva os dados das entidades da aplicação no banco de dados JSON.
     * <p>
     * Em caso de erro, exibe mensagem no stderr.
     * </p>
     */
    public static void save() {
        Path path = DatabasePathHelper.getDatabasePath();

        try {
            var entidades = EntityLoader.dumpEntities();
            DatabaseJsonHelper.saveJson(path, (Map<String, List<?>>) (Map<?, ?>) entidades);
        } catch (Exception e) {
            System.err.println("❌ Erro ao salvar banco: " + e.getMessage());
            // Para detalhes do erro, descomente a linha abaixo:
            // e.printStackTrace();
        }
    }

}
