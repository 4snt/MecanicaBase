package domain.entities.operacao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import core.Entity;
import domain.entities.usuarios.Cliente;

/**
 * Representa um veículo cadastrado no sistema.
 * Essa classe é parte do modelo de domínio e armazena os dados essenciais do veículo.
 */
public class Veiculo extends Entity {

    /**
     * Lista que armazena todas as instâncias de veículos.
     * Essa lista simula um banco de dados em memória e será usada para serialização/deserialização em JSON.
     * É uma exigência do projeto como forma de persistência de dados sem uso de banco relacional.
     */
    public static List<Veiculo> instances = new ArrayList<>();

    // =========================
    // Atributos do Veículo
    // =========================

    private String modelo;         // Modelo do veículo (ex: "Fiat Uno", "Toyota Corolla")
    private String placa;          // Placa do veículo (ex: "ABC-1234")
    private int anoFabricacao;     // Ano de fabricação (ex: 2015)
    private String cor;            // Cor do veículo (ex: "Branco", "Preto")
    private StatusVeiculo status;         // Status do veículo (ex: "Disponível", "Em manutenção")
    private UUID clienteId;

    // =========================
    // Construtor
    // =========================

    /**
     * Construtor para criar um novo objeto Veiculo.
     *
     * @param modelo Modelo do veículo
     * @param placa Placa do veículo
     * @param anoFabricacao Ano de fabricação
     * @param cor Cor do veículo
     * @param status Status atual do veículo
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
        for (Cliente c : Cliente.instances) {
            if (c.getId().equals(this.clienteId)) {
                return c;
            }
        }
        return null;
    }
    @Override
        public String toString() {
            return """
                Veículo {
                ID: %s
                Modelo: %s
                Placa: %s
                Ano de Fabricação: %d
                Cor: %s
                Status: %s
                Cliente: %s
                }
                """.formatted(
                    getId(),
                    modelo,
                    placa,
                    anoFabricacao,
                    cor,
                    status,
                    getCliente() != null ? getCliente().getNome() : "Desconhecido"
                );
        }

}
