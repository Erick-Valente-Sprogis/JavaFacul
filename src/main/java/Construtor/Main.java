package Construtor;

public class Main {
    public static void main(String[] args) {
        // Chama o construtor padrão (implícito)
        Pessoa pessoa1 = new Pessoa();
        pessoa1.exibirInformacoes();

        // Chama o construtor com parâmetros
        Pessoa pessoa2 = new Pessoa("Carlos", 30);
        pessoa2.exibirInformacoes();
    }
}
