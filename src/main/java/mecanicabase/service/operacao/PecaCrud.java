package mecanicabase.service.operacao;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import mecanicabase.core.Crud;
import mecanicabase.flyweight.PecaFlyweightFactory;
import mecanicabase.infra.EnvConfig;
import mecanicabase.model.operacao.EntradaPeca;
import mecanicabase.model.operacao.Peca;

public class PecaCrud extends Crud<Peca> {

    private boolean validarDuplicidade = true;
    private final boolean modoFlyweight;
    private final PecaFlyweightFactory flyweightFactory;

    public PecaCrud() {
        this.modoFlyweight = EnvConfig.INSTANCE.getBoolean("USE_FLYWEIGHT", true);
        this.flyweightFactory = new PecaFlyweightFactory();
    }

    public void setValidarDuplicidade(boolean validarDuplicidade) {
        this.validarDuplicidade = validarDuplicidade;
    }

    @Override
    public List<Peca> getInstancias() {
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

        if (modoFlyweight) {
            Peca p = flyweightFactory.getPeca(nome, valor);
            p.adicionarEstoque(quantidade);
            return p;
        } else {
            return new Peca(nome, valor, quantidade);
        }
    }

    @Override
    protected void atualizarInstancia(Peca peca, Object... params) {
        String novoNome = (String) params[0];
        Float novoValor = (Float) params[1];
        Integer novaQuantidade = (Integer) params[2];

        if (novoNome != null && !novoNome.equalsIgnoreCase(peca.getNome())) {
            if (validarDuplicidade) {
                boolean nomeRepetido = Peca.instances.stream()
                        .anyMatch(p -> p.getNome().equalsIgnoreCase(novoNome));
                if (nomeRepetido) {
                    throw new RuntimeException("Já existe outra peça com esse nome.");
                }
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

    public EntradaPeca registrarEntrada(UUID pecaId, String nomeFornecedor, float custo, int quantidade) {
        Peca peca = buscarPorId(pecaId);
        if (peca == null) {
            throw new NoSuchElementException("Peça não encontrada");
        }

        if (custo < 0) {
            throw new IllegalArgumentException("Valor de custo inválido");
        }

        EntradaPeca entrada = new EntradaPeca(peca.getId(), quantidade, custo, nomeFornecedor);
        EntradaPeca.instances.add(entrada);
        peca.adicionarEstoque(quantidade);

        return entrada;
    }

    public int getTotalCompartilhadosFlyweight() {
        return modoFlyweight ? flyweightFactory.getTotalCompartilhados() : 0;
    }

    public void limparFlyweight() {
        if (modoFlyweight) {
            flyweightFactory.limpar();
        }
    }

    public java.util.Map<java.util.UUID, Peca> index() {
        java.util.Map<java.util.UUID, Peca> index = new java.util.HashMap<>();
        for (Peca p : getInstancias()) {
            index.put(p.getId(), p);
        }
        return index;
    }
}
