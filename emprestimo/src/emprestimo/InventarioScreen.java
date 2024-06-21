package emprestimo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InventarioScreen {

    private GridPane layout;
    private TableView<Livro> tableView;

    public InventarioScreen(Stage stage) {
        layout = new GridPane();
        layout.getStyleClass().add("grid-pane");
        layout.setPadding(new Insets(10));
        layout.setVgap(8);
        layout.setHgap(10);

        tableView = new TableView<>();
        TableColumn<Livro, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Livro, String> tituloColumn = new TableColumn<>("Título");
        tituloColumn.setCellValueFactory(new PropertyValueFactory<>("titulo"));

        TableColumn<Livro, String> disponivelColumn = new TableColumn<>("Disponível");
        disponivelColumn.setCellValueFactory(new PropertyValueFactory<>("disponivel"));

        TableColumn<Livro, Integer> quantidadeColumn = new TableColumn<>("Quantidade");
        quantidadeColumn.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        tableView.getColumns().addAll(idColumn, tituloColumn, disponivelColumn, quantidadeColumn);

        loadBookData();

        Button backButton = new Button("Voltar ao Menu");
        backButton.getStyleClass().add("button");
        backButton.setOnAction(e -> {
            MenuScreen menuScreen = new MenuScreen(stage);
            Scene scene = new Scene(menuScreen.getLayout(), 400, 300);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            stage.setScene(scene);
        });

        layout.add(tableView, 0, 0);
        layout.add(backButton, 0, 1);
    }

    public GridPane getLayout() {
        return layout;
    }

    private void loadBookData() {
        ObservableList<Livro> livros = FXCollections.observableArrayList();

        String query = "SELECT id, titulo, disponivel, quantidade FROM livros";

        try (Connection connection = Database.getConnection(); PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String titulo = resultSet.getString("titulo");
                String disponivel = resultSet.getString("disponivel");
                int quantidade = resultSet.getInt("quantidade");

                // Adiciona o livro à lista
                livros.add(new Livro(id, titulo, disponivel.equals("sim") ? "Sim" : "Não", quantidade));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tableView.setItems(livros);
    }

    public static class Livro {

        private final int id;
        private final String titulo;
        private final String disponivel;
        private final int quantidade;

        public Livro(int id, String titulo, String disponivel, int quantidade) {
            this.id = id;
            this.titulo = titulo;
            this.disponivel = disponivel;
            this.quantidade = quantidade;
        }

        public int getId() {
            return id;
        }

        public String getTitulo() {
            return titulo;
        }

        public String getDisponivel() {
            return disponivel;
        }

        public int getQuantidade() {
            return quantidade;
        }
    }
}
