package mecanicabase;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import mecanicabase.controller.MainViewController;
import mecanicabase.infra.ApplicationContext;

public class MainView extends Application {

    private final ApplicationContext context = new ApplicationContext();

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mecanicabase/view/fxml/Main.fxml"));
        loader.setControllerFactory(param -> new MainViewController(context));

        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/mecanicabase/view/css/main.css").toExternalForm());

        stage.setTitle("Sistema Oficina Mecânica");
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/mecanicabase/view/assets/icon.png"))); // opcional
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
