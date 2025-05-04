package controllers;

import java.util.Scanner;

import domain.entities.usuarios.Administrador;
import infra.auth.Session;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import presentation.Terminal.AgendamentoTerminalHandler;
import presentation.Terminal.CategoriaDespesaTerminalHandler;
import presentation.Terminal.ClienteTerminalHandler;
import presentation.Terminal.ColaboradorTerminalHandler;
import presentation.Terminal.DespesaTerminalHandler;
import presentation.Terminal.GerarBalancoTerminalHandler;
import presentation.Terminal.GerarRelatorioTerminalHandler;
import presentation.Terminal.OficinaTerminalHandler;
import presentation.Terminal.OrdemDeServicoTerminalHandler;
import presentation.Terminal.ServicoTerminalHandler;

public class MainViewController {

    private final Scanner scanner = new Scanner(System.in);

    @FXML
    private MFXButton btnClientes;

    @FXML
    private MFXButton btnOficina;

    @FXML
    private MFXButton btnServicos;

    @FXML
    private MFXButton btnAgendamentos;

    @FXML
    private MFXButton btnOrdemServico;

    @FXML
    private MFXButton btnColaboradores;

    @FXML
    private MFXButton btnFinanceiro;

    @FXML
    private VBox adminSection;

    @FXML
    public void initialize() {
        // Exibe a área admin somente se for um administrador
        boolean isAdmin = Session.getPessoaLogado() instanceof Administrador;
        adminSection.setVisible(isAdmin);
    }

    @FXML
    private void abrirClientes() {
        new ClienteTerminalHandler(scanner).menu();
    }

    @FXML
    private void abrirOficina() {
        new OficinaTerminalHandler(scanner).menu();
    }

    @FXML
    private void abrirServicos() {
        new ServicoTerminalHandler(scanner).menu();
    }

    @FXML
    private void abrirAgendamentos() {
        new AgendamentoTerminalHandler(scanner).menu();
    }

    @FXML
    private void abrirOrdens() {
        new OrdemDeServicoTerminalHandler(scanner).menu();
    }

    @FXML
    private void abrirColaboradores() {
        new ColaboradorTerminalHandler(scanner).menu();
    }

    @FXML
    private void abrirFinanceiro() {
        new CategoriaDespesaTerminalHandler(scanner).menu();
        new DespesaTerminalHandler(scanner).menu();
        new GerarRelatorioTerminalHandler(scanner).menu();
        new GerarBalancoTerminalHandler(scanner).menu();
    }
}
