package mecanicabase.service.operacao.peca;

import java.util.UUID;
import mecanicabase.model.operacao.Peca;

/**
 * Caso de uso responsável por buscar uma peça pelo seu identificador.
 */
public class BuscaPorIdPecaUseCase {

    /**
     * Busca uma peça pelo seu ID.
     *
     * @param id ID da peça no formato de string UUID.
     * @return A instância de {@link Peca} correspondente ao ID, ou {@code null}
     * se não encontrada.
     */
    public Peca use(String id) {
        UUID uuid = UUID.fromString(id);
        return Peca.instances.stream()
                .filter(p -> p.getId().equals(uuid))
                .findFirst()
                .orElse(null);
    }
}
