package domain.usecases.financeiro.agendamento;

import java.time.LocalDateTime;
import java.util.UUID;

import domain.entities.financeiro.Agendamento;
import domain.entities.financeiro.ServicoItem;
import domain.entities.financeiro.StatusAgendamento;
import domain.entities.operacao.Elevador;
import domain.entities.operacao.Servico;
import domain.entities.operacao.Veiculo;
import domain.entities.usuarios.Funcionario;

public class AtualizaAgendamentoUseCase {

    public Agendamento use(UUID agendamentoId, LocalDateTime data, String descricaoProblema,
                           Veiculo veiculo, Elevador elevador, Funcionario funcionario,
                           Servico servico, StatusAgendamento status) {

        Agendamento agendamentoExiste = Agendamento.buscarPorId(agendamentoId);

        if (agendamentoExiste == null) {
            throw new RuntimeException("Agendamento não encontrado");
        }

        for (Agendamento ag : Agendamento.instances) {
            if (ag.getId().equals(agendamentoId)) {
                if (data != null) ag.setData(data);
                if (descricaoProblema != null) ag.setDescricaoProblema(descricaoProblema);

                if (veiculo != null) {
                    boolean veiculoExiste = Veiculo.instances.contains(veiculo);
                    if (!veiculoExiste) {
                        throw new RuntimeException("Veículo não existe");
                    }
                    ag.setVeiculo(veiculo.getId());
                }

                if (elevador != null) {
                    boolean elevadorExiste = Elevador.instances.contains(elevador);
                    if (!elevadorExiste) {
                        throw new RuntimeException("Elevador não existe");
                    }
                    ag.setElevador(elevador.getId());
                }

                if (funcionario != null) {
                    boolean funcionarioExiste = Funcionario.instances.contains(funcionario);
                    if (!funcionarioExiste) {
                        throw new RuntimeException("Funcionário não existe");
                    }
                    ag.setFuncionario(funcionario.getId());
                }

                if (servico != null) {
                    boolean servicoExiste = Servico.instances.contains(servico);
                    if (!servicoExiste) {
                        throw new RuntimeException("Serviço não existe");
                    }
                    ag.setServico(servico.getId());
                }

                if (status != null) {
                    if (status == StatusAgendamento.CANCELADO) {
                        ServicoItem servicoItem = new ServicoItem(
                            agendamentoExiste.getServico().getId(),
                            agendamentoExiste.getServico().getPreco() * 0.2f,
                            agendamentoExiste.getOrdemDeServico().getId()
                        );
                        ServicoItem.instances.add(servicoItem);
                        agendamentoExiste.getOrdemDeServico().addServico(servicoItem.getId());
                    }

                    if (status == StatusAgendamento.CONCLUIDO) {
                        ServicoItem servicoItem = new ServicoItem(
                            agendamentoExiste.getServico().getId(),
                            agendamentoExiste.getServico().getPreco(),
                            agendamentoExiste.getOrdemDeServico().getId()
                        );
                        ServicoItem.instances.add(servicoItem);
                        agendamentoExiste.getOrdemDeServico().addServico(servicoItem.getId());
                    }

                    ag.setStatus(status);
                }

                return ag;
            }
        }

        return null;
    }
}
