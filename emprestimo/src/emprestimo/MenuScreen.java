package emprestimo;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuScreen {
    private VBox layout;

    public MenuScreen(Stage stage) {
        layout = new VBox(10);
        layout.getStyleClass().add("vbox");
        layout.setPadding(new Insets(10));

        Button cadastrarEmprestimoButton = new Button("Cadastrar Empréstimo");
        cadastrarEmprestimoButton.getStyleClass().add("button");
        cadastrarEmprestimoButton.setOnAction(e -> {
            CadastrarEmprestimoScreen cadastrarEmprestimoScreen = new CadastrarEmprestimoScreen(stage);
            Scene scene = new Scene(cadastrarEmprestimoScreen.getLayout(), 400, 300);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            stage.setScene(scene);
        });

        Button consultaEmprestimosButton = new Button("Consultar Empréstimos");
        consultaEmprestimosButton.getStyleClass().add("button");
        consultaEmprestimosButton.setOnAction(e -> {
            ConsultaEmprestimosScreen consultaEmprestimosScreen = new ConsultaEmprestimosScreen(stage);
            Scene scene = new Scene(consultaEmprestimosScreen.getLayout(), 400, 300);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            stage.setScene(scene);
        });

        Button verificaDebitosButton = new Button("Verificar Débitos");
        verificaDebitosButton.getStyleClass().add("button");
        verificaDebitosButton.setOnAction(e -> {
            VerificaDebitosScreen verificaDebitosScreen = new VerificaDebitosScreen(stage);
            Scene scene = new Scene(verificaDebitosScreen.getLayout(), 400, 300);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            stage.setScene(scene);
        });

        Button inventarioButton = new Button("Inventário de Livros");
        inventarioButton.getStyleClass().add("button");
        inventarioButton.setOnAction(e -> {
            InventarioScreen inventarioScreen = new InventarioScreen(stage);
            Scene scene = new Scene(inventarioScreen.getLayout(), 600, 400);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            stage.setScene(scene);
        });

        layout.getChildren().addAll(cadastrarEmprestimoButton, consultaEmprestimosButton, verificaDebitosButton, inventarioButton);
    }

    public VBox getLayout() {
        return layout;
    }
}
