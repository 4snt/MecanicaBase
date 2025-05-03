package domain.usecases.usuarios.administrador;

import java.util.List;
import java.util.stream.Collectors;

import domain.entities.usuarios.Administrador;

/**
 * Caso de uso responsável por listar administradores, com a possibilidade de
 * filtrar pela nome ou email.
 */
public class ListarAdministradorUseCase {

    /**
     * Retorna uma lista de administradores filtrada pelo nome ou email, caso um
     * filtro seja fornecido.
     *
     * @param filtro Texto utilizado para filtrar os administradores pelo nome
     * ou email. Se for nulo ou vazio, todos os administradores serão
     * retornados.
     * @return Lista de administradores que atendem ao critério de filtro.
     */
    public List<Administrador> use(String filtro) {
        // Se o filtro for nulo ou vazio, retorna todos os administradores
        if (filtro == null || filtro.isBlank()) {
            return Administrador.instances;
        }

        // Converte o filtro para minúsculas para garantir que a busca não seja sensível a maiúsculas/minúsculas
        String filtroLower = filtro.toLowerCase();

        // Filtra os administradores que contêm o filtro no nome ou email
        return Administrador.instances.stream()
                .filter(administrador
                        -> administrador.getNome().toLowerCase().contains(filtroLower)
                || // Verifica se o nome contém o filtro
                administrador.getEmail().toLowerCase().contains(filtroLower)) // Verifica se o email contém o filtro
                .collect(Collectors.toList());  // Coleta os resultados filtrados em uma lista
    }
}
