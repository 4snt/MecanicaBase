// Funcionario.java
package domain.entities.usuarios;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import domain.entities.financeiro.Agendamento;
import domain.entities.financeiro.StatusAgendamento;

/**
 * Representa um funcionário da oficina, com função e salário atribuídos. Um
 * funcionário é um colaborador que pode ser associado a agendamentos.
 */
public class Funcionario extends Colaborador {

    /**
     * Lista estática que simula um banco de dados em memória para funcionários.
     */
    public static List<Funcionario> instances = new ArrayList<>();

    private TipoFuncionario funcao;
    private float salario;

    /**
     * Construtor do funcionário.
     *
     * @param nome Nome do funcionário
     * @param funcao Tipo/função do funcionário (ex: MECANICO, ATENDENTE)
     * @param email Email do funcionário
     * @param senha Senha do funcionário (criptografada)
     * @param cpf CPF do funcionário
     * @param telefone Telefone do funcionário
     * @param endereco Endereço do funcionário
     * @param salario Salário do funcionário
     */
    public Funcionario(String nome, TipoFuncionario funcao, String email, String senha, String cpf, String telefone, String endereco, float salario) {
        super(nome, email, senha, cpf, telefone, endereco);
        this.funcao = funcao;
        this.salario = salario;
    }

    /**
     * Retorna a função do funcionário.
     *
     * @return Tipo do funcionário
     */
    public TipoFuncionario getFuncao() {
        return this.funcao;
    }

    /**
     * Define a função do funcionário.
     *
     * @param funcao Nova função
     */
    public void setFuncao(TipoFuncionario funcao) {
        this.funcao = funcao;
    }

    /**
     * Retorna o salário atual do funcionário.
     *
     * @return Salário
     */
    public float getSalario() {
        return salario;
    }

    /**
     * Define o salário do funcionário.
     *
     * @param salario Novo salário
     */
    public void setSalario(float salario) {
        this.salario = salario;
    }

    /**
     * Busca todos os funcionários disponíveis em um intervalo de tempo e com o
     * tipo de função requisitado.
     *
     * @param tipo Tipo de funcionário necessário
     * @param dataInicio Início do período do agendamento
     * @param dataFinal Fim do período do agendamento
     * @return Lista de funcionários disponíveis
     */
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
                                if (!func.equals(a.getFuncionario())) {
                                    return false;
                                }

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

    /**
     * Busca um funcionário pelo email.
     *
     * @param email Email do funcionário
     * @return Funcionário correspondente ou null
     */
    public static Funcionario buscarPorEmail(String email) {
        return Funcionario.instances.stream()
                .filter(s -> s.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    /**
     * Busca um funcionário pelo ID.
     *
     * @param id UUID do funcionário
     * @return Funcionário correspondente ou null
     */
    public static Funcionario buscarPorId(UUID id) {
        return Funcionario.instances.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
