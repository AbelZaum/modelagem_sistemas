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
import java.time.LocalDate;

public class CadastroEmprestimoScreen {

    private GridPane layout;

    public CadastroEmprestimoScreen(Stage stage) {
        layout = new GridPane();
        layout.getStyleClass().add("grid-pane");
        layout.setPadding(new Insets(10));
        layout.setVgap(8);
        layout.setHgap(10);

        TextField userIdField = new TextField();
        userIdField.setPromptText("User ID");
        userIdField.getStyleClass().add("text-field");
        GridPane.setConstraints(userIdField, 0, 0);

        TextField bookTitleField = new TextField();
        bookTitleField.setPromptText("Book Title");
        bookTitleField.getStyleClass().add("text-field");
        GridPane.setConstraints(bookTitleField, 0, 1);

        Button emprestarButton = new Button("Emprestar");
        emprestarButton.getStyleClass().add("button");
        GridPane.setConstraints(emprestarButton, 1, 1);

        Button backButton = new Button("Voltar ao Menu");
        backButton.getStyleClass().add("button");
        GridPane.setConstraints(backButton, 0, 2);
        backButton.setOnAction(e -> {
            MenuScreen menuScreen = new MenuScreen(stage);
            Scene scene = new Scene(menuScreen.getLayout(), 400, 300);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            stage.setScene(scene);
        });

        emprestarButton.setOnAction(e -> handleEmprestar(userIdField.getText(), bookTitleField.getText()));

        layout.getChildren().addAll(userIdField, bookTitleField, emprestarButton, backButton);
    }

    public GridPane getLayout() {
        return layout;
    }

    private void handleEmprestar(String userIdStr, String bookTitle) {
        int userId = Integer.parseInt(userIdStr);

        try (Connection connection = Database.getConnection()) {
            // Check if book exists and is available
            String bookQuery = "SELECT id FROM livros WHERE titulo = ? AND disponivel = TRUE";
            PreparedStatement bookStatement = connection.prepareStatement(bookQuery);
            bookStatement.setString(1, bookTitle);
            ResultSet bookResultSet = bookStatement.executeQuery();

            if (bookResultSet.next()) {
                int bookId = bookResultSet.getInt("id");

                // Check if user has no pending debts
                String userQuery = "SELECT tem_pendencia FROM usuarios WHERE id = ?";
                PreparedStatement userStatement = connection.prepareStatement(userQuery);
                userStatement.setInt(1, userId);
                ResultSet userResultSet = userStatement.executeQuery();

                if (userResultSet.next() && !userResultSet.getBoolean("tem_pendencia")) {
                    // Register the loan
                    String emprestimoQuery = "INSERT INTO emprestimos (id_usuario, id_livro, data_emprestimo) VALUES (?, ?, ?)";
                    PreparedStatement emprestimoStatement = connection.prepareStatement(emprestimoQuery);
                    emprestimoStatement.setInt(1, userId);
                    emprestimoStatement.setInt(2, bookId);
                    emprestimoStatement.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
                    emprestimoStatement.executeUpdate();

                    // Update book availability
                    String updateBookQuery = "UPDATE livros SET disponivel = FALSE WHERE id = ?";
                    PreparedStatement updateBookStatement = connection.prepareStatement(updateBookQuery);
                    updateBookStatement.setInt(1, bookId);
                    updateBookStatement.executeUpdate();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Empréstimo");
                    alert.setHeaderText(null);
                    alert.setContentText("Empréstimo registrado com sucesso!");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText(null);
                    alert.setContentText("Usuário tem pendências ou não foi encontrado.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText(null);
                alert.setContentText("Livro não encontrado ou não disponível.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
