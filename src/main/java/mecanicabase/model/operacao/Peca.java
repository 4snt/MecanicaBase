package mecanicabase.model.operacao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mecanicabase.core.Entity;
import mecanicabase.flyweight.Flyweight;

/**
 * Representa uma peça utilizada em ordens de serviço dentro da oficina. Aplica
 * o padrão Flyweight como ConcreteFlyweight (nome e valor são intrínsecos).
 */
public class Peca extends Entity implements Flyweight {

    public static List<Peca> instances = new ArrayList<>();

    private String nome;
    private float valor;
    private int quantidade;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void reduzirEstoque(int quantidade) {
        if (quantidade <= this.quantidade) {
            this.quantidade -= quantidade;
        } else {
            throw new IllegalArgumentException("Quantidade em estoque insuficiente.");
        }
    }

    public void adicionarEstoque(int quantidade) {
        this.quantidade += quantidade;
    }

    public static Peca buscarPorId(UUID id) {
        return Peca.instances.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Peca(String nome, float valor) {
        this(nome, valor, 0); // usa o outro construtor
    }

    public Peca(String nome, float valor, int quantidade) {
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
    }

    public int getEstoque() {
        return this.quantidade;
    }

    /**
     * Implementação do padrão Flyweight. Recebe e opera sobre o estado
     * extrínseco (ex: quantidade solicitada, ordem, etc).
     */
    @Override
    public void operar(Object estadoExterno) {
        System.out.println("Operando com peça '" + nome + "' com estado externo: " + estadoExterno);
    }

    @Override
    public String toString() {
        return String.format(
                "Peca [ID=%s, Nome='%s', Valor=%.2f, Quantidade=%d]",
                getId(),
                nome,
                valor,
                quantidade
        );
    }
}
