package com.mycompany.mecanicabase.service.financeiro.ordem_de_servico;

import java.util.UUID;
import com.mycompany.mecanicabase.model.financeiro.Agendamento;
import com.mycompany.mecanicabase.model.financeiro.OrdemDeServico;
import com.mycompany.mecanicabase.model.financeiro.StatusAgendamento;
import com.mycompany.mecanicabase.model.financeiro.StatusOrdemDeServico;
import com.mycompany.mecanicabase.model.operacao.StatusVeiculo;
import com.mycompany.mecanicabase.model.operacao.Veiculo;

/**
 * Caso de uso responsável por atualizar os dados de uma Ordem de Serviço.
 */
public class AtualizaOrdemDeServicoUseCase {

    /**
     * Atualiza uma Ordem de Serviço existente com um novo cliente ou novo
     * status. Se o status for definido como CONCLUIDO, verifica se todos os
     * agendamentos estão encerrados. Também atualiza o status do veículo para
     * ENTREGUE.
     *
     * @param id ID da Ordem de Serviço como string.
     * @param clienteId Novo ID do cliente associado (opcional).
     * @param status Novo status da ordem (opcional).
     * @return A Ordem de Serviço atualizada, ou null se não encontrada.
     * @throws RuntimeException Se houver agendamento ainda AGENDADO ao tentar
     * concluir a OS.
     */
    public OrdemDeServico use(String id, UUID clienteId, StatusOrdemDeServico status) {
        UUID uuid = UUID.fromString(id);

        for (OrdemDeServico os : OrdemDeServico.instances) {
            if (os.getId().equals(uuid)) {
                if (clienteId != null) {
                    os.setClienteId(clienteId);
                }
                if (status != null) {
                    if (status == StatusOrdemDeServico.CONCLUIDO) {
                        for (Agendamento agendamento : os.getAgendamentos()) {
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
