package emprestimo;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsultaEmprestimosScreen {
    private GridPane layout;

    public ConsultaEmprestimosScreen(Stage stage) {
        layout = new GridPane();
        layout.getStyleClass().add("grid-pane");
        layout.setPadding(new Insets(10));
        layout.setVgap(8);
        layout.setHgap(10);

        TextField userIdField = new TextField();
        userIdField.setPromptText("User ID");
        userIdField.getStyleClass().add("text-field");
        GridPane.setConstraints(userIdField, 0, 0);

        Button consultarButton = new Button("Consultar Empréstimos");
        consultarButton.getStyleClass().add("button");
        GridPane.setConstraints(consultarButton, 1, 0);

        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.getStyleClass().add("text-area");
        GridPane.setConstraints(resultArea, 0, 1, 2, 1);

        Button backButton = new Button("Voltar ao Menu");
        backButton.getStyleClass().add("button");
        GridPane.setConstraints(backButton, 0, 2);
        backButton.setOnAction(e -> {
            MenuScreen menuScreen = new MenuScreen(stage);
            Scene scene = new Scene(menuScreen.getLayout(), 400, 300);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            stage.setScene(scene);
        });

        consultarButton.setOnAction(e -> handleConsultar(userIdField.getText(), resultArea));

        layout.getChildren().addAll(userIdField, consultarButton, resultArea, backButton);
    }

    public GridPane getLayout() {
        return layout;
    }

    private void handleConsultar(String userIdStr, TextArea resultArea) {
        int userId = Integer.parseInt(userIdStr);

        try (Connection connection = Database.getConnection()) {
            String query = "SELECT livros.titulo, emprestimos.data_emprestimo " +
                           "FROM emprestimos " +
                           "JOIN livros ON emprestimos.id_livro = livros.id " +
                           "WHERE emprestimos.id_usuario = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            StringBuilder resultText = new StringBuilder();
            while (resultSet.next()) {
                String bookTitle = resultSet.getString("titulo");
                String emprestimoDate = resultSet.getDate("data_emprestimo").toString();
                resultText.append("Livro: ").append(bookTitle).append(", Data de Empréstimo: ").append(emprestimoDate).append("\n");
            }

            if (resultText.length() > 0) {
                resultArea.setText(resultText.toString());
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Consulta de Empréstimos");
                alert.setHeaderText(null);
                alert.setContentText("Nenhum empréstimo encontrado para este usuário.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
