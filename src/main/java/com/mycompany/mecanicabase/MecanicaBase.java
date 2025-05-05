package com.mycompany.mecanicabase;

import infra.db.Database;
import presentation.Terminal.router.TerminalRouter;

public class MecanicaBase {

    public static void main(String[] args) {
        Database.load();

        new TerminalRouter().start();

        Database.save(); // salva no final do terminal
    }
}
