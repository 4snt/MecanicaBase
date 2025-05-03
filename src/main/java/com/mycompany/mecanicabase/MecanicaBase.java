/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.mecanicabase;

import infra.db.Database;
import javafx.application.Application;
import presentation.view.MainView;

;

/**
 * @author Luis Claudio Prado
 * @author Murilo Santiago
 * @author Pávila Cardoso
 */
public class MecanicaBase {

    public static void main(String[] args) {
        Database.load();
        Application.launch(MainView.class, args);
        Database.save();
    }
}
