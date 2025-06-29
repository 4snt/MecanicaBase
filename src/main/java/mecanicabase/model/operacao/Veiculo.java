package mecanicabase.model.operacao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mecanicabase.core.Entity;
import mecanicabase.model.usuarios.Cliente;

/**
 * Representa um veículo cadastrado no sistema. Essa classe é parte do modelo de
 * domínio e armazena os dados essenciais do veículo.
 */
public class Veiculo extends Entity {

    /**
     * Lista que armazena todas as instâncias de veículos. Essa lista simula um
     * banco de dados em memória e será usada para serialização/deserialização
     * em JSON.
     */
    public static List<Veiculo> instances = new ArrayList<>();

    // =========================
    // Atributos do Veículo
    // =========================
    private String modelo;
    private String placa;
    private int anoFabricacao;
    private String cor;
    private StatusVeiculo status;
    private UUID clienteId;

    // =========================
    // Construtor
    // =========================
    /**
     * Construtor para criar um novo veículo vinculado a um cliente.
     *
     * @param clienteId ID do cliente proprietário do veículo
     * @param modelo Modelo do veículo
     * @param placa Placa do veículo
     * @param anoFabricacao Ano de fabricação
     * @param cor Cor do veículo
     */
    public Veiculo(UUID clienteId, String modelo, String placa, int anoFabricacao, String cor) {
        this.modelo = modelo;
        this.placa = placa;
        this.anoFabricacao = anoFabricacao;
        this.cor = cor;
        this.clienteId = clienteId;
        this.status = StatusVeiculo.RECEBIDO;
    }

    // =========================
    // Getters e Setters
    // =========================
    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getAnoFabricacao() {
        return anoFabricacao;
    }

    public void setAnoFabricacao(int anoFabricacao) {
        this.anoFabricacao = anoFabricacao;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public StatusVeiculo getStatus() {
        return status;
    }

    public void setStatus(StatusVeiculo status) {
        this.status = status;
    }

    public void setCliente(UUID clienteId) {
        this.clienteId = clienteId;
    }

    public Cliente getCliente() {
        return Cliente.instances.stream()
                .filter(c -> c.getId().equals(this.clienteId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retorna uma representação textual do veículo.
     *
     * @return String formatada com os dados do veículo
     */
    @Override
    public String toString() {
        return String.format(
                "Veiculo [ID=%s, Modelo='%s', Placa='%s', Ano=%d, Cor='%s', Status=%s, Cliente='%s']",
                getId(),
                modelo,
                placa,
                anoFabricacao,
                cor,
                status != null ? status.name() : "N/A",
                getCliente() != null ? getCliente().getNome() : "Desconhecido"
        );
    }
}
