package emprestimo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.InputStream;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        LoginScreen loginScreen = new LoginScreen(primaryStage);
        Scene scene = new Scene(loginScreen.getLayout(), 400, 300);

        primaryStage.setTitle("Sistema de Empréstimo de Livros");

        // Tente carregar a imagem
        try (InputStream iconStream = getClass().getResourceAsStream("icon.png")) {
            if (iconStream == null) {
                throw new RuntimeException("Icone nao encontrado");
            }
            Image icon = new Image(iconStream);
            primaryStage.getIcons().add(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }

        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
