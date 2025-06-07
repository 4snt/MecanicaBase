package mecanicabase.service.operacao.peca;

import java.util.List;
import mecanicabase.model.operacao.Peca;

/**
 * Caso de uso responsável por listar todas as peças cadastradas.
 */
public class ListaPecaUseCase {

    /**
     * Retorna a lista de todas as peças existentes.
     *
     * @return Lista de instâncias de {@link Peca}.
     */
    public List<Peca> use() {
        return Peca.instances;
    }
}
