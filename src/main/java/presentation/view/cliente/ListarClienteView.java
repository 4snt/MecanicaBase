package presentation.view.cliente;

import java.util.List;

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

public class ListarClienteView {

    private final ClienteController controller = new ClienteController();

    public Node getView() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(20));
        box.setStyle("""
            -fx-background-color: white;
            -fx-background-radius: 15;
            -fx-border-radius: 15;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10, 0, 2, 2);
        """);

        Label title = new Label("Listar Clientes");
        title.setFont(Font.font(null, FontWeight.BOLD, 16));

        MFXTextField filtroField = new MFXTextField();
        filtroField.setFloatingText("Filtrar por nome/email");

        MFXButton listarBtn = new MFXButton("Listar Clientes");
        listarBtn.setStyle("-fx-background-color: #37CBD5; -fx-text-fill: white;");
        listarBtn.setPrefWidth(Double.MAX_VALUE);

        Label listaLabel = new Label();
        listaLabel.setTextFill(Color.BLACK);
        listaLabel.setWrapText(true);
        listaLabel.setStyle("-fx-font-size: 12px;");

        listarBtn.setOnAction(e -> {
            List<Cliente> clientes = controller.listar(filtroField.getText());
            StringBuilder sb = new StringBuilder();
            for (Cliente c : clientes) {
                sb.append("• ").append(c.getNome()).append(" - ").append(c.getEmail()).append("\n");
            }
            listaLabel.setText(sb.toString().isEmpty() ? "Nenhum cliente encontrado." : sb.toString());
        });

        box.getChildren().addAll(title, filtroField, listarBtn, listaLabel);
        return box;
    }
}
