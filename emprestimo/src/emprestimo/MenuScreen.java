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
        layout.setPadding(new Insets(10));
        layout.getStyleClass().add("grid-pane");

        Button cadastroEmprestimoButton = new Button("Cadastrar Empréstimo");
        Button verificaDebitosButton = new Button("Verificar Débitos");
        Button consultaEmprestimosButton = new Button("Consultar Empréstimos");

        cadastroEmprestimoButton.getStyleClass().add("button");
        verificaDebitosButton.getStyleClass().add("button");
        consultaEmprestimosButton.getStyleClass().add("button");

        cadastroEmprestimoButton.setOnAction(e -> openCadastroEmprestimo(stage));
        verificaDebitosButton.setOnAction(e -> openVerificaDebitos(stage));
        consultaEmprestimosButton.setOnAction(e -> openConsultaEmprestimos(stage));

        layout.getChildren().addAll(cadastroEmprestimoButton, verificaDebitosButton, consultaEmprestimosButton);
    }

    public VBox getLayout() {
        return layout;
    }

    private void openCadastroEmprestimo(Stage stage) {
        CadastroEmprestimoScreen cadastroEmprestimoScreen = new CadastroEmprestimoScreen(stage);
        Scene scene = new Scene(cadastroEmprestimoScreen.getLayout(), 400, 300);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
    }

    private void openVerificaDebitos(Stage stage) {
        VerificaDebitosScreen verificaDebitosScreen = new VerificaDebitosScreen(stage);
        Scene scene = new Scene(verificaDebitosScreen.getLayout(), 400, 300);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
    }

    private void openConsultaEmprestimos(Stage stage) {
        ConsultaEmprestimosScreen consultaEmprestimosScreen = new ConsultaEmprestimosScreen(stage);
        Scene scene = new Scene(consultaEmprestimosScreen.getLayout(), 400, 300);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
    }
}
    