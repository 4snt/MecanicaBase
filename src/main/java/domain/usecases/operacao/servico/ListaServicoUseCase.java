package domain.usecases.operacao.servico;

import java.util.List;

import domain.entities.operacao.Servico;

public class ListaServicoUseCase {

    public List<Servico> use() {
        return Servico.instances;
    }
}
