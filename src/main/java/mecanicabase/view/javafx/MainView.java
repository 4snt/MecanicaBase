package mecanicabase.view.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainView extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Caminho para o FXML dentro de resources
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/view/MainView.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Sistema Mecânica");
        stage.show();
    }
}
