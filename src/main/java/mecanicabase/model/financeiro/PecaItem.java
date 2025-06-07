package mecanicabase.model.financeiro;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mecanicabase.core.Entity;
import mecanicabase.model.operacao.Peca;

/**
 * Representa um item de peça associado a uma ordem de serviço.
 */
public class PecaItem extends Entity {

    /**
     * Lista de instâncias de PecaItem.
     */
    public static List<PecaItem> instances = new ArrayList<>();

    private UUID peca;
    private int quantidade;
    private float valorUnitario;

    private final UUID ordemDeServico;

    /**
     * Construtor da classe PecaItem.
     *
     * @param peca UUID da peça associada.
     * @param quantidade Quantidade da peça.
     * @param valorUnitario Valor unitário da peça.
     * @param ordemDeServico UUID da ordem de serviço associada.
     */
    public PecaItem(UUID peca, int quantidade, float valorUnitario, UUID ordemDeServico) {
        this.peca = peca;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.ordemDeServico = ordemDeServico;
    }

    /**
     * Obtém a peça associada a este item.
     *
     * @return A instância de Peca correspondente ao UUID armazenado.
     */
    public Peca getPeca() {
        return Peca.instances.stream()
                .filter(p -> p.getId().equals(this.peca))
                .findFirst()
                .orElse(null);
    }

    /**
     * Define o UUID da peça associada.
     *
     * @param peca UUID da peça.
     */
    public void setPeca(UUID peca) {
        this.peca = peca;
    }

    /**
     * Obtém a quantidade da peça.
     *
     * @return Quantidade.
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Define a quantidade da peça.
     *
     * @param quantidade Quantidade.
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * Obtém o valor unitário da peça.
     *
     * @return Valor unitário.
     */
    public float getValorUnitario() {
        return valorUnitario;
    }

    /**
     * Define o valor unitário da peça.
     *
     * @param valorUnitario Valor unitário.
     */
    public void setValorUnitario(float valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    /**
     * Obtém a ordem de serviço associada a este item.
     *
     * @return Ordem de serviço.
     */
    public OrdemDeServico getOrdemDeServico() {
        return OrdemDeServico.buscarPorId(this.ordemDeServico);
    }

    /**
     * Busca uma instância de PecaItem pelo seu ID.
     *
     * @param id UUID do PecaItem.
     * @return Instância correspondente de PecaItem, ou null se não encontrada.
     */
    public static PecaItem buscarPorId(UUID id) {
        return PecaItem.instances.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return String.format(
                "PecaItem [ID=%s, Peça='%s', Quantidade=%d, Valor Unitário=%.2f, Ordem de Serviço=%s]",
                getId(),
                getPeca() != null ? getPeca().getNome() : "N/A",
                quantidade,
                valorUnitario,
                ordemDeServico
        );
    }
}
