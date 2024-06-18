package emprestimo;

public class Livro {

    private String titulo;
    private boolean disponivel;
    private int quantidade;

    public Livro(String titulo, boolean disponivel, int quantidade) {
        this.titulo = titulo;
        this.disponivel = disponivel;
        this.quantidade = quantidade;
    }

    public String getTitulo() {
        return titulo;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public int getQuantidade() {
        return quantidade;
    }
}
