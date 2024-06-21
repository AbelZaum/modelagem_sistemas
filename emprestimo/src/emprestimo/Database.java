package emprestimo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {

    private static final String URL = "jdbc:mysql://localhost:3306/biblioteca";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static boolean cadastrarEmprestimo(String livroTitulo, String usuarioId) {
        String verificarLivroQuery = "SELECT quantidade FROM livros WHERE titulo = ? AND quantidade > 0";
        String atualizarQuantidadeQuery = "UPDATE livros SET quantidade = quantidade - 1 WHERE titulo = ?";
        String verificarUsuarioQuery = "SELECT tem_pendencia FROM usuarios WHERE id = ?";
        String inserirEmprestimoQuery = "INSERT INTO emprestimos (livroTitulo, usuarioId) VALUES (?, ?)";

        try (Connection connection = getConnection()) {
            // Verificar se o usuário tem pendências
            try (PreparedStatement verificarUsuarioStmt = connection.prepareStatement(verificarUsuarioQuery)) {
                verificarUsuarioStmt.setString(1, usuarioId);
                ResultSet usuarioRs = verificarUsuarioStmt.executeQuery();

                if (usuarioRs.next() && usuarioRs.getBoolean("tem_pendencia")) {
                    // Usuário tem pendências, não permitir empréstimo
                    return false;
                }
            }

            // Verificar se o livro está disponível
            try (PreparedStatement verificarLivroStmt = connection.prepareStatement(verificarLivroQuery)) {
                verificarLivroStmt.setString(1, livroTitulo);
                ResultSet livroRs = verificarLivroStmt.executeQuery();

                if (livroRs.next()) {
                    int quantidade = livroRs.getInt("quantidade");
                    if (quantidade > 0) {
                        // Atualizar quantidade do livro
                        try (PreparedStatement atualizarQuantidadeStmt = connection.prepareStatement(atualizarQuantidadeQuery)) {
                            atualizarQuantidadeStmt.setString(1, livroTitulo);
                            atualizarQuantidadeStmt.executeUpdate();
                        }

                        // Inserir empréstimo
                        try (PreparedStatement inserirEmprestimoStmt = connection.prepareStatement(inserirEmprestimoQuery)) {
                            inserirEmprestimoStmt.setString(1, livroTitulo);
                            inserirEmprestimoStmt.setString(2, usuarioId);
                            inserirEmprestimoStmt.executeUpdate();
                            return true;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
