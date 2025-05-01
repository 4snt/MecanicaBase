package domain.usecases.operacao.agendamento;

import java.time.LocalDateTime;
import java.util.List;

import domain.entities.financeiro.Agendamento;
import domain.entities.financeiro.OrdemDeServico;
import domain.entities.financeiro.StatusOrdemDeServico;
import domain.entities.operacao.Elevador;
import domain.entities.operacao.Servico;
import domain.entities.operacao.Veiculo;
import domain.entities.usuarios.Cliente;
import domain.entities.usuarios.Funcionario;

public class CriarAgendamentoUseCase {

    public Agendamento use(
        LocalDateTime horarioDesejado,
        Servico servico,
        String descricaoProblema,
        Cliente cliente,
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

        Elevador elevadorAlocado = null;
        if (servico.usaElevador()) {
            List<Elevador> elevadoresDisponiveis = Elevador.buscarElevadoresDisponiveis(
              servico.getTipoElevador(), 
              horarioDesejado, 
              horarioFinal
            );

            if (elevadoresDisponiveis.isEmpty()) {
                throw new RuntimeException("Nenhum elevador do tipo " + servico.getTipoElevador() + " disponível.");
            }

            elevadorAlocado = elevadoresDisponiveis.get(0);
        }

        Agendamento agendamento = new Agendamento(
            horarioDesejado,
            descricaoProblema,
            cliente,
            veiculo,
            elevadorAlocado,
            funcionarioAlocado,
            servico,
            ordemDeServico
        );

        Agendamento.instances.add(agendamento);
        return agendamento;
    }
}
