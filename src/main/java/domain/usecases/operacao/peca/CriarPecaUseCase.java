package domain.usecases.operacao.peca;

import domain.entities.operacao.Peca;

public class CriarPecaUseCase {
    public Peca use(String nome, float valor, int quantidade) {
        Peca peca = new Peca(nome, valor, quantidade);
        Peca.instances.add(peca);
        return peca;
    }
}
