package mecanicabase.infra.db;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import mecanicabase.model.operacao.Sistema;
import mecanicabase.model.operacao.Veiculo;

public class Database {

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
            System.err.println("❌ Erro ao carregar banco:");
            e.printStackTrace();
        }
    }

    public static void save() {
        Path path = DatabasePathHelper.getDatabasePath();

        try {
            var entidades = EntityLoader.dumpEntities();
            DatabaseJsonHelper.saveJson(path, (Map<String, List<?>>) (Map<?, ?>) entidades);
        } catch (Exception e) {
            System.err.println("❌ Erro ao salvar banco:");
            e.printStackTrace();
        }
    }

}
