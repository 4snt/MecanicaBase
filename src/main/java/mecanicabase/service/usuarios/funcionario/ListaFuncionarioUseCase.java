package mecanicabase.service.usuarios.funcionario;

import java.util.List;
import java.util.stream.Collectors;
import mecanicabase.model.usuarios.Funcionario;

/**
 * Caso de uso responsável por listar os funcionários com base em um filtro.
 */
public class ListaFuncionarioUseCase {

    /**
     * Retorna uma lista de funcionários que correspondem ao filtro fornecido.
     * Se o filtro for nulo ou vazio, retorna todos os funcionários.
     *
     * @param filtro Filtro utilizado para buscar funcionários (pode ser nome ou
     * email). Se o filtro for nulo ou vazio, todos os funcionários são
     * retornados.
     * @return Uma lista de funcionários que atendem ao filtro fornecido.
     */
    public List<Funcionario> use(String filtro) {
        // Se o filtro for nulo ou vazio, retorna todos os funcionários
        if (filtro == null || filtro.isBlank()) {
            return Funcionario.instances;
        }

        // Converte o filtro para minúsculas para busca case-insensitive
        String filtroLower = filtro.toLowerCase();

        // Filtra a lista de funcionários pelo nome ou email
        return Funcionario.instances.stream()
                .filter(funcionario
                        -> funcionario.getNome().toLowerCase().contains(filtroLower)
                || funcionario.getEmail().toLowerCase().contains(filtroLower))
                .collect(Collectors.toList());
    }
}
