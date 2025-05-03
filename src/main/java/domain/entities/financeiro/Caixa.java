package domain.entities.financeiro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import core.Entity;

/**
 * Representa o caixa da empresa, responsável por controlar o saldo financeiro
 * disponível.
 */
public class Caixa extends Entity {

    /**
     * Lista estática contendo as instâncias de caixas do sistema. Inicialmente
     * preenchida com uma posição nula.
     */
    public static List<Caixa> instances = new ArrayList<>(Collections.nCopies(1, null));

    /**
     * Saldo atual do caixa.
     */
    private float saldo;

    /**
     * Construtor privado para criação de um caixa com saldo inicial.
     *
     * @param saldo Saldo inicial do caixa.
     */
    private Caixa(float saldo) {
        this.saldo = saldo;
    }

    /**
     * Incrementa o saldo do caixa com o valor informado.
     *
     * @param valor Valor a ser adicionado ao saldo.
     */
    public void incrementarSaldo(float valor) {
        this.saldo += valor;
    }

    /**
     * Reduz o saldo do caixa com o valor informado.
     *
     * @param valor Valor a ser subtraído do saldo.
     */
    public void reduzirSaldo(float valor) {
        this.saldo -= valor;
    }

    /**
     * Retorna o saldo atual do caixa.
     *
     * @return Saldo do caixa.
     */
    public float getSaldo() {
        return this.saldo;
    }

    /**
     * Inicializa uma instância de caixa com o saldo especificado e a adiciona à
     * lista estática de caixas.
     *
     * @param saldo Saldo inicial para o novo caixa.
     */
    public void init(float saldo) {
        Caixa.instances.add(new Caixa(saldo));
    }
}
