package presentation.view.cliente;

import controllers.ClienteController;
import domain.entities.usuarios.Cliente;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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
        form.setMaxWidth(600);
        form.setStyle("-fx-background-color: transparent;");

        Label title = new Label("Criar Cliente");
        title.setFont(Font.font(null, FontWeight.BOLD, 16));

        MFXTextField nomeField = input("Nome");
        MFXTextField ruaField = input("Rua");
        MFXTextField numeroField = input("Número");
        MFXTextField bairroField = input("Bairro");
        MFXTextField cidadeField = input("Cidade");
        MFXTextField estadoField = input("Estado");
        MFXTextField telefoneField = input("Telefone");
        MFXTextField emailField = input("Email");
        MFXTextField cpfField = input("CPF");

        Label feedback = new Label();
        feedback.setTextFill(Color.DARKGREEN);
        feedback.setWrapText(true);

        // ===== Endereço formatado com HBox proporcional =====
        HBox row1 = new HBox(10, ruaField, numeroField);
        HBox.setHgrow(ruaField, Priority.ALWAYS);
        HBox.setHgrow(numeroField, Priority.ALWAYS);
        ruaField.setPrefWidth(350);
        numeroField.setPrefWidth(100);

        HBox row2 = new HBox(10, bairroField, cidadeField);
        HBox.setHgrow(bairroField, Priority.ALWAYS);
        HBox.setHgrow(cidadeField, Priority.ALWAYS);

        HBox row3 = new HBox(10, estadoField);
        HBox.setHgrow(estadoField, Priority.ALWAYS);

        row1.setFillHeight(true);
        row2.setFillHeight(true);
        row3.setFillHeight(true);

        // ===== Máscara CPF =====
        cpfField.textProperty().addListener((obs, oldVal, newVal) -> {
            String digits = newVal.replaceAll("\\D", "");
            if (digits.length() > 11) {
                digits = digits.substring(0, 11);
            }

            StringBuilder formatted = new StringBuilder();
            for (int i = 0; i < digits.length(); i++) {
                if (i == 3 || i == 6) {
                    formatted.append('.'); 
                }else if (i == 9) {
                    formatted.append('-');
                }
                formatted.append(digits.charAt(i));
            }

            cpfField.setText(formatted.toString());
            cpfField.positionCaret(formatted.length());
        });

        // ===== Máscara Telefone =====
        telefoneField.textProperty().addListener((obs, oldVal, newVal) -> {
            String digits = newVal.replaceAll("\\D", "");
            if (digits.length() > 11) {
                digits = digits.substring(0, 11);
            }

            StringBuilder formatted = new StringBuilder();
            for (int i = 0; i < digits.length(); i++) {
                if (i == 0) {
                    formatted.append('('); 
                }else if (i == 2) {
                    formatted.append(") "); 
                }else if (i == 7) {
                    formatted.append('-');
                }
                formatted.append(digits.charAt(i));
            }

            telefoneField.setText(formatted.toString());
            telefoneField.positionCaret(formatted.length());
        });

        // ===== Botão estilizado =====
        MFXButton criarBtn = new MFXButton("Criar Cliente");
        criarBtn.setPrefWidth(Double.MAX_VALUE);
        criarBtn.setMinHeight(40);
        VBox.setMargin(criarBtn, new Insets(10, 0, 0, 0));
        criarBtn.setStyle("""
            -fx-background-color: #47D4DD;
            -fx-text-fill: white;
            -fx-font-weight: bold;
            -fx-font-size: 14px;
            -fx-background-radius: 5;
        """);

        criarBtn.setOnAction(e -> {
            String email = emailField.getText();
            if (!email.matches("^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,}$")) {
                feedback.setTextFill(Color.RED);
                feedback.setText("❌ Email inválido.");
                return;
            }

            String enderecoCompleto = ruaField.getText() + ", " + numeroField.getText() + ", "
                    + bairroField.getText() + ", " + cidadeField.getText() + " - " + estadoField.getText();

            Cliente cliente = controller.criar(
                    nomeField.getText(),
                    enderecoCompleto,
                    telefoneField.getText(),
                    email,
                    cpfField.getText()
            );

            feedback.setTextFill(Color.DARKGREEN);
            feedback.setText("✅ Cliente criado: " + cliente.getId());
        });

        // ===== Montar layout final =====
        form.getChildren().addAll(
                title,
                nomeField,
                row1,
                row2,
                row3,
                telefoneField,
                emailField,
                cpfField,
                criarBtn,
                feedback
        );

        return form;
    }

    private MFXTextField input(String floatingText) {
        MFXTextField field = new MFXTextField();
        field.setFloatingText(floatingText);
        field.setMaxWidth(Double.MAX_VALUE);
        return field;
    }
}
