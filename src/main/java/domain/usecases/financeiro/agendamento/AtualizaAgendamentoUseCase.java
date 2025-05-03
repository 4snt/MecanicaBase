package domain.usecases.financeiro.agendamento;

import java.time.LocalDateTime;
import java.util.UUID;

import domain.entities.financeiro.Agendamento;
import domain.entities.financeiro.ServicoItem;
import domain.entities.financeiro.StatusAgendamento;
import domain.entities.operacao.Elevador;
import domain.entities.operacao.Servico;
import domain.entities.operacao.StatusVeiculo;
import domain.entities.operacao.Veiculo;
import domain.entities.usuarios.Funcionario;

/**
 * Caso de uso responsável por atualizar um agendamento existente no sistema.
 */
public class AtualizaAgendamentoUseCase {

    /**
     * Atualiza os dados de um agendamento específico com as informações
     * fornecidas.
     *
     * @param agendamentoId ID do agendamento a ser atualizado.
     * @param data Nova data e hora do agendamento.
     * @param descricaoProblema Nova descrição do problema relatado pelo
     * cliente.
     * @param veiculo Veículo a ser vinculado ao agendamento (se alterado).
     * @param elevador Elevador que será utilizado (se necessário).
     * @param funcionario Funcionário designado para realizar o serviço.
     * @param servico Serviço a ser executado.
     * @param status Novo status do agendamento (ex: AGENDADO, CANCELADO,
     * CONCLUIDO).
     * @return O objeto {@link Agendamento} atualizado.
     * @throws RuntimeException Se o agendamento, veículo, elevador, funcionário
     * ou serviço não forem encontrados.
     */
    public Agendamento use(UUID agendamentoId, LocalDateTime data, String descricaoProblema,
            Veiculo veiculo, Elevador elevador, Funcionario funcionario,
            Servico servico, StatusAgendamento status) {

        // Busca o agendamento pelo ID
        Agendamento agendamentoExiste = Agendamento.buscarPorId(agendamentoId);
        if (agendamentoExiste == null) {
            throw new RuntimeException("Agendamento não encontrado");
        }

        // Atualiza o agendamento
        for (Agendamento ag : Agendamento.instances) {
            if (ag.getId().equals(agendamentoId)) {

                if (data != null) {
                    ag.setData(data);
                }

                if (descricaoProblema != null) {
                    ag.setDescricaoProblema(descricaoProblema);
                }

                if (veiculo != null) {
                    if (!Veiculo.instances.contains(veiculo)) {
                        throw new RuntimeException("Veículo não existe");
                    }
                    ag.setVeiculo(veiculo.getId());
                }

                if (elevador != null) {
                    if (!Elevador.instances.contains(elevador)) {
                        throw new RuntimeException("Elevador não existe");
                    }
                    ag.setElevador(elevador.getId());
                }

                if (funcionario != null) {
                    if (!Funcionario.instances.contains(funcionario)) {
                        throw new RuntimeException("Funcionário não existe");
                    }
                    ag.setFuncionario(funcionario.getId());
                }

                if (servico != null) {
                    if (!Servico.instances.contains(servico)) {
                        throw new RuntimeException("Serviço não existe");
                    }
                    ag.setServico(servico.getId());
                }

                if (status != null) {
                    // Se for cancelado, cria item de serviço com 20% do valor original
                    if (status == StatusAgendamento.CANCELADO) {
                        ServicoItem servicoItem = new ServicoItem(
                                agendamentoExiste.getServico().getId(),
                                agendamentoExiste.getServico().getPreco() * 0.2f,
                                agendamentoExiste.getOrdemDeServico().getId()
                        );
                        ServicoItem.instances.add(servicoItem);
                        agendamentoExiste.getOrdemDeServico().addServico(servicoItem.getId());
                        agendamentoExiste.getVeiculo().setStatus(StatusVeiculo.PRONTO);
                    }

                    // Se for concluído, cria item de serviço com valor cheio
                    if (status == StatusAgendamento.CONCLUIDO) {
                        ServicoItem servicoItem = new ServicoItem(
                                agendamentoExiste.getServico().getId(),
                                agendamentoExiste.getServico().getPreco(),
                                agendamentoExiste.getOrdemDeServico().getId()
                        );
                        ServicoItem.instances.add(servicoItem);
                        agendamentoExiste.getOrdemDeServico().addServico(servicoItem.getId());
                        agendamentoExiste.getVeiculo().setStatus(StatusVeiculo.PRONTO);
                    }

                    ag.setStatus(status);
                }

                return ag;
            }
        }

        return null;
    }
}
