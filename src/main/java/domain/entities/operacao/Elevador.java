package domain.entities.operacao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import core.Entity;
import domain.entities.financeiro.Agendamento;
import domain.entities.financeiro.StatusAgendamento;

public class Elevador extends Entity {
    public static final List<Elevador> instances = new ArrayList<>(Collections.nCopies(3, null));

    private TipoElevador tipo;

    private Elevador(TipoElevador tipo) {
        this.tipo = tipo;
    }

    public TipoElevador getTipo() {
        return tipo;
    }

    public void setTipo(TipoElevador tipo) {
        this.tipo = tipo;
        this.touch();
    }

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
                        if (!s.usaElevador() || s.getTipoElevador() != tipo) return false;
                        if (!e.equals(a.getElevador())) return false;

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

    public static final void init() {
        Elevador.instances.add(new Elevador(TipoElevador.ALINHAMENTO_E_BALANCEAMENTO));
        Elevador.instances.add(new Elevador(TipoElevador.GERAL));
        Elevador.instances.add(new Elevador(TipoElevador.GERAL));
    }
}
