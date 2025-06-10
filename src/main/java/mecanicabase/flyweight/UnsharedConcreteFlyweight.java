package mecanicabase.flyweight;

import mecanicabase.model.operacao.Peca;

public class UnsharedConcreteFlyweight extends Peca {

    private Object estadoExclusivo;

    public UnsharedConcreteFlyweight(String nome, float valor, int quantidade, Object estadoExclusivo) {
        super(nome, valor, quantidade);
        this.estadoExclusivo = estadoExclusivo;
    }

    public Object getEstadoExclusivo() {
        return estadoExclusivo;
    }

    public void setEstadoExclusivo(Object estadoExclusivo) {
        this.estadoExclusivo = estadoExclusivo;
    }

    @Override
    public void operar(Object estadoExterno) {
        System.out.println("Peça exclusiva com estado: " + estadoExterno + " e interno: " + estadoExclusivo);
    }
}
