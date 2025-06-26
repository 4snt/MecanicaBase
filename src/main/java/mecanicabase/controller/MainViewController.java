package mecanicabase.controller;

import java.io.IOException;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mecanicabase.infra.ApplicationContext;
import mecanicabase.infra.auth.Session;
import mecanicabase.model.usuarios.Administrador;

public class MainViewController {

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

    private final ApplicationContext context;

    public MainViewController(ApplicationContext context) {
        this.context = context;
    }

    @FXML
    public void initialize() {
        boolean isAdmin = Session.getPessoaLogado() instanceof Administrador;
        adminSection.setVisible(isAdmin);
    }

    @FXML
    private void abrirClientes() {
        abrirTela("/mecanicabase/view/fxml/ClienteView.fxml", "Gestão de Clientes");
    }

    @FXML
    private void abrirOficina() {
        abrirTela("/mecanicabase/view/fxml/OficinaView.fxml", "Gestão de Oficina");
    }

    @FXML
    private void abrirServicos() {
        abrirTela("/mecanicabase/view/fxml/ServicoView.fxml", "Gestão de Serviços");
    }

    @FXML
    private void abrirAgendamentos() {
        abrirTela("/mecanicabase/view/fxml/AgendamentoView.fxml", "Gestão de Agendamentos");
    }

    @FXML
    private void abrirOrdens() {
        abrirTela("/mecanicabase/view/fxml/OrdemServicoView.fxml", "Gestão de Ordens de Serviço");
    }

    @FXML
    private void abrirColaboradores() {
        abrirTela("/mecanicabase/view/fxml/ColaboradorView.fxml", "Gestão de Colaboradores");
    }

    @FXML
    private void abrirFinanceiro() {
        abrirTela("/mecanicabase/view/fxml/FinanceiroView.fxml", "Gestão Financeira");
    }

    private void abrirTela(String fxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            loader.setControllerFactory(param -> {
                try {
                    String name = fxml.substring(fxml.lastIndexOf('/') + 1, fxml.lastIndexOf("View.fxml"));
                    return Class.forName("mecanicabase.controller." + capitalize(name) + "Controller")
                            .getConstructor(ApplicationContext.class)
                            .newInstance(context);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
