package domain.usecases.operacao.peca;

import java.util.UUID;

import domain.entities.operacao.Peca;

public class AtualizaPecaUseCase {
    public Peca use(String id, String nome, Float valor, Integer quantidade) {
        UUID uuid = UUID.fromString(id);

        for (Peca peca : Peca.instances) {
            if (peca.getId().equals(uuid)) {
                if (nome != null) peca.setNome(nome);
                if (valor != null) peca.setValor(valor);
                if (quantidade != null) peca.setQuantidade(quantidade);
                return peca;
            }
        }

        return null;
    }
}
