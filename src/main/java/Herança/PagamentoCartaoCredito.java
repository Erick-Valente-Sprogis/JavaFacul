package Herança;

public class PagamentoCartaoCredito extends Pagamento {
    // Atributo específico para pagamento com cartão
    private String numeroCartao;
    // Construtor
    public PagamentoCartaoCredito(double valor, String numeroCartao) {
        super(valor);
        this.numeroCartao = numeroCartao;
    }
    public void processarPagamento() {
        System.out.println("Processando pagamento de R$ " + valor + " com Cartão de Crédito. Cartão: " + numeroCartao);
        // Lógica específica para processar o pagamento com cartão de crédito
    }
}

