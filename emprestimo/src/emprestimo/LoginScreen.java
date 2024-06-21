package emprestimo;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginScreen {
    private GridPane layout;

    public LoginScreen(Stage stage) {
        layout = new GridPane();
        layout.getStyleClass().add("grid-pane");
        layout.setPadding(new Insets(10));
        layout.setVgap(8);
        layout.setHgap(10);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Usuario");
        usernameField.getStyleClass().add("text-field");
        GridPane.setConstraints(usernameField, 0, 0);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Senha");
        passwordField.getStyleClass().add("text-field");
        GridPane.setConstraints(passwordField, 0, 1);

        Button loginButton = new Button("Login");
        loginButton.getStyleClass().add("button");
        loginButton.setOnAction(e -> handleLogin(stage, usernameField.getText(), passwordField.getText()));
        GridPane.setConstraints(loginButton, 1, 1);

        layout.getChildren().addAll(usernameField, passwordField, loginButton);
    }

    public GridPane getLayout() {
        return layout;
    }

    private void handleLogin(Stage stage, String username, String password) {
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT * FROM usuarios WHERE login  = ? AND senha  = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                MenuScreen menuScreen = new MenuScreen(stage);
                Scene scene = new Scene(menuScreen.getLayout(), 400, 300);
                scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                stage.setScene(scene);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro de Login");
                alert.setHeaderText(null);
                alert.setContentText("Usuario ou Senha incorretos.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
