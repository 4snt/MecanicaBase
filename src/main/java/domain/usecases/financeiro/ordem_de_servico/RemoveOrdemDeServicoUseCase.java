package domain.usecases.financeiro.ordem_de_servico;

import java.util.UUID;

import domain.entities.financeiro.OrdemDeServico;

public class RemoveOrdemDeServicoUseCase {

    public boolean use(String id) {
        UUID uuid = UUID.fromString(id);
        return OrdemDeServico.instances.removeIf(os -> os.getId().equals(uuid));
    }
}
