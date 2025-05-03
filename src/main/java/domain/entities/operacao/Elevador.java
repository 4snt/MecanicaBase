package domain.entities.operacao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import core.Entity;
import domain.entities.financeiro.Agendamento;
import domain.entities.financeiro.StatusAgendamento;

/**
 * Representa um Elevador da oficina, que pode ser do tipo geral ou exclusivo
 * para alinhamento e balanceamento. Responsável por controlar o tipo e
 * verificar disponibilidade de uso.
 */
public class Elevador extends Entity {

    /**
     * Lista estática contendo as instâncias dos elevadores disponíveis na
     * oficina.
     */
    public static final List<Elevador> instances = new ArrayList<>(Collections.nCopies(3, null));

    /**
     * Tipo do elevador (geral ou alinhamento e balanceamento).
     */
    private TipoElevador tipo;

    /**
     * Construtor privado para criação de um elevador com o tipo especificado.
     *
     * @param tipo Tipo do elevador.
     */
    private Elevador(TipoElevador tipo) {
        this.tipo = tipo;
    }

    /**
     * Retorna o tipo do elevador.
     *
     * @return Tipo do elevador.
     */
    public TipoElevador getTipo() {
        return tipo;
    }

    /**
     * Define o tipo do elevador e atualiza a data de modificação da entidade.
     *
     * @param tipo Novo tipo do elevador.
     */
    public void setTipo(TipoElevador tipo) {
        this.tipo = tipo;
        this.touch();
    }

    /**
     * Busca os elevadores disponíveis de um determinado tipo dentro de um
     * intervalo de tempo. Um elevador está disponível se não houver
     * agendamentos conflitantes com o período informado.
     *
     * @param tipo Tipo de elevador desejado.
     * @param dataInicio Início do intervalo de tempo.
     * @param dataFinal Fim do intervalo de tempo.
     * @return Lista de elevadores disponíveis.
     */
    public static List<Elevador> buscarElevadoresDisponiveis(
            TipoElevador tipo,
            LocalDateTime dataInicio,
            LocalDateTime dataFinal
    ) {
        return Elevador.instances.stream()
                .filter(e -> e.getTipo() == tipo)
                .filter(e -> {
                    // Verifica se o elevador está livre no intervalo desejado
                    boolean conflita = Agendamento.instances.stream()
                            .filter(a -> a.getStatus() == StatusAgendamento.AGENDADO)
                            .filter(a -> {
                                Servico s = a.getServico();
                                if (!s.usaElevador() || s.getTipoElevador() != tipo) {
                                    return false;
                                }
                                if (!e.equals(a.getElevador())) {
                                    return false;
                                }

                                LocalDateTime aInicio = a.getData();
                                LocalDateTime aFim = aInicio.plusMinutes(s.getDuracao());

                                return !dataFinal.isBefore(aInicio) && !dataInicio.isAfter(aFim);
                            })
                            .findAny()
                            .isPresent();

                    return !conflita;
                })
                .collect(Collectors.toList());
    }

    /**
     * Inicializa a lista de elevadores disponíveis na oficina com tipos
     * pré-definidos. Deve ser chamado uma única vez na configuração do sistema.
     */
    public static final void init() {
        Elevador.instances.add(new Elevador(TipoElevador.ALINHAMENTO_E_BALANCEAMENTO));
        Elevador.instances.add(new Elevador(TipoElevador.GERAL));
        Elevador.instances.add(new Elevador(TipoElevador.GERAL));
    }
}
