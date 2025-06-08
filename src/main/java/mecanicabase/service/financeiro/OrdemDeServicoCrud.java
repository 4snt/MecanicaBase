package mecanicabase.service.financeiro;

import java.util.List;
import java.util.UUID;
import mecanicabase.core.Crud;
import mecanicabase.model.financeiro.Agendamento;
import mecanicabase.model.financeiro.OrdemDeServico;
import mecanicabase.model.financeiro.PecaItem;
import mecanicabase.model.financeiro.StatusAgendamento;
import mecanicabase.model.financeiro.StatusOrdemDeServico;
import mecanicabase.model.operacao.Peca;
import mecanicabase.model.operacao.StatusVeiculo;
import mecanicabase.model.operacao.Veiculo;

public class OrdemDeServicoCrud extends Crud<OrdemDeServico> {

    @Override
    protected List<OrdemDeServico> getInstancias() {
        return OrdemDeServico.instances;
    }

    @Override
    protected UUID getId(OrdemDeServico entidade) {
        return entidade.getId();
    }

    @Override
    protected OrdemDeServico criarInstancia(Object... params) {
        UUID clienteId = (UUID) params[0];
        return new OrdemDeServico(clienteId);
    }

    @Override
    protected void atualizarInstancia(OrdemDeServico ordemDeServico, Object... params) {
        UUID clienteId = (UUID) params[0];
        StatusOrdemDeServico status = (StatusOrdemDeServico) params[1];

        if (clienteId != null) {
            ordemDeServico.setClienteId(clienteId);
        }

        if (status != null) {
            if (status == StatusOrdemDeServico.CONCLUIDO) {
                for (Agendamento agendamento : ordemDeServico.getAgendamentos()) {
                    if (agendamento.getStatus() == StatusAgendamento.AGENDADO) {
                        throw new RuntimeException("Ainda há um agendamento aberto nesta Ordem de Serviço");
                    }
                    Veiculo veiculo = agendamento.getVeiculo();
                    veiculo.setStatus(StatusVeiculo.ENTREGUE);
                }
            }
            ordemDeServico.setStatus(status);
        }
    }

    @Override
    protected boolean validarCriacao(Object... params) {
        try {
            UUID clienteId = (UUID) params[0];
            return clienteId != null;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected boolean validarAtualizacao(OrdemDeServico os, Object... params) {
        try {
            UUID clienteId = (UUID) params[0];
            StatusOrdemDeServico status = (StatusOrdemDeServico) params[1];

            boolean clienteValido = clienteId == null || clienteId.toString().length() > 0;
            boolean statusValido = status == null || status instanceof StatusOrdemDeServico;

            return clienteValido && statusValido;
        } catch (Exception e) {
            return false;
        }
    }

    // ✅ Substitui o antigo use(UUID pecaItemId)
    public void removerPecaItem(UUID pecaItemId) {
        PecaItem pecaItem = PecaItem.buscarPorId(pecaItemId);

        if (pecaItem == null) {
            throw new RuntimeException("PeçaItem não encontrado");
        }

        OrdemDeServico os = pecaItem.getOrdemDeServico();

        if (os == null) {
            throw new RuntimeException("Ordem de Serviço não encontrada");
        }

        if (os.getStatus() != StatusOrdemDeServico.ABERTO) {
            throw new RuntimeException("Esta Ordem de Serviço já foi fechada");
        }

        os.removePeca(pecaItem.getId());
    }

    // ✅ Substitui o antigo use(UUID, UUID, int)
    public PecaItem venderPeca(UUID ordemDeServicoId, UUID pecaId, int quantidade) {
        OrdemDeServico ordemDeServico = OrdemDeServico.buscarPorId(ordemDeServicoId);
        if (ordemDeServico == null) {
            throw new RuntimeException("Ordem de Serviço não foi encontrada");
        }

        Peca peca = Peca.buscarPorId(pecaId);
        if (peca == null) {
            throw new RuntimeException("Peça não foi encontrada");
        }

        if (peca.getQuantidade() < quantidade) {
            throw new RuntimeException("Quantidade da peça insuficiente");
        }

        // Atualiza o estoque
        peca.setQuantidade(peca.getQuantidade() - quantidade);

        PecaItem pecaItem = new PecaItem(peca.getId(), quantidade, peca.getValor(), ordemDeServico.getId());
        PecaItem.instances.add(pecaItem);

        ordemDeServico.addPeca(pecaItem.getId());

        return pecaItem;
    }
}
