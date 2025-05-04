package presentation.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainView extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/view/Main.fxml"));
        VBox root = loader.load();

        Scene scene = new Scene(root);
        stage.setTitle("Sistema Mecânica - Painel Principal");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
