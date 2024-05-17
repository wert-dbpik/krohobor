package ru.wert.krohobor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ru.wert.krohobor.controllers.KrohoborController;

public class StartApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/krohobor.fxml"));
        Parent root = loader.load();

        KrohoborController controller = loader.getController();
        controller.init();
        stage.setTitle("Крохобор-МК");
        stage.getIcons().add(new Image("/pics/logo.png"));
        stage.setResizable(false);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/krohobor.css").toString());
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
