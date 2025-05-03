package domain.usecases.financeiro.ordem_de_servico;

import java.util.List;

import domain.entities.financeiro.OrdemDeServico;

public class ListaOrdemDeServicoUseCase {

    public List<OrdemDeServico> use() {
        return OrdemDeServico.instances;
    }
}
