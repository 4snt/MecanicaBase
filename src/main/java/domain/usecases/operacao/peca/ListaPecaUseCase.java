package domain.usecases.operacao.peca;

import java.util.List;

import domain.entities.operacao.Peca;

public class ListaPecaUseCase {
    public List<Peca> use() {
        return Peca.instances;
    }
}
