package mecanicabase.service.operacao.servico;

import java.util.UUID;
import mecanicabase.model.operacao.Servico;
import mecanicabase.model.operacao.TipoElevador;
import mecanicabase.model.usuarios.TipoFuncionario;

/**
 * Caso de uso responsável por atualizar os dados de um serviço existente.
 */
public class AtualizaServicoUseCase {

    /**
     * Atualiza os dados de um serviço com base no ID fornecido.
     *
     * @param id ID do serviço (em formato de string UUID).
     * @param tipo Novo tipo do serviço (pode ser {@code null} para não
     * alterar).
     * @param preco Novo preço do serviço (pode ser {@code null} para não
     * alterar).
     * @param descricao Nova descrição do serviço (pode ser {@code null} para
     * não alterar).
     * @param duracao Nova duração do serviço em minutos (pode ser
     * {@code null}).
     * @param tipoFuncionario Novo tipo de funcionário responsável (pode ser
     * {@code null}).
     * @param tipoElevador Novo tipo de elevador necessário (pode ser
     * {@code null}).
     * @param usaElevador Indica se o serviço utiliza elevador (pode ser
     * {@code null}).
     * @return A instância do serviço atualizada ou {@code null} se não
     * encontrada.
     */
    public Servico use(String id, String tipo, Float preco, String descricao, Integer duracao, TipoFuncionario tipoFuncionario, TipoElevador tipoElevador, Boolean usaElevador) {
        UUID uuid = UUID.fromString(id);

        for (Servico servico : Servico.instances) {
            if (servico.getId().equals(uuid)) {
                if (tipo != null) {
                    servico.setTipo(tipo);
                }
                if (preco != null) {
                    servico.setPreco(preco);
                }
                if (descricao != null) {
                    servico.setDescricao(descricao);
                }
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
                return servico;
            }
        }

        return null;
    }
}
