package domain.entities.operacao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import core.Entity;

/**
 * Representa uma entrada de peça no estoque, contendo referência à peça,
 * quantidade, custo e nome do fornecedor.
 */
public class EntradaPeca extends Entity {

    /**
     * Lista estática que simula a persistência das entradas de peças.
     */
    public static final List<EntradaPeca> instances = new ArrayList<>();

    private int quantidade;
    private String nomeFornecedor;
    private float custo;
    private UUID peca;

    /**
     * Construtor para criar uma nova entrada de peça.
     *
     * @param peca ID da peça associada
     * @param quantidade Quantidade de peças recebidas
     * @param custo Custo unitário
     * @param nomeFornecedor Nome do fornecedor
     */
    public EntradaPeca(UUID peca, int quantidade, float custo, String nomeFornecedor) {
        this.peca = peca;
        this.quantidade = quantidade;
        this.custo = custo;
        this.nomeFornecedor = nomeFornecedor;
    }

    public UUID getPecaId() {
        return peca;
    }

    /**
     * Retorna o objeto Peca associado com base no ID.
     */
    public Peca getPeca() {
        return Peca.instances.stream()
                .filter(p -> p.getId().equals(this.peca))
                .findFirst()
                .orElse(null);
    }

    public void setPeca(UUID peca) {
        this.peca = peca;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getNomeFornecedor() {
        return nomeFornecedor;
    }

    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }

    public float getCusto() {
        return custo;
    }

    public void setCusto(float custo) {
        this.custo = custo;
    }

    /**
     * Retorna uma representação textual da entrada de peça.
     */
    @Override
    public String toString() {
        Peca pecaObj = getPeca();
        return String.format(
                "EntradaPeca {\n"
                + "  ID: %s\n"
                + "  Peca: %s\n"
                + "  Quantidade: %d\n"
                + "  Custo: %.2f\n"
                + "}",
                getId(),
                pecaObj != null ? pecaObj.getNome() : "Desconhecida",
                quantidade,
                custo
        );
    }
}
