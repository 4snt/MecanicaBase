package mecanicabase.service.operacao.entrada_peca;

import java.util.NoSuchElementException;
import java.util.UUID;
import mecanicabase.model.operacao.EntradaPeca;
import mecanicabase.model.operacao.Peca;

/**
 * Caso de uso para criar manualmente uma entrada de peça.
 */
public class CriarManualEntradaPecaUseCase {

    /**
     * Cria uma nova entrada de peça com base nas informações fornecidas.
     *
     * @param pecaId ID da peça a ser adicionada
     * @param nomeFornecedor Nome do fornecedor
     * @param custo Custo unitário da peça
     * @param quantidade Quantidade de peças adquiridas
     * @return A instância da entrada de peça criada
     * @throws IllegalArgumentException se o custo for negativo
     * @throws NoSuchElementException se a peça não for encontrada
     */
    public EntradaPeca use(UUID pecaId, String nomeFornecedor, float custo, int quantidade) {
        Peca peca = Peca.buscarPorId(pecaId);

        if (peca == null) {
            throw new NoSuchElementException("Peça não encontrada");
        }

        if (custo < 0) {
            throw new IllegalArgumentException("Valor de custo inválido");
        }

        EntradaPeca entradaPeca = new EntradaPeca(peca.getId(), quantidade, custo, nomeFornecedor);

        peca.adicionarEstoque(quantidade);
        EntradaPeca.instances.add(entradaPeca);

        return entradaPeca;
    }
}
