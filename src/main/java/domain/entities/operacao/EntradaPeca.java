package domain.entities.operacao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import core.Entity;

public class EntradaPeca extends Entity {

    public static final List<EntradaPeca> instances = new ArrayList<>();

    private int quantidade;
    private String nomeFornecedor;
    private float custo;
    private UUID peca;

    public EntradaPeca(UUID peca, int quantidade, float custo, String nomeFornecedor) {
        this.peca = peca;
        this.quantidade = quantidade;
        this.custo = custo;
        this.nomeFornecedor = nomeFornecedor;
    }

    public UUID getPecaId() {
        return peca;
    }

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

    @Override
    public String toString() {
        Peca pecaObj = getPeca();
        return """
            EntradaPeca {
                ID: %s
                Peca: %s
                Quantidade: %d
                Custo: %.2f
            }
            """.formatted(
                getId(),
                pecaObj != null ? pecaObj.getNome() : "Desconhecida",
                quantidade,
                custo
        );
    }
}
