package domain.entities.operacao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import core.Entity;
import domain.entities.usuarios.TipoFuncionario;

/**
 * Representa um serviço oferecido pela oficina, como troca de óleo, revisão,
 * entre outros. Cada serviço pode exigir um tipo de funcionário, utilizar
 * elevador e ter uma duração estimada.
 */
public class Servico extends Entity {

    /**
     * Lista que simula um banco de dados em memória para persistência via JSON.
     */
    public static List<Servico> instances = new ArrayList<>();

    private String tipo;
    private float preco;
    private String descricao;
    private int duracao;
    private TipoFuncionario tipoFuncionario;
    private TipoElevador tipoElevador;
    private boolean usaElevador;

    /**
     * Construtor da classe Servico.
     *
     * @param tipo Tipo ou nome do serviço
     * @param preco Valor cobrado pelo serviço
     * @param descricao Descrição detalhada do serviço
     */
    public Servico(String tipo, float preco, String descricao) {
        this.tipo = tipo;
        this.preco = preco;
        this.descricao = descricao;
    }

    /**
     * Retorna o tipo do serviço.
     *
     * @return Tipo do serviço
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Define o tipo do serviço.
     *
     * @param tipo Novo tipo do serviço
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Retorna o preço do serviço.
     *
     * @return Preço do serviço
     */
    public float getPreco() {
        return preco;
    }

    /**
     * Define o preço do serviço.
     *
     * @param preco Novo preço
     */
    public void setPreco(float preco) {
        this.preco = preco;
    }

    /**
     * Retorna a descrição do serviço.
     *
     * @return Descrição do serviço
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição do serviço.
     *
     * @param descricao Nova descrição
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna a duração estimada do serviço em minutos.
     *
     * @return Duração do serviço
     */
    public int getDuracao() {
        return duracao;
    }

    /**
     * Define a duração do serviço em minutos.
     *
     * @param duracao Nova duração
     */
    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    /**
     * Retorna o tipo de funcionário necessário para realizar o serviço.
     *
     * @return Tipo de funcionário
     */
    public TipoFuncionario getTipoFuncionario() {
        return tipoFuncionario;
    }

    /**
     * Define o tipo de funcionário necessário para o serviço.
     *
     * @param tipoFuncionario Tipo de funcionário
     */
    public void setTipoFuncionario(TipoFuncionario tipoFuncionario) {
        this.tipoFuncionario = tipoFuncionario;
    }

    /**
     * Retorna o tipo de elevador necessário, caso o serviço utilize um.
     *
     * @return Tipo de elevador
     */
    public TipoElevador getTipoElevador() {
        return tipoElevador;
    }

    /**
     * Define o tipo de elevador necessário para o serviço.
     *
     * @param tipoElevador Tipo de elevador
     */
    public void setTipoElevador(TipoElevador tipoElevador) {
        this.tipoElevador = tipoElevador;
    }

    /**
     * Indica se o serviço utiliza elevador.
     *
     * @return {@code true} se usa elevador, {@code false} caso contrário
     */
    public boolean usaElevador() {
        return usaElevador;
    }

    /**
     * Define se o serviço utiliza elevador.
     *
     * @param usaElevador Valor booleano indicando o uso de elevador
     */
    public void setUsaElevador(boolean usaElevador) {
        this.usaElevador = usaElevador;
    }

    /**
     * Busca um serviço pelo seu UUID.
     *
     * @param id Identificador único do serviço
     * @return Instância de {@code Servico} correspondente ou {@code null} se
     * não encontrada
     */
    public static Servico buscarPorId(UUID id) {
        return Servico.instances.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
