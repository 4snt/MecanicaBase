package presentation.view;

import domain.usecases.usuarios.administrador.LoginAdministradorUseCase;
import domain.usecases.usuarios.funcionario.LoginFuncionarioUseCase;
import infra.db.Database;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainView extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private static BorderPane staticRoot;
    private Label errorLabel;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        showLogin();
    }

    private void showLogin() {
        VBox loginBox = new VBox(12);
        loginBox.setPadding(new Insets(30));
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setStyle("""
            -fx-background-color: #ffffff;
            -fx-background-radius: 16;
            -fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.1), 12, 0, 0, 6);
        """);

        MFXTextField emailField = new MFXTextField();
        emailField.setPromptText("E-mail");
        emailField.setPrefWidth(250);

        MFXPasswordField senhaField = new MFXPasswordField();
        senhaField.setPromptText("Senha");
        senhaField.setPrefWidth(250);

        MFXButton loginAdminBtn = new MFXButton("Login como Admin");
        loginAdminBtn.setPrefWidth(250);
        loginAdminBtn.setStyle(buttonStyle());

        MFXButton loginFuncBtn = new MFXButton("Login como Funcionário");
        loginFuncBtn.setPrefWidth(250);
        loginFuncBtn.setStyle(buttonStyle());

        errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        loginAdminBtn.setOnAction(e -> fazerLogin(emailField.getText(), senhaField.getText(), true));
        loginFuncBtn.setOnAction(e -> fazerLogin(emailField.getText(), senhaField.getText(), false));

        loginBox.getChildren().addAll(emailField, senhaField, loginAdminBtn, loginFuncBtn, errorLabel);
        StackPane wrapper = new StackPane(loginBox);
        wrapper.setStyle("-fx-background-color: #f4f4f4");

        Scene scene = new Scene(wrapper, 400, 320);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    private void fazerLogin(String email, String senha, boolean isAdmin) {
        boolean sucesso;
        try {
            sucesso = isAdmin
                    ? new LoginAdministradorUseCase().login(email, senha)
                    : new LoginFuncionarioUseCase().login(email, senha);
        } catch (RuntimeException e) {
            errorLabel.setText("Erro interno ao tentar logar.");
            e.printStackTrace();
            return;
        }

        if (sucesso) {
            showMainMenu();
        } else {
            errorLabel.setText("❌ Login inválido. Verifique suas credenciais.");
        }
    }

    private void showMainMenu() {
        VBox sidebar = new VBox(16);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #f7f7f7;");
        sidebar.setPrefWidth(180);

        MFXButton clienteBtn = new MFXButton("Clientes", new MFXFontIcon("mfx-users"));
        clienteBtn.setStyle(buttonStyle());
        clienteBtn.setPrefWidth(Double.MAX_VALUE);

        MFXButton oficinaBtn = new MFXButton("Oficinas", new MFXFontIcon("mfx-shop"));
        oficinaBtn.setStyle(buttonStyle());
        oficinaBtn.setPrefWidth(Double.MAX_VALUE);

        MFXButton sairBtn = new MFXButton("Sair", new MFXFontIcon("mfx-exit"));
        sairBtn.setStyle(buttonStyle());
        sairBtn.setPrefWidth(Double.MAX_VALUE);

        clienteBtn.setOnAction(e -> rootLayout.setCenter(new ClienteView().getView()));
        oficinaBtn.setOnAction(e -> rootLayout.setCenter(new Label("Oficina ainda não implementado.")));
        sairBtn.setOnAction(e -> primaryStage.close());

        sidebar.getChildren().addAll(clienteBtn, oficinaBtn, sairBtn);

        rootLayout = new BorderPane();
        staticRoot = rootLayout;
        rootLayout.setLeft(sidebar);
        rootLayout.setCenter(new Label("Selecione uma opção no menu."));

        Scene scene = new Scene(rootLayout, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Menu Principal");
        primaryStage.show();
    }

    private String buttonStyle() {
        return """
            -fx-background-color: #47D4DD;
            -fx-text-fill: white;
            -fx-font-weight: bold;
            -fx-background-radius: 12;
            -fx-padding: 10 16;
        """;
    }

    public static void setConteudoCentral(Node node) {
        if (staticRoot != null) {
            staticRoot.setCenter(node);
        }
    }

    @Override
    public void stop() {
        Database.save();
    }
}
