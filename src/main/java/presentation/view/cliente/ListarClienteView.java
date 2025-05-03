package presentation.view.cliente;

import java.util.List;

import controllers.ClienteController;
import domain.entities.usuarios.Cliente;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import presentation.view.MainView;

public class ListarClienteView {

    private final ClienteController controller = new ClienteController();

    public Node getView() {
        VBox box = new VBox(12);
        box.setPadding(new Insets(25));
        box.setMaxWidth(600);
        box.setStyle("""
            -fx-background-color: transparent;
        """);

        Label title = new Label("Listar Clientes");
        title.setFont(Font.font(null, FontWeight.BOLD, 16));

        MFXTextField filtroField = new MFXTextField();
        filtroField.setFloatingText("Filtrar por nome/email");

        MFXButton listarBtn = new MFXButton("Buscar");
        listarBtn.setStyle("""
            -fx-background-color: #37CBD5;
            -fx-text-fill: white;
            -fx-font-weight: bold;
        """);
        listarBtn.setPrefWidth(Double.MAX_VALUE);
        listarBtn.setMinHeight(36);

        VBox listaContainer = new VBox(8);
        listaContainer.setPadding(new Insets(10));
        listaContainer.setStyle("""
            -fx-background-color: white;
            -fx-background-radius: 12;
            -fx-border-radius: 12;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 1, 1);
        """);

        listarBtn.setOnAction(e -> {
            listaContainer.getChildren().clear();
            List<Cliente> clientes = controller.listar(filtroField.getText());

            if (clientes.isEmpty()) {
                Label vazio = new Label("Nenhum cliente encontrado.");
                vazio.setTextFill(Color.GRAY);
                listaContainer.getChildren().add(vazio);
                return;
            }

            for (Cliente c : clientes) {
                HBox linha = new HBox(10);
                linha.setAlignment(Pos.CENTER_LEFT);
                linha.setStyle("-fx-padding: 10; -fx-background-color: #F4FFFE; -fx-background-radius: 8;");

                VBox info = new VBox(2);
                Label nome = new Label(c.getNome());
                nome.setFont(Font.font(null, FontWeight.BOLD, 13));
                Label email = new Label(c.getEmail());
                email.setFont(Font.font(11));
                info.getChildren().addAll(nome, email);
                HBox.setHgrow(info, Priority.ALWAYS);

                MFXButton btnAtualizar = new MFXButton("Atualizar");
                btnAtualizar.setStyle("""
                    -fx-background-color: #014283;
                    -fx-text-fill: white;
                    -fx-font-size: 12px;
                """);

                btnAtualizar.setOnAction(ev -> {
                    AtualizarClienteView atualizarView = new AtualizarClienteView(c);
                    Node atualizarNode = atualizarView.getView();

                    // Aqui substituímos o conteúdo principal da interface
                    MainView.setConteudoCentral(atualizarNode);
                });
                linha.getChildren().addAll(info, btnAtualizar);
                listaContainer.getChildren().add(linha);
            }
        });

        box.getChildren().addAll(title, filtroField, listarBtn, listaContainer);
        return box;
    }
}
