package presentation.components;

import javafx.scene.control.Label;

public class TitleLabel extends Label {

    public TitleLabel(String text) {
        super(text);
        setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #222;");
    }
}
