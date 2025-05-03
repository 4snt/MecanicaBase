// Funcionario.java
package domain.entities.usuarios;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import domain.entities.financeiro.Agendamento;
import domain.entities.financeiro.StatusAgendamento;

public class Funcionario extends PessoaComLogin {
    public static List<Funcionario> instances = new ArrayList<>();

    private TipoFuncionario funcao;

    public Funcionario(String nome, TipoFuncionario funcao, String email, String senha, String cpf, String telefone, String endereco) {
        super(nome, email, senha, cpf, telefone, endereco);

        this.funcao = funcao;
    }

    public TipoFuncionario getFuncao() {
        return this.funcao;
    }

    public void setFuncao(TipoFuncionario funcao) {
        this.funcao = funcao;
    }

    public static List<Funcionario> buscarFuncionariosDisponiveis(
        TipoFuncionario tipo,
        LocalDateTime dataInicio,
        LocalDateTime dataFinal
    ) {
        return Funcionario.instances.stream()
            .filter(func -> func.getFuncao() == tipo)
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
