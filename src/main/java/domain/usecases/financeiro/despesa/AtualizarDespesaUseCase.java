package domain.usecases.financeiro.despesa;

import java.util.UUID;

import domain.entities.financeiro.Despesa;

public class AtualizarDespesaUseCase {

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
