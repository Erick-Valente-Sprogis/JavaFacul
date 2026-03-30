package Herança;

public class Main {
    public static void main(String[] args) {
        // Criando um pagamento com Cartão de Crédito
        Pagamento pagamento1 = new PagamentoCartaoCredito(250.00, "1234-5678-9012-3456");
        pagamento1.processarPagamento();
        // Criando um pagamento via PayPal
        Pagamento pagamento2 = new PagamentoPayPal(100.00, "usuario@paypal.com");
        pagamento2.processarPagamento();
    }
}
