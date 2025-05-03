package domain.usecases.operacao.servico;

import domain.entities.operacao.Servico;

public class CriarServicoUseCase {

    public Servico use(String tipo, float preco, String descricao) {
        Servico servico = new Servico(tipo, preco, descricao);
        Servico.instances.add(servico);
        return servico;
    }
}
