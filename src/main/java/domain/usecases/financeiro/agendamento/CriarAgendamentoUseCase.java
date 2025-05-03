package domain.usecases.financeiro.agendamento;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import domain.entities.financeiro.Agendamento;
import domain.entities.financeiro.OrdemDeServico;
import domain.entities.financeiro.StatusOrdemDeServico;
import domain.entities.operacao.Elevador;
import domain.entities.operacao.Servico;
import domain.entities.operacao.Veiculo;
import domain.entities.usuarios.Funcionario;

/**
 * Caso de uso responsável por criar um novo agendamento. Aloca automaticamente
 * um funcionário e, se necessário, um elevador disponível.
 */
public class CriarAgendamentoUseCase {

    /**
     * Cria um novo agendamento com base nas informações fornecidas e recursos
     * disponíveis.
     *
     * @param horarioDesejado Data e hora desejada para o agendamento.
     * @param servico Serviço a ser executado.
     * @param descricaoProblema Descrição do problema relatado pelo cliente.
     * @param veiculo Veículo que será atendido.
     * @param ordemDeServico Ordem de serviço à qual o agendamento será
     * vinculado.
     * @return O agendamento criado.
     * @throws RuntimeException Se a ordem de serviço não estiver aberta, ou não
     * houver funcionário/elevador disponível.
     */
    public Agendamento use(
            LocalDateTime horarioDesejado,
            Servico servico,
            String descricaoProblema,
            Veiculo veiculo,
            OrdemDeServico ordemDeServico
    ) {
        if (ordemDeServico.getStatus() != StatusOrdemDeServico.ABERTO) {
            throw new RuntimeException("A Ordem de Serviço não está aberta.");
        }

        LocalDateTime horarioFinal = horarioDesejado.plusMinutes(servico.getDuracao());

        List<Funcionario> funcionariosDisponiveis = Funcionario.buscarFuncionariosDisponiveis(
                servico.getTipoFuncionario(),
                horarioDesejado,
                horarioFinal
        );

        if (funcionariosDisponiveis.isEmpty()) {
            throw new RuntimeException("Nenhum funcionário do tipo " + servico.getTipoFuncionario() + " disponível.");
        }

        Funcionario funcionarioAlocado = funcionariosDisponiveis.get(0);

        UUID elevadorId = null;

        if (servico.usaElevador()) {
            List<Elevador> elevadoresDisponiveis = Elevador.buscarElevadoresDisponiveis(
                    servico.getTipoElevador(),
                    horarioDesejado,
                    horarioFinal
            );

            if (elevadoresDisponiveis.isEmpty()) {
                throw new RuntimeException("Nenhum elevador do tipo " + servico.getTipoElevador() + " disponível.");
            }

            elevadorId = elevadoresDisponiveis.get(0).getId();
        }

        Agendamento agendamento = new Agendamento(
                horarioDesejado,
                descricaoProblema,
                veiculo.getId(),
                elevadorId,
                funcionarioAlocado.getId(),
                servico.getId(),
                ordemDeServico.getId()
        );

        Agendamento.instances.add(agendamento);

        ordemDeServico.addAgendamento(agendamento.getId());

        return agendamento;
    }
}
