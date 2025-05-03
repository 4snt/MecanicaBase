package domain.entities.usuarios;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * Representa um cliente da oficina. Cada cliente pode estar associado a vários
 * veículos, referenciados por seus UUIDs. Herda atributos e comportamentos de
 * {@link Pessoa}.
 */
public class Cliente extends Pessoa implements Comparable<Cliente> {

    /**
     * Lista estática que simula uma base de dados em memória contendo todos os
     * clientes cadastrados.
     */
    public static List<Cliente> instances = new ArrayList<>();

    /**
     * Lista de identificadores (UUIDs) dos veículos associados ao cliente.
     */
    private final List<UUID> veiculos;

    /**
     * Construtor da classe Cliente.
     *
     * @param nome Nome do cliente.
     * @param telefone Telefone do cliente.
     * @param endereco Endereço do cliente.
     * @param email Email do cliente.
     * @param cpf CPF do cliente.
     */
    public Cliente(String nome, String telefone, String endereco, String email, String cpf) {
        super(nome, email, cpf, telefone, endereco);
        this.veiculos = new ArrayList<>();
    }

    /**
     * Adiciona o ID de um veículo à lista de veículos do cliente.
     *
     * @param veiculoId UUID do veículo a ser adicionado.
     */
    public void addVeiculo(UUID veiculoId) {
        this.veiculos.add(veiculoId);
    }

    /**
     * Remove o ID de um veículo da lista de veículos do cliente.
     *
     * @param veiculoId UUID do veículo a ser removido.
     */
    public void removeVeiculo(UUID veiculoId) {
        this.veiculos.remove(veiculoId);
    }

    /**
     * Retorna a lista de UUIDs dos veículos associados ao cliente.
     *
     * @return Lista de UUIDs dos veículos.
     */
    public List<UUID> getVeiculos() {
        return veiculos;
    }

    /**
     * @param email Email a ser buscado.
     * @return Instância de {@link Cliente} ou {@code null} se não encontrado.
     */
    public static Cliente buscarPorEmail(String email) {
        return Cliente.instances.stream()
                .filter(s -> s.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    // IMPLEMENTAÇÃO DO COMPARATOR
    @Override
    public int compareTo(Cliente outro) {
        return this.getNome().compareToIgnoreCase(outro.getNome()); // Ordenação padrão por nome
    }

    public static final Comparator<Cliente> porNome
            = Comparator.comparing(Cliente::getNome, String.CASE_INSENSITIVE_ORDER);

    public static final Comparator<Cliente> porCpf
            = Comparator.comparing(Cliente::getCpf);

    public static final Comparator<Cliente> porEmail
            = Comparator.comparing(Cliente::getEmail, String.CASE_INSENSITIVE_ORDER);
}
