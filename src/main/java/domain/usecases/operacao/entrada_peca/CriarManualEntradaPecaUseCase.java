package domain.usecases.operacao.entrada_peca;

import java.util.UUID;

import domain.entities.operacao.EntradaPeca;
import domain.entities.operacao.Peca;

public class CriarManualEntradaPecaUseCase {

    public EntradaPeca use(UUID pecaId, String nomeFornecedor, float custo, int quantidade) {
        Peca peca = Peca.buscarPorId(pecaId);

        if (peca == null) {
            throw new RuntimeException("Peca não encontrada");
        }

        if (custo < 0) {
            throw new RuntimeException("Valor de custo inválido");
        }

        EntradaPeca entradaPeca = new EntradaPeca(peca.getId(), quantidade, custo, nomeFornecedor);

        peca.adicionarEstoque(quantidade);

        EntradaPeca.instances.add(entradaPeca);

        return entradaPeca;
    }
}
