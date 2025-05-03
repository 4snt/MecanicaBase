package presentation.view;

import domain.entities.usuarios.Colaborador;
import domain.usecases.usuarios.administrador.LoginAdministradorUseCase;
import domain.usecases.usuarios.funcionario.LoginFuncionarioUseCase;
import infra.auth.Session;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LoginView extends Application {

    @Override
    public void start(Stage stage) {
        // Campos de entrada
        MFXTextField emailField = new MFXTextField();
        emailField.setPromptText("E-mail");

        MFXPasswordField senhaField = new MFXPasswordField();
        senhaField.setPromptText("Senha");

        // Botões
        MFXButton loginAdminBtn = new MFXButton("Login como Admin");
        MFXButton loginFuncBtn = new MFXButton("Login como Funcionário");

        // Label de erro
        Label errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);
        errorLabel.setFont(Font.font(12));

        loginAdminBtn.setOnAction(e -> fazerLogin(emailField.getText(), senhaField.getText(), true, stage, errorLabel));
        loginFuncBtn.setOnAction(e -> fazerLogin(emailField.getText(), senhaField.getText(), false, stage, errorLabel));

        // Card de login
        VBox card = new VBox(15, emailField, senhaField, loginAdminBtn, loginFuncBtn, errorLabel);
        card.setPadding(new Insets(30));
        card.setAlignment(Pos.CENTER);
        card.setMaxWidth(300);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12;");
        card.setEffect(new DropShadow(10, Color.gray(0.4)));

        // Container centralizado
        StackPane container = new StackPane(card);
        container.setStyle("-fx-background-color: linear-gradient(to right, #e0f7fa, #ffffff);");
        container.setPrefSize(400, 350);

        Scene scene = new Scene(container);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

    private void fazerLogin(String email, String senha, boolean isAdmin, Stage currentStage, Label errorLabel) {
        boolean sucesso;

        try {
            sucesso = isAdmin
                    ? new LoginAdministradorUseCase().login(email, senha)
                    : new LoginFuncionarioUseCase().login(email, senha);
        } catch (RuntimeException e) {
            errorLabel.setText("Erro: " + e.getMessage());
            return;
        }

        if (sucesso) {
            Colaborador logado = Session.getPessoaLogado();
            System.out.println("Bem-vindo, " + logado.getNome());
            currentStage.close();
            new MainView().start(new Stage());
        } else {
            errorLabel.setText("Credenciais inválidas!");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
