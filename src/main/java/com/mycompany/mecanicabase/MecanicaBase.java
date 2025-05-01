/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mecanicabase;

import infra.db.Database;
import presentation.router.TerminalRouter;;

/**
 * @author Luis Claudio Prado
 * @author Murilo Santiago
 * @author Pávila Cardoso
 */
public class MecanicaBase {

    public static void main(String[] args) {
        Database.load();
        new TerminalRouter().start();
        Database.save();
    }
}
