package domain.usecases.financeiro.agendamento;

import java.time.LocalDateTime;
import java.util.UUID;

import domain.entities.financeiro.Agendamento;
import domain.entities.financeiro.OrdemDeServico;
import domain.entities.financeiro.StatusAgendamento;
import domain.entities.operacao.Elevador;
import domain.entities.operacao.Servico;
import domain.entities.operacao.Veiculo;
import domain.entities.usuarios.Funcionario;

public class AtualizaAgendamentoUseCase {

    public Agendamento use(String id, LocalDateTime data, String descricaoProblema,
                           Veiculo veiculo, Elevador elevador, Funcionario funcionario,
                           Servico servico, OrdemDeServico ordem, StatusAgendamento status) {
        UUID uuid = UUID.fromString(id);

        for (Agendamento ag : Agendamento.instances) {
            if (ag.getId().equals(uuid)) {
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

                if (ordem != null) {
                    boolean ordemExiste = OrdemDeServico.instances.contains(ordem);
                    if (!ordemExiste) {
                        throw new RuntimeException("Ordem de Serviço não existe");
                    }
                    ag.setOrdemDeServico(ordem.getId());
                }

                if (status != null) ag.setStatus(status);

                return ag;
            }
        }

        return null;
    }
}
