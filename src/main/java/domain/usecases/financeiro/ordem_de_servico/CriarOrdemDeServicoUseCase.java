package domain.usecases.financeiro.ordem_de_servico;

import java.util.UUID;

import domain.entities.financeiro.OrdemDeServico;

public class CriarOrdemDeServicoUseCase {

    public OrdemDeServico use(UUID clienteId) {
        OrdemDeServico os = new OrdemDeServico(clienteId);
        OrdemDeServico.instances.add(os);
        return os;
    }
}
