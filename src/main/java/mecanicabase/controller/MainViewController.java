package mecanicabase.controller;

import java.util.Scanner;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import mecanicabase.infra.auth.Session;
import mecanicabase.model.usuarios.Administrador;
import mecanicabase.view.Terminal.AgendamentoTerminalHandler;
import mecanicabase.view.Terminal.CategoriaDespesaTerminalHandler;
import mecanicabase.view.Terminal.ClienteTerminalHandler;
import mecanicabase.view.Terminal.ColaboradorTerminalHandler;
import mecanicabase.view.Terminal.DespesaTerminalHandler;
import mecanicabase.view.Terminal.GerarBalancoTerminalHandler;
import mecanicabase.view.Terminal.GerarRelatorioTerminalHandler;
import mecanicabase.view.Terminal.OficinaTerminalHandler;
import mecanicabase.view.Terminal.OrdemDeServicoTerminalHandler;
import mecanicabase.view.Terminal.ServicoTerminalHandler;

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
        new OficinaTerminalHandler(scanner, true).menu();
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
        new OrdemDeServicoTerminalHandler(scanner, true).menu();
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
