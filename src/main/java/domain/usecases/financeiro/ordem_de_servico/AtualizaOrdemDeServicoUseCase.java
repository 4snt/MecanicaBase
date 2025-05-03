package domain.usecases.financeiro.ordem_de_servico;

import java.util.List;
import java.util.UUID;

import domain.entities.financeiro.Agendamento;
import domain.entities.financeiro.OrdemDeServico;
import domain.entities.financeiro.StatusAgendamento;
import domain.entities.financeiro.StatusOrdemDeServico;
import domain.entities.operacao.StatusVeiculo;
import domain.entities.operacao.Veiculo;

public class AtualizaOrdemDeServicoUseCase {

    public OrdemDeServico use(String id, UUID clienteId, StatusOrdemDeServico status) {
        UUID uuid = UUID.fromString(id);

        for (OrdemDeServico os : OrdemDeServico.instances) {
            if (os.getId().equals(uuid)) {
                if (clienteId != null) {
                    os.setClienteId(clienteId);
                }
                if (status != null) {
                    if (status == StatusOrdemDeServico.CONCLUIDO) {
                        List<Agendamento> agendamentos = os.getAgendamentos();
                        for (Agendamento agendamento : agendamentos) {
                            if (agendamento.getStatus() == StatusAgendamento.AGENDADO) {
                                throw new RuntimeException("Ainda há um agendamento aberto nesta Ordem de Serviço");
                            }

                            Veiculo veiculo = agendamento.getVeiculo();

                            veiculo.setStatus(StatusVeiculo.ENTREGUE);
                        }
                    }
                    os.setStatus(status);
                }
                return os;
            }
        }

        return null;
    }
}
