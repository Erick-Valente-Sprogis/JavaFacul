package Herança;

public class Pagamento {
    // Atributos comuns a todos os pagamentos
    protected double valor;
    // Construtor
    public Pagamento(double valor) {
        this.valor = valor;
    }
    // Método genérico para processar pagamento
    public void processarPagamento() {
        System.out.println("Processando pagamento genérico de R$ " + valor);
    }

}