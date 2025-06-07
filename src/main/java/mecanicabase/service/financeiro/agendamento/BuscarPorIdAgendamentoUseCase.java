package mecanicabase.service.financeiro.agendamento;

import java.util.UUID;
import mecanicabase.model.financeiro.Agendamento;

/**
 * Caso de uso responsável por buscar um agendamento pelo seu identificador
 * único (UUID).
 */
public class BuscarPorIdAgendamentoUseCase {

    /**
     * Executa a busca de um agendamento utilizando o ID fornecido.
     *
     * @param id UUID do agendamento que se deseja encontrar.
     * @return O objeto {@link Agendamento} correspondente ao ID, ou
     * {@code null} se não encontrado.
     */
    public Agendamento use(UUID id) {
        return Agendamento.buscarPorId(id);
    }
}
