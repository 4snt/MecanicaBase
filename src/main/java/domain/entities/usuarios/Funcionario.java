// Funcionario.java
package domain.entities.usuarios;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import domain.entities.financeiro.Agendamento;
import domain.entities.financeiro.StatusAgendamento;
public class Funcionario extends Pessoa {
    public static List<Funcionario> instances = new ArrayList<>();

    protected TipoFuncionario funcao;
    protected String login;
    protected String senha;

    public Funcionario(String nome, TipoFuncionario funcao, String login, String senha) {
        super(nome);
        this.funcao = funcao;
        this.login = login;
        this.senha = senha;
    }
    public TipoFuncionario getTipo() {
        return this.funcao;
    }

    public static List<Funcionario> buscarFuncionariosDisponiveis(
    TipoFuncionario tipo,
    LocalDateTime dataInicio,
    LocalDateTime dataFinal
) {
    return Funcionario.instances.stream()
        .filter(func -> func.getTipo() == tipo)
        .filter(func -> {
            boolean conflita = Agendamento.instances.stream()
                .filter(a -> a.getStatus() == StatusAgendamento.AGENDADO)
                .filter(a -> {
                    if (!func.equals(a.getFuncionario())) return false;

                    LocalDateTime aInicio = a.getData();
                    LocalDateTime aFim = aInicio.plusMinutes(a.getServico().getDuracao());

                    return !dataFinal.isBefore(aInicio) && !dataInicio.isAfter(aFim);
                })
                .findAny()
                .isPresent();

            return !conflita;
        })
        .collect(Collectors.toList());
}
}
