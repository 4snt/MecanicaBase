package mecanicabase.service.operacao;

import java.util.List;
import java.util.UUID;
import mecanicabase.core.Crud;
import mecanicabase.model.operacao.Peca;

public class PecaCrud extends Crud<Peca> {

    @Override
    protected List<Peca> getInstancias() {
        return Peca.instances;
    }

    @Override
    protected UUID getId(Peca entidade) {
        return entidade.getId();
    }

    @Override
    protected Peca criarInstancia(Object... params) {
        String nome = (String) params[0];
        float valor = (float) params[1];
        int quantidade = (int) params[2];

        boolean nomeRepetido = Peca.instances.stream()
                .anyMatch(p -> p.getNome().equalsIgnoreCase(nome));

        if (nomeRepetido) {
            throw new RuntimeException("Já existe uma peça com esse nome.");
        }

        Peca peca = new Peca(nome, valor, quantidade);
        Peca.instances.add(peca);
        return peca;
    }

    @Override
    protected void atualizarInstancia(Peca peca, Object... params) {
        String novoNome = (String) params[0];
        Float novoValor = (Float) params[1];
        Integer novaQuantidade = (Integer) params[2];

        if (novoNome != null && !novoNome.equalsIgnoreCase(peca.getNome())) {
            boolean nomeRepetido = Peca.instances.stream()
                    .anyMatch(p -> p.getNome().equalsIgnoreCase(novoNome));
            if (nomeRepetido) {
                throw new RuntimeException("Já existe outra peça com esse nome.");
            }
            peca.setNome(novoNome);
        }

        if (novoValor != null) {
            peca.setValor(novoValor);
        }

        if (novaQuantidade != null) {
            peca.setQuantidade(novaQuantidade);
        }
    }

}
