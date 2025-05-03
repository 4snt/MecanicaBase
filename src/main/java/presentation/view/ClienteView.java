package presentation.view;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import presentation.view.cliente.CriarClienteView;
import presentation.view.cliente.ListarClienteView;

public class ClienteView {

    private final BorderPane root;
    private final BorderPane contentWrapper;

    public ClienteView() {
        root = new BorderPane();
        root.setStyle("-fx-background-color: #f9fbfd;");
        root.setPadding(new Insets(30));

        VBox container = new VBox(20);
        container.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Gestão de Clientes");
        title.setFont(Font.font(null, FontWeight.BOLD, 24));

        HBox menu = new HBox(15);
        menu.setAlignment(Pos.CENTER);

        MFXButton btnCriar = new MFXButton("Criar Cliente");
        MFXButton btnListar = new MFXButton("Listar Clientes");

        styleTabButton(btnCriar);
        styleTabButton(btnListar);

        contentWrapper = new BorderPane();
        contentWrapper.setStyle("""
            -fx-background-color: white;
            -fx-background-radius: 15;
            -fx-border-radius: 15;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10, 0, 2, 2);
        """);
        contentWrapper.setPadding(new Insets(30));
        contentWrapper.setPrefWidth(600); // aumentar largura dos cards

        // Eventos
        btnCriar.setOnAction(e -> setContent(new CriarClienteView().getView()));
        btnListar.setOnAction(e -> setContent(new ListarClienteView().getView()));

        // Início padrão
        setContent(new CriarClienteView().getView());

        menu.getChildren().addAll(btnCriar, btnListar);
        container.getChildren().addAll(title, menu, contentWrapper);
        root.setCenter(container);
    }

    private void setContent(Node node) {
        contentWrapper.setCenter(node);
    }

    private void styleTabButton(MFXButton button) {
        button.setStyle("""
            -fx-background-color: #47D4DD;
            -fx-text-fill: white;
            -fx-font-weight: bold;
        """);
    }

    public BorderPane getView() {
        return root;
    }
}
