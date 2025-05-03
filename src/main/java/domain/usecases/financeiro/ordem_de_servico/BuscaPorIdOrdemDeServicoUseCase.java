package domain.usecases.financeiro.ordem_de_servico;

import java.util.UUID;

import domain.entities.financeiro.OrdemDeServico;

public class BuscaPorIdOrdemDeServicoUseCase {

    public OrdemDeServico use(String id) {
        UUID uuid = UUID.fromString(id);

        for (OrdemDeServico os : OrdemDeServico.instances) {
            if (os.getId().equals(uuid)) {
                return os;
            }
        }

        return null;
    }
}
