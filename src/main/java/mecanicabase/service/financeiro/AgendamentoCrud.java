package mecanicabase.service.financeiro;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import mecanicabase.core.Crud;
import mecanicabase.model.financeiro.Agendamento;
import mecanicabase.model.financeiro.ServicoItem;
import mecanicabase.model.financeiro.StatusAgendamento;
import mecanicabase.model.operacao.StatusVeiculo;
import mecanicabase.model.operacao.Veiculo;

public class AgendamentoCrud extends Crud<Agendamento> {

    @Override
    protected List<Agendamento> getInstancias() {
        return Agendamento.instances;
    }

    @Override
    protected UUID getId(Agendamento entidade) {
        return entidade.getId();
    }

    @Override
    protected Agendamento criarInstancia(Object... params) {
        LocalDateTime data = (LocalDateTime) params[0];
        String descricao = (String) params[1];
        UUID veiculoId = (UUID) params[2];
        UUID elevadorId = (UUID) params[3];
        UUID funcionarioId = (UUID) params[4];
        UUID servicoId = (UUID) params[5];
        UUID ordemDeServicoId = (UUID) params[6];

        return new Agendamento(data, descricao, veiculoId, elevadorId, funcionarioId, servicoId, ordemDeServicoId);
    }

    public List<Agendamento> buscarComFiltros(
            LocalDateTime dataInicio,
            LocalDateTime dataFinal,
            StatusAgendamento status,
            UUID funcionarioId,
            UUID clienteId
    ) {
        return Agendamento.instances.stream()
                .filter(a -> dataInicio == null || !a.getData().isBefore(dataInicio))
                .filter(a -> dataFinal == null || !a.getData().isAfter(dataFinal))
                .filter(a -> status == null || a.getStatus() == status)
                .filter(a -> funcionarioId == null || (a.getFuncionario() != null && a.getFuncionario().getId().equals(funcionarioId)))
                .filter(a -> {
                    if (clienteId == null) {
                        return true;
                    }
                    Veiculo v = a.getVeiculo();
                    return v != null && v.getCliente() != null && v.getCliente().getId().equals(clienteId);
                })
                .toList();
    }

    @Override
    protected void atualizarInstancia(Agendamento entidade, Object... params) {
        LocalDateTime data = (LocalDateTime) params[0];
        String descricao = (String) params[1];
        UUID veiculoId = (UUID) params[2];
        UUID elevadorId = (UUID) params[3];
        UUID funcionarioId = (UUID) params[4];
        UUID servicoId = (UUID) params[5];
        UUID ordemDeServicoId = (UUID) params[6];
        StatusAgendamento status = (StatusAgendamento) params[7];

        entidade.setData(data);
        entidade.setDescricaoProblema(descricao);
        entidade.setVeiculo(veiculoId);
        entidade.setElevador(elevadorId);
        entidade.setFuncionario(funcionarioId);
        entidade.setServico(servicoId);
        entidade.setOrdemDeServico(ordemDeServicoId);

        if (status != null) {
            float preco = status == StatusAgendamento.CANCELADO
                    ? entidade.getServico().getPreco() * 0.2f
                    : status == StatusAgendamento.CONCLUIDO
                            ? entidade.getServico().getPreco()
                            : -1f;

            if (preco >= 0f) {
                ServicoItem servicoItem = new ServicoItem(
                        entidade.getServico().getId(),
                        preco,
                        entidade.getOrdemDeServico().getId()
                );
                ServicoItem.instances.add(servicoItem);
                entidade.getOrdemDeServico().addServico(servicoItem.getId());

                if (entidade.getVeiculo() != null) {
                    entidade.getVeiculo().setStatus(StatusVeiculo.PRONTO);
                }
            }

            entidade.setStatus(status);
        }
    }

    @Override
    protected boolean validarAtualizacao(Agendamento atual, Object... params) {
        try {
            LocalDateTime data = (LocalDateTime) params[0];
            UUID elevadorId = (UUID) params[3];
            UUID funcionarioId = (UUID) params[4];

            if (data == null || funcionarioId == null) {
                return false;
            }

            // Valida elevador apenas se ele for obrigatório (ou seja, informado)
            boolean elevadorOcupado = false;
            if (elevadorId != null) {
                elevadorOcupado = Agendamento.instances.stream()
                        .anyMatch(a -> {
                            if (a.getId().equals(atual.getId())) {
                                return false;
                            }
                            if (a.getElevador() == null) {
                                return false;
                            }
                            return a.getElevador().getId().equals(elevadorId) && a.getData().equals(data);
                        });
            }

            // Funcionário sempre precisa ser validado
            boolean funcionarioOcupado = Agendamento.instances.stream()
                    .anyMatch(a -> !a.getId().equals(atual.getId())
                    && a.getFuncionario() != null
                    && a.getFuncionario().getId().equals(funcionarioId)
                    && a.getData().equals(data));

            // Somente retorna true se não houver conflitos com elevador nem funcionário
            return !elevadorOcupado && !funcionarioOcupado;
        } catch (Exception e) {
            return false;
        }
    }

}
