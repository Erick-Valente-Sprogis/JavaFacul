package Construtor;

public class Pessoa {
    private String nome;
    private int idade;

    // Construtor padrão
    public Pessoa() {
        this.nome = "Desconhecido";
        this.idade = 0;
    }

    // Construtor com parâmetros
    public Pessoa(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }

    public void exibirInformacoes() {
        System.out.println("Nome: " + nome);
        System.out.println("Idade: " + idade);
    }
}
