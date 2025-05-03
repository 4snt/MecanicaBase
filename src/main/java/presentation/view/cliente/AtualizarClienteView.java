package presentation.view.cliente;

import domain.entities.usuarios.Cliente;
import domain.usecases.usuarios.cliente.AtualizaClienteUseCase;
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

public class AtualizarClienteView {

    private final AtualizaClienteUseCase useCase = new AtualizaClienteUseCase();
    private final Cliente cliente;

    public AtualizarClienteView(Cliente cliente) {
        this.cliente = cliente;
    }

    public Node getView() {
        VBox form = new VBox(12);
        form.setPadding(new Insets(25));
        form.setFillWidth(true);
        form.setMaxWidth(600);
        form.setStyle("-fx-background-color: transparent;");

        Label title = new Label("Atualizar Cliente");
        title.setFont(Font.font(null, FontWeight.BOLD, 16));

        // Campos separados
        MFXTextField nomeField = input("Nome", cliente.getNome());
        MFXTextField ruaField = input("Rua");
        MFXTextField numeroField = input("Número");
        MFXTextField bairroField = input("Bairro");
        MFXTextField cidadeField = input("Cidade");
        MFXTextField estadoField = input("Estado");
        MFXTextField telefoneField = input("Telefone", cliente.getTelefone());
        MFXTextField emailField = input("Email", cliente.getEmail());
        MFXTextField cpfField = input("CPF", cliente.getCpf());

        Label feedback = new Label();
        feedback.setTextFill(Color.DARKGREEN);
        feedback.setWrapText(true);

        // Layouts
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

        // Máscara CPF
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

        // Máscara Telefone
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

        MFXButton atualizarBtn = new MFXButton("Salvar Alterações");
        atualizarBtn.setPrefWidth(Double.MAX_VALUE);
        atualizarBtn.setMinHeight(40);
        VBox.setMargin(atualizarBtn, new Insets(10, 0, 0, 0));
        atualizarBtn.setStyle("""
            -fx-background-color: #014283;
            -fx-text-fill: white;
            -fx-font-weight: bold;
            -fx-font-size: 14px;
            -fx-background-radius: 5;
        """);

        atualizarBtn.setOnAction(e -> {
            if (!emailField.getText().matches("^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,}$")) {
                feedback.setTextFill(Color.RED);
                feedback.setText("❌ Email inválido.");
                return;
            }

            String enderecoCompleto = ruaField.getText() + ", " + numeroField.getText() + ", "
                    + bairroField.getText() + ", " + cidadeField.getText() + " - " + estadoField.getText();

            Cliente atualizado = useCase.use(
                    cliente.getId().toString(),
                    nomeField.getText(),
                    enderecoCompleto,
                    telefoneField.getText(),
                    emailField.getText(),
                    cpfField.getText()
            );

            if (atualizado != null) {
                feedback.setTextFill(Color.DARKGREEN);
                feedback.setText("✅ Cliente atualizado com sucesso.");
            } else {
                feedback.setTextFill(Color.RED);
                feedback.setText("❌ Cliente não encontrado.");
            }
        });

        form.getChildren().addAll(
                title,
                nomeField,
                row1,
                row2,
                row3,
                telefoneField,
                emailField,
                cpfField,
                atualizarBtn,
                feedback
        );

        return form;
    }

    private MFXTextField input(String floatingText, String valorInicial) {
        MFXTextField field = new MFXTextField();
        field.setFloatingText(floatingText);
        field.setText(valorInicial);
        field.setMaxWidth(Double.MAX_VALUE);
        return field;
    }

    private MFXTextField input(String floatingText) {
        return input(floatingText, "");
    }
}
