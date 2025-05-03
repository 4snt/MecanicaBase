package com.mycompany.mecanicabase;

import infra.db.Database;
import javafx.application.Application;
import presentation.view.MainView;

/**
 * Classe principal da aplicação MecanicaBase.
 *
 * Responsável por iniciar o carregamento do banco de dados, lançar a interface
 * gráfica com JavaFX e salvar os dados ao final.
 */
public class MecanicaBase {

    /**
     * Método principal que inicia a aplicação.
     *
     * @param args Argumentos da linha de comando.
     */
    public static void main(String[] args) {
        Database.load(); // Carrega as instâncias salvas em JSON
        Application.launch(MainView.class, args); // Inicia a interface gráfica
        Database.save(); // Salva as alterações no arquivo JSON ao finalizar
    }
}
