package emprestimo;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VerificaDebitosScreen {

    private GridPane layout;

    public VerificaDebitosScreen(Stage stage) {
        layout = new GridPane();
        layout.getStyleClass().add("grid-pane");
        layout.setPadding(new Insets(10));
        layout.setVgap(8);
        layout.setHgap(10);

        TextField userIdField = new TextField();
        userIdField.setPromptText("ID do Usuario");
        userIdField.getStyleClass().add("text-field");
        GridPane.setConstraints(userIdField, 0, 0);

        Button verificarButton = new Button("Verificar Débitos");
        verificarButton.getStyleClass().add("button");
        verificarButton.setOnAction(e -> handleVerificar(userIdField.getText()));
        GridPane.setConstraints(verificarButton, 1, 0);

        Button backButton = new Button("Voltar ao Menu");
        backButton.getStyleClass().add("button");
        GridPane.setConstraints(backButton, 0, 1);
        backButton.setOnAction(e -> {
            MenuScreen menuScreen = new MenuScreen(stage);
            Scene scene = new Scene(menuScreen.getLayout(), 400, 300);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            stage.setScene(scene);
        });

        layout.getChildren().addAll(userIdField, verificarButton, backButton);
    }

    public GridPane getLayout() {
        return layout;
    }

    private void handleVerificar(String userIdStr) {
        int userId = Integer.parseInt(userIdStr);

        try (Connection connection = Database.getConnection()) {
            String query = "SELECT tem_pendencia FROM usuarios WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                boolean temPendencia = resultSet.getBoolean("tem_pendencia");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Verificação de Débitos");
                alert.setHeaderText(null);
                alert.setContentText(temPendencia ? "Usuário tem pendências." : "Usuário não tem pendências.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText(null);
                alert.setContentText("Usuário não encontrado.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
