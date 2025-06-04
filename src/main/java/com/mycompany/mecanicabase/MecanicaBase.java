package com.mycompany.mecanicabase;

import com.mycompany.mecanicabase.infra.db.Database;
import com.mycompany.mecanicabase.view.Terminal.router.TerminalRouter;

public class MecanicaBase {

    public static void main(String[] args) {
        Database.load();

        new TerminalRouter().start();

        Database.save(); // salva no final do terminal
    }
}
