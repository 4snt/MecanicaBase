package domain.entities.operacao;

import java.util.*;
import core.*;

public class Elevador extends Entity {
    public static final List<Elevador> instances = new ArrayList<>(Collections.nCopies(3, null));

    private TipoElevador tipo;
    private boolean emUso;

    private Elevador(TipoElevador tipo) {
        this.tipo = tipo;
        this.emUso = false;
    }

    public TipoElevador getTipo() {
        return tipo;
    }

    public void setTipo(TipoElevador tipo) {
        this.tipo = tipo;
        this.touch();
    }

    public boolean getEmUso() {
        return emUso;
    }

    public void setEmUso(boolean emUso) {
        this.emUso = emUso;
        this.touch();
    }

    public static final void init() {
        Elevador.instances.add(new Elevador(TipoElevador.ALINHAMENTO_E_BALANCEAMENTO));
        Elevador.instances.add(new Elevador(TipoElevador.GERAL));
        Elevador.instances.add(new Elevador(TipoElevador.GERAL));
    }
}
