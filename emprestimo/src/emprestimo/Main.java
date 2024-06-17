package emprestimo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        LoginScreen loginScreen = new LoginScreen(primaryStage);
        Scene scene = new Scene(loginScreen.getLayout(), 400, 300);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sistema de Empréstimo de Livros");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
