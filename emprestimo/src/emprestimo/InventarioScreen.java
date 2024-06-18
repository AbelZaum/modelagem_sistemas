package emprestimo;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InventarioScreen {
    private BorderPane layout;

    public InventarioScreen(Stage stage) {
        layout = new BorderPane();
        layout.getStyleClass().add("border-pane");
        layout.setPadding(new Insets(10));

        Label titleLabel = new Label("Inventário de Livros");
        titleLabel.getStyleClass().add("label-title");
        layout.setTop(titleLabel);

        TableView<Livro> tableView = new TableView<>();
        tableView.getStyleClass().add("table-view");

        TableColumn<Livro, String> titleColumn = new TableColumn<>("Título");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        titleColumn.getStyleClass().add("table-column-header");

        TableColumn<Livro, Boolean> statusColumn = new TableColumn<>("Disponível");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("disponivel"));
        statusColumn.getStyleClass().add("table-column-header");

        TableColumn<Livro, Integer> quantityColumn = new TableColumn<>("Quantidade");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        quantityColumn.getStyleClass().add("table-column-header");

        tableView.getColumns().addAll(titleColumn, statusColumn, quantityColumn);
        loadBookData(tableView);

        layout.setCenter(tableView);

        Button backButton = new Button("Voltar");
        backButton.getStyleClass().add("button");
        backButton.setOnAction(e -> {
            MenuScreen menuScreen = new MenuScreen(stage);
            Scene scene = new Scene(menuScreen.getLayout(), 400, 300);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            stage.setScene(scene);
        });

        HBox bottomLayout = new HBox(10);
        bottomLayout.getStyleClass().add("bottom-layout");
        bottomLayout.getChildren().add(backButton);
        bottomLayout.setPadding(new Insets(10));
        layout.setBottom(bottomLayout);
    }

    public BorderPane getLayout() {
        return layout;
    }

    private void loadBookData(TableView<Livro> tableView) {
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT titulo, disponivel, quantidade FROM livros";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String titulo = resultSet.getString("titulo");
                boolean disponivel = resultSet.getBoolean("disponivel");
                int quantidade = resultSet.getInt("quantidade");

                Livro livro = new Livro(titulo, disponivel, quantidade);
                tableView.getItems().add(livro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
