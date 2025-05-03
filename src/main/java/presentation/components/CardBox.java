package presentation.components;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

public class CardBox extends VBox {

    public CardBox() {
        super(10);
        setMaxWidth(300);
        setPadding(new Insets(15));
        setStyle("-fx-background-color: white; "
                + "-fx-background-radius: 12px; "
                + "-fx-border-radius: 12px; "
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0.3, 0, 4);");
    }
}
