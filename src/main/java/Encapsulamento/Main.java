package Encapsulamento;

public class Main {
    public static void main(String[] args) {
        Pessoa pessoa = new Pessoa();

        // Definindo os valores usando setters
        pessoa.setNome("Carlos");
        pessoa.setIdade(30);

        // Exibindo as informações da pessoa
        pessoa.exibirInformacoes();

        // Tentativa de definir uma idade inválida
        pessoa.setIdade(-5);  // Exibe "Idade inválida."

        // Tentativa de definir uma idade válida
        pessoa.setIdade(35);
        pessoa.exibirInformacoes();
    }
}
