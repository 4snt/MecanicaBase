package mecanicabase.infra.db;

import java.util.List;
import mecanicabase.core.Entity;
import mecanicabase.model.financeiro.Agendamento;
import mecanicabase.model.financeiro.CategoriaDespesa;
import mecanicabase.model.financeiro.Despesa;
import mecanicabase.model.financeiro.PecaItem;
import mecanicabase.model.financeiro.ServicoItem;
import mecanicabase.model.operacao.Elevador;
import mecanicabase.model.operacao.Peca;
import mecanicabase.model.operacao.Servico;
import mecanicabase.model.operacao.Veiculo;
import mecanicabase.model.usuarios.Administrador;
import mecanicabase.model.usuarios.Cliente;
import mecanicabase.model.usuarios.Funcionario;

public class EntityRegistry {

    public static List<Class<? extends Entity>> getEntities() {
        return List.of(
                Agendamento.class, CategoriaDespesa.class, Despesa.class,
                PecaItem.class, ServicoItem.class,
                Elevador.class, Peca.class, Servico.class, Veiculo.class,
                Administrador.class, Funcionario.class, Cliente.class
        );
    }
}
