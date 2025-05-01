package domain.entities.operacao;

import java.util.ArrayList;
import java.util.List;

import core.Entity;

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
    private String status;         // Status do veículo (ex: "Disponível", "Em manutenção")

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
    public Veiculo(String modelo, String placa, int anoFabricacao, String cor, String status) {
        this.modelo = modelo;
        this.placa = placa;
        this.anoFabricacao = anoFabricacao;
        this.cor = cor;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
