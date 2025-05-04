package domain.usecases.operacao.servico;

import domain.entities.operacao.Servico;
import domain.entities.operacao.TipoElevador;
import domain.entities.usuarios.TipoFuncionario;

/**
 * Caso de uso responsável pela criação de um novo serviço.
 */
public class CriarServicoUseCase {

    /**
     * Cria um novo serviço com os dados fornecidos e o adiciona à lista de
     * instâncias.
     *
     * @param tipo Tipo ou nome do serviço.
     * @param preco Preço do serviço.
     * @param descricao Descrição do serviço.
     * @param duracao Duração estimada do serviço em minutos.
     * @param tipoFuncionario Tipo de funcionário necessário para o serviço.
     * @param tipoElevador Tipo de elevador necessário para o serviço, se
     * aplicável.
     * @param usaElevador Define se o serviço utiliza elevador.
     * @return Instância do {@link Servico} criada.
     */
    public Servico use(String tipo, float preco, String descricao, Integer duracao, TipoFuncionario tipoFuncionario, TipoElevador tipoElevador, Boolean usaElevador) {
        Servico servico = new Servico(tipo, preco, descricao);
        if (duracao != null) {
            servico.setDuracao(duracao);
        }
        if (tipoFuncionario != null) {
            servico.setTipoFuncionario(tipoFuncionario);
        }
        if (tipoElevador != null) {
            servico.setTipoElevador(tipoElevador);
        }
        if (usaElevador != null) {
            servico.setUsaElevador(usaElevador);
        }

        Servico.instances.add(servico);
        return servico;
    }
}
