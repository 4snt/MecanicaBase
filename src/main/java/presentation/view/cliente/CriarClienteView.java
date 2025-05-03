package presentation.view.cliente;

import controllers.ClienteController;
import domain.entities.usuarios.Cliente;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CriarClienteView {

    private final ClienteController controller = new ClienteController();

    public Node getView() {
        VBox form = new VBox(12);
        form.setPadding(new Insets(25));
        form.setFillWidth(true);
        form.setStyle("""
            -fx-background-color: white;
            -fx-background-radius: 15;
            -fx-border-radius: 15;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10, 0, 2, 2);
        """);

        Label title = new Label("Criar Cliente");
        title.setFont(Font.font(null, FontWeight.BOLD, 16));

        MFXTextField nomeField = input("Nome");
        MFXTextField enderecoField = input("Endereço");
        MFXTextField telefoneField = input("Telefone");
        MFXTextField emailField = input("Email");
        MFXTextField cpfField = input("CPF");

        Label feedback = new Label();
        feedback.setTextFill(Color.DARKGREEN);
        feedback.setWrapText(true);

        MFXButton criarBtn = new MFXButton("Criar Cliente");
        criarBtn.setPrefWidth(Double.MAX_VALUE);
        criarBtn.setStyle("-fx-background-color: #47D4DD; -fx-text-fill: white;");

        criarBtn.setOnAction(e -> {
            Cliente cliente = controller.criar(
                    nomeField.getText(),
                    enderecoField.getText(),
                    telefoneField.getText(),
                    emailField.getText(),
                    cpfField.getText()
            );
            feedback.setText("✅ Cliente criado: " + cliente.getId());
        });

        form.getChildren().addAll(title, nomeField, enderecoField, telefoneField, emailField, cpfField, criarBtn, feedback);
        return form;
    }

    private MFXTextField input(String floatingText) {
        MFXTextField field = new MFXTextField();
        field.setFloatingText(floatingText);
        field.setMaxWidth(Double.MAX_VALUE);
        return field;
    }
}
