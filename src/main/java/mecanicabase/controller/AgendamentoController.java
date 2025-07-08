package mecanicabase.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import mecanicabase.model.financeiro.Agendamento;
import mecanicabase.model.financeiro.OrdemDeServico;
import mecanicabase.model.financeiro.ServicoItem;
import mecanicabase.model.financeiro.StatusOrdemDeServico;
import mecanicabase.model.operacao.Elevador;
import mecanicabase.model.operacao.Servico;
import mecanicabase.model.operacao.Veiculo;
import mecanicabase.model.usuarios.Funcionario;
import mecanicabase.service.financeiro.AgendamentoCrud;

/**
 * Controller responsável por operações relacionadas a agendamentos.
 * <p>
 * Documentação detalhada será adicionada futuramente.
 * </p>
 */
public class AgendamentoController {

    private final AgendamentoCrud crud;

    public AgendamentoController(AgendamentoCrud crud) {
        this.crud = crud;
    }

    /**
     * Cria um novo agendamento, alocando automaticamente os recursos
     * necessários.
     *
     * @param horarioDesejado Data e hora desejadas
     * @param servico Serviço a ser executado
     * @param descricaoProblema Descrição do problema relatado
     * @param veiculo Veículo do cliente
     * @param ordemDeServico Ordem de Serviço aberta
     * @return Agendamento criado
     */
    public Agendamento criarAgendamentoComAlocacao(
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

        // Criação delegada ao Crud
        Agendamento agendamento = crud.criar(
                true,
                horarioDesejado,
                descricaoProblema,
                veiculo.getId(),
                elevadorId,
                funcionarioAlocado.getId(),
                servico.getId(),
                ordemDeServico.getId()
        );
        ServicoItem servicoItem = new ServicoItem(
                servico.getId(),
                servico.getPreco(),
                ordemDeServico.getId()
        );
        ServicoItem.instances.add(servicoItem);
        ordemDeServico.addServico(servicoItem.getId());

        ordemDeServico.addAgendamento(agendamento.getId());
        return agendamento;
    }
}
