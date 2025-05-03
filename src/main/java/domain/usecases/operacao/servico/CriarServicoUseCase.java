package domain.usecases.operacao.servico;

import domain.entities.operacao.Servico;

/**
 * Caso de uso responsável pela criação de um novo serviço.
 */
public class CriarServicoUseCase {

    /**
     * Cria um novo serviço com os dados fornecidos e o adiciona à lista de
     * instâncias.
     *
     * @param tipo Tipo do serviço.
     * @param preco Preço do serviço.
     * @param descricao Descrição do serviço.
     * @return A instância do serviço criado.
     */
    public Servico use(String tipo, float preco, String descricao) {
        Servico servico = new Servico(tipo, preco, descricao);
        Servico.instances.add(servico);
        return servico;
    }
}
