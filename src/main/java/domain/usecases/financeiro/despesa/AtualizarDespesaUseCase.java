package domain.usecases.financeiro.despesa;

import java.util.UUID;

import domain.entities.financeiro.Despesa;

/**
 * Caso de uso responsável por atualizar os dados de uma despesa existente.
 */
public class AtualizarDespesaUseCase {

    /**
     * Atualiza os dados de uma despesa com base no ID informado.
     *
     * @param id UUID da despesa a ser atualizada.
     * @param novaDescricao Nova descrição da despesa (opcional).
     * @param novoValor Novo valor da despesa (opcional).
     * @return A instância atualizada da despesa, ou null se não encontrada.
     */
    public Despesa use(UUID id, String novaDescricao, Float novoValor) {
        Despesa d = Despesa.instances.stream()
                .filter(dep -> dep.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (d == null) {
            return null;
        }

        if (novaDescricao != null) {
            d.setDescricao(novaDescricao);
        }
        if (novoValor != null) {
            d.setValor(novoValor);
        }

        return d;
    }
}
