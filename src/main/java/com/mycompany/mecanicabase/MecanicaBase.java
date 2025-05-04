package com.mycompany.mecanicabase;

import infra.db.Database;
import javafx.application.Application;
import presentation.Terminal.router.TerminalRouter;
import presentation.view.MainView;

public class MecanicaBase {

    public static void main(String[] args) {
        Database.load();

        boolean modoTerminal = false; // ✅ Troque para true se quiser rodar no terminal

        if (modoTerminal) {
            new TerminalRouter().start();
        } else {
            Application.launch(MainView.class, args);
        }
        Database.save(); // salva no final do terminal
    }
}
