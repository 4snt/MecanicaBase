package mecanicabase.service.operacao.peca;

import java.util.UUID;
import mecanicabase.model.operacao.Peca;

/**
 * Caso de uso responsável por remover uma peça com base no seu identificador.
 */
public class RemovePecaUseCase {

    /**
     * Remove uma peça da lista de instâncias com base no ID fornecido.
     *
     * @param id ID da peça em formato de string UUID.
     * @return {@code true} se a peça foi removida com sucesso, {@code false} se
     * não foi encontrada.
     */
    public boolean use(String id) {
        UUID uuid = UUID.fromString(id);
        return Peca.instances.removeIf(p -> p.getId().equals(uuid));
    }
}
