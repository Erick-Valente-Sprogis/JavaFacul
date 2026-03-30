package Herança;

public class PagamentoPayPal extends Pagamento {
    // Atributo específico para pagamento via PayPal
    private String emailUsuario;

    // Construtor
    public PagamentoPayPal(double valor, String emailUsuario) {
        super(valor);
        this.emailUsuario = emailUsuario;
    }
    public void processarPagamento() {
        System.out.println("Processando pagamento de R$ " + valor + " via PayPal. Conta: " + emailUsuario);
        // Lógica específica para processar o pagamento via PayPal
    }
}
