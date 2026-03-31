package Geral;

import java.util.ArrayList;
import java.util.List;

/**
 * =============================================================================
 *  PADRÕES DE PROJETO EM JAVA — GoF
 *  Prof. Kesede R. Julio | Exemplos Completos e Comentados
 * =============================================================================
 *
 * Este arquivo reúne todos os exemplos dos padrões cobertos no curso:
 *
 *  CRIACIONAIS:
 *   1. Factory Method     — Criação delegada a subclasses
 *   2. Abstract Factory   — Famílias de objetos compatíveis
 *   3. Builder            — Construção passo a passo
 *   4. Prototype          — Clonagem de objetos existentes
 *   5. Singleton          — Instância única (4 versões)
 *
 *  ESTRUTURAIS:
 *   6. Adapter            — Adaptar interfaces incompatíveis
 *   7. Bridge             — Separar abstração de implementação
 *   8. Decorator          — Adicionar comportamentos dinamicamente
 *   9. Composite          — Estruturas hierárquicas uniformes
 *  10. Facade             — Interface simplificada para subsistemas
 *
 * =============================================================================
 */
public class PadroesDeProjetoJava {

    public static void main(String[] args) {
        System.out.println("=== 1. FACTORY METHOD ===");
        testarFactoryMethod();

        System.out.println("\n=== 2. ABSTRACT FACTORY ===");
        testarAbstractFactory();

        System.out.println("\n=== 3. BUILDER ===");
        testarBuilder();

        System.out.println("\n=== 4. PROTOTYPE ===");
        testarPrototype();

        System.out.println("\n=== 5. SINGLETON ===");
        testarSingleton();

        System.out.println("\n=== 6. ADAPTER ===");
        testarAdapter();

        System.out.println("\n=== 7. BRIDGE ===");
        testarBridge();

        System.out.println("\n=== 8. DECORATOR ===");
        testarDecorator();

        System.out.println("\n=== 9. COMPOSITE ===");
        testarComposite();

        System.out.println("\n=== 10. FACADE ===");
        testarFacade();
    }

    // =========================================================================
    // 1. FACTORY METHOD
    // =========================================================================
    //
    // CONCEITO: Define uma interface para criação de objetos, mas deixa as
    // subclasses decidirem qual classe instanciar.
    // Em vez de usar "new" diretamente, o cliente chama um método fábrica.
    //
    // ESTRUTURA:
    //   Transporte (Product/Interface)
    //      ├── Caminhao (ConcreteProduct)
    //      └── Navio    (ConcreteProduct)
    //   TransporteFactory (Creator abstrato)
    //      ├── CaminhaoFactory (ConcreteCreator)
    //      └── NavioFactory    (ConcreteCreator)
    // =========================================================================

    /** Product: interface comum para todos os produtos criados */
    interface Transporte {
        void entregar();
    }

    /** ConcreteProduct: implementação 1 */
    static class Caminhao implements Transporte {
        @Override
        public void entregar() {
            System.out.println("Entrega feita por caminhão!");
        }
    }

    /** ConcreteProduct: implementação 2 */
    static class Navio implements Transporte {
        @Override
        public void entregar() {
            System.out.println("Entrega feita por navio!");
        }
    }

    /** Creator: declara o método fábrica abstrato */
    static abstract class TransporteFactory {
        // O método fábrica — subclasses decidem o que criar
        public abstract Transporte criarTransporte();
    }

    /** ConcreteCreator: cria Caminhao */
    static class CaminhaoFactory extends TransporteFactory {
        @Override
        public Transporte criarTransporte() {
            return new Caminhao();
        }
    }

    /** ConcreteCreator: cria Navio */
    static class NavioFactory extends TransporteFactory {
        @Override
        public Transporte criarTransporte() {
            return new Navio();
        }
    }

    static void testarFactoryMethod() {
        // Client não sabe qual classe concreta está sendo criada
        TransporteFactory fabrica1 = new CaminhaoFactory();
        Transporte t1 = fabrica1.criarTransporte();
        t1.entregar(); // Entrega feita por caminhão!

        TransporteFactory fabrica2 = new NavioFactory();
        Transporte t2 = fabrica2.criarTransporte();
        t2.entregar(); // Entrega feita por navio!

        // Para adicionar Avião no futuro: basta criar AviaoFactory e Aviao
        // sem modificar o código existente (princípio Open/Closed)
    }


    // =========================================================================
    // 2. ABSTRACT FACTORY
    // =========================================================================
    //
    // CONCEITO: Cria FAMÍLIAS de objetos relacionados e compatíveis entre si.
    // Cada fábrica concreta produz um conjunto coeso de produtos.
    //
    // DIFERENÇA DO FACTORY METHOD:
    //   Factory Method → cria 1 tipo de produto
    //   Abstract Factory → cria N tipos de produtos compatíveis entre si
    //
    // ESTRUTURA:
    //   Veiculo (AbstractProduct A)         Pacote (AbstractProduct B)
    //     ├── Caminhao / Moto / Navio          ├── CaixaPequena
    //     └── Balsa                            └── CaixaGrande
    //   TransporteAbstractFactory (Abstract Factory)
    //     ├── TerrestreFactory
    //     └── MaritimoFactory
    // =========================================================================

    /** Abstract Product A */
    interface Veiculo {
        void entregar();
    }

    /** Abstract Product B */
    interface Pacote {
        void embalar();
    }

    // Concrete Products — Terrestre
    static class CaminhaoV implements Veiculo {
        public void entregar() { System.out.println("Entrega por caminhão."); }
    }
    static class Moto implements Veiculo {
        public void entregar() { System.out.println("Entrega por moto."); }
    }
    static class CaixaPequena implements Pacote {
        public void embalar() { System.out.println("Pacote pequeno embalado."); }
    }

    // Concrete Products — Marítimo
    static class NavioV implements Veiculo {
        public void entregar() { System.out.println("Entrega por navio."); }
    }
    static class Balsa implements Veiculo {
        public void entregar() { System.out.println("Entrega por balsa."); }
    }
    static class CaixaGrande implements Pacote {
        public void embalar() { System.out.println("Pacote grande embalado."); }
    }

    /** Abstract Factory: declara os métodos de criação para cada produto */
    interface TransporteAbstractFactory {
        Veiculo criarVeiculo();
        Pacote  criarPacote();
    }

    /** Concrete Factory 1: cria produtos terrestres (compatíveis entre si) */
    static class TransporteTerrestreFactory implements TransporteAbstractFactory {
        public Veiculo criarVeiculo() { return new CaminhaoV(); }
        public Pacote  criarPacote()  { return new CaixaPequena(); }
    }

    /** Concrete Factory 2: cria produtos marítimos via navio */
    static class TransporteMaritimoFactory implements TransporteAbstractFactory {
        public Veiculo criarVeiculo() { return new NavioV(); }
        public Pacote  criarPacote()  { return new CaixaGrande(); }
    }

    /** Concrete Factory 3: entrega terrestre rápida por moto */
    static class TransporteMotoFactory implements TransporteAbstractFactory {
        public Veiculo criarVeiculo() { return new Moto(); }
        public Pacote  criarPacote()  { return new CaixaPequena(); }
    }

    /** Concrete Factory 4: travessia marítima alternativa por balsa */
    static class TransporteBalsaFactory implements TransporteAbstractFactory {
        public Veiculo criarVeiculo() { return new Balsa(); }
        public Pacote  criarPacote()  { return new CaixaGrande(); }
    }

    static void testarAbstractFactory() {
        // Transporte Terrestre — caminhão com caixa pequena
        TransporteAbstractFactory terrestreFactory = new TransporteTerrestreFactory();
        Veiculo vT = terrestreFactory.criarVeiculo();
        Pacote  pT = terrestreFactory.criarPacote();
        vT.entregar(); // Entrega por caminhão.
        pT.embalar();  // Pacote pequeno embalado.

        // Transporte Marítimo — navio com caixa grande
        TransporteAbstractFactory maritimoFactory = new TransporteMaritimoFactory();
        Veiculo vM = maritimoFactory.criarVeiculo();
        Pacote  pM = maritimoFactory.criarPacote();
        vM.entregar(); // Entrega por navio.
        pM.embalar();  // Pacote grande embalado.

        // Terrestre Rápido — moto com caixa pequena
        TransporteAbstractFactory motoFactory = new TransporteMotoFactory();
        Veiculo vMoto = motoFactory.criarVeiculo();
        Pacote  pMoto = motoFactory.criarPacote();
        vMoto.entregar(); // Entrega por moto.
        pMoto.embalar();  // Pacote pequeno embalado.

        // Marítimo Alternativo — balsa com caixa grande
        TransporteAbstractFactory balsaFactory = new TransporteBalsaFactory();
        Veiculo vB = balsaFactory.criarVeiculo();
        Pacote  pB = balsaFactory.criarPacote();
        vB.entregar(); // Entrega por balsa.
        pB.embalar();  // Pacote grande embalado.
    }


    // =========================================================================
    // 3. BUILDER
    // =========================================================================
    //
    // CONCEITO: Separa a construção de objetos complexos da sua representação.
    // Permite criar o objeto passo a passo usando uma API fluente (method chaining).
    //
    // VANTAGEM: Evita construtores telescópicos (com dezenas de parâmetros).
    // Ex: new Pedido("João", "Notebook", 2, "Crédito", "Expressa") — confuso!
    // Com Builder: .setCliente("João").setItem("Notebook")... — legível!
    //
    // NOTA: O Lombok (@Builder) automatiza este padrão em projetos reais.
    // =========================================================================

    static class Pedido {
        // Atributos privados — acessíveis apenas pelo builder interno
        private String cliente;
        private String item;
        private int    quantidade;
        private String pagamento;
        private String entrega;

        // Construtor privado: impede instanciação direta, força uso do Builder
        private Pedido() {}

        @Override
        public String toString() {
            return "Pedido{" +
                "cliente='" + cliente + '\'' +
                ", item='" + item + '\'' +
                ", quantidade=" + quantidade +
                ", pagamento='" + pagamento + '\'' +
                ", entrega='" + entrega + '\'' +
                '}';
        }

        /** Builder como classe interna estática */
        public static class PedidoBuilder {
            private Pedido pedido;

            public PedidoBuilder() {
                this.pedido = new Pedido(); // cria o objeto vazio
            }

            // Cada set retorna "this" para permitir encadeamento
            public PedidoBuilder setCliente(String cliente) {
                pedido.cliente = cliente;
                return this;
            }
            public PedidoBuilder setItem(String item) {
                pedido.item = item;
                return this;
            }
            public PedidoBuilder setQuantidade(int quantidade) {
                pedido.quantidade = quantidade;
                return this;
            }
            public PedidoBuilder setPagamento(String pagamento) {
                pedido.pagamento = pagamento;
                return this;
            }
            public PedidoBuilder setEntrega(String entrega) {
                pedido.entrega = entrega;
                return this;
            }

            /** Retorna o objeto final construído */
            public Pedido build() {
                return this.pedido;
            }
        }
    }

    static void testarBuilder() {
        // API Fluente: cada método retorna o próprio builder (method chaining)
        Pedido pedido = new Pedido.PedidoBuilder()
            .setCliente("João Silva")
            .setItem("Notebook")
            .setQuantidade(2)
            .setPagamento("Cartão de Crédito")
            .setEntrega("Entrega Expressa")
            .build();

        System.out.println(pedido);
        // Pedido{cliente='João Silva', item='Notebook', quantidade=2, ...}

        // Podemos criar variações facilmente, sem afetar o original
        Pedido pedidoSimples = new Pedido.PedidoBuilder()
            .setCliente("Maria")
            .setItem("Mouse")
            .setQuantidade(1)
            .build(); // pagamento e entrega ficam null — OK para pedidos simples

        System.out.println(pedidoSimples);
    }


    // =========================================================================
    // 4. PROTOTYPE
    // =========================================================================
    //
    // CONCEITO: Cria novos objetos clonando um objeto existente (protótipo).
    // Útil quando a criação do zero é cara ou complexa.
    //
    // Java já provê suporte nativo via interface Cloneable e super.clone().
    //
    // ⚠️ ATENÇÃO — Cópia Rasa (Shallow) vs Cópia Profunda (Deep):
    //   super.clone() faz cópia RASA: copia primitivos e referências, mas
    //   NÃO duplica objetos internos. Se Contrato tivesse um campo
    //   List<String> itens, o clone compartilharia a mesma lista!
    //   Para deep copy: clone manual dos campos que são objetos.
    // =========================================================================

    interface Documento extends Cloneable {
        Documento clonar();
        void exibirInfo();
    }

    static class Contrato implements Documento {
        private String tipo;
        private String cliente;

        public Contrato(String cliente) {
            this.tipo    = "Contrato Padrão";
            this.cliente = cliente;
        }

        @Override
        public Documento clonar() {
            try {
                // super.clone() copia todos os campos (shallow copy)
                return (Documento) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException("Erro ao clonar documento", e);
            }
        }

        @Override
        public void exibirInfo() {
            System.out.println("Documento: " + tipo + ", Cliente: " + cliente);
        }

        public void setCliente(String cliente) {
            this.cliente = cliente;
        }
    }

    static void testarPrototype() {
        // Criamos o contrato base (objeto caro de criar, imagine ele com muitos campos)
        Contrato contratoBase = new Contrato("Empresa XYZ");
        contratoBase.exibirInfo(); // Documento: Contrato Padrão, Cliente: Empresa XYZ

        // Clonamos e personalizamos — o base permanece intacto
        Contrato contratoClone = (Contrato) contratoBase.clonar();
        contratoClone.setCliente("Empresa ABC");

        contratoBase.exibirInfo();  // ainda: Empresa XYZ (não foi alterado)
        contratoClone.exibirInfo(); // Empresa ABC (clone modificado)
    }


    // =========================================================================
    // 5. SINGLETON
    // =========================================================================
    //
    // CONCEITO: Garante que uma classe tenha APENAS UMA instância e provê
    // acesso global a ela.
    //
    // VERSÕES:
    //   V1 — Lazy (simples, mas não thread-safe)
    //   V2 — Thread-safe com synchronized (correto, mas pode ser lento)
    //   V3 — Eager (criado na inicialização, thread-safe, mas sempre alocado)
    //   V4 — Enum (MELHOR opção: thread-safe, serialization-safe, reflex-safe)
    // =========================================================================

    // --- Versão 1: Lazy (Preguiçosa) — NÃO use em multi-thread! ---
    static class SingletonLazy {
        private static SingletonLazy instancia; // null até ser pedida

        private SingletonLazy() {} // construtor privado

        public static SingletonLazy getInstancia() {
            if (instancia == null) {
                // PROBLEMA: duas threads podem entrar aqui ao mesmo tempo
                instancia = new SingletonLazy();
            }
            return instancia;
        }

        public void mostrarMensagem() {
            System.out.println("Singleton Lazy ativo! hashCode: " + hashCode());
        }
    }

    // --- Versão 2: Thread-Safe com synchronized ---
    static class SingletonThreadSafe {
        private static SingletonThreadSafe instancia;

        private SingletonThreadSafe() {}

        // "synchronized" garante que só uma thread por vez acessa este método
        public static synchronized SingletonThreadSafe getInstancia() {
            if (instancia == null) {
                instancia = new SingletonThreadSafe();
            }
            return instancia;
        }
    }

    // --- Versão 3: Eager (Antecipada) — cria na inicialização da classe ---
    static class SingletonEager {
        // A instância é criada quando a classe é carregada (antes de qualquer uso)
        private static final SingletonEager instancia = new SingletonEager();

        private SingletonEager() {}

        public static SingletonEager getInstancia() {
            return instancia; // sem verificações — já existe
        }
    }

    // --- Versão 4: Enum — MELHOR opção no Java moderno ---
    enum GerenciadorConfiguracao {
        INSTANCIA; // a única instância possível

        private String nomeApp = "Minha Aplicação";

        public void exibirConfig() {
            System.out.println("Configuração atual: " + nomeApp);
        }

        public void setNomeApp(String nome) {
            this.nomeApp = nome;
        }
    }

    static void testarSingleton() {
        // Versão 1 — Lazy (não thread-safe)
        SingletonLazy obj1 = SingletonLazy.getInstancia();
        SingletonLazy obj2 = SingletonLazy.getInstancia();
        obj1.mostrarMensagem();
        System.out.println("Lazy — mesma instância? " + (obj1 == obj2)); // true

        // Versão 2 — Thread-Safe com synchronized
        SingletonThreadSafe ts1 = SingletonThreadSafe.getInstancia();
        SingletonThreadSafe ts2 = SingletonThreadSafe.getInstancia();
        System.out.println("ThreadSafe — mesma instância? " + (ts1 == ts2)); // true

        // Versão 3 — Eager (criado na inicialização da classe)
        SingletonEager e1 = SingletonEager.getInstancia();
        SingletonEager e2 = SingletonEager.getInstancia();
        System.out.println("Eager — mesma instância? " + (e1 == e2)); // true

        // Versão 4 — Enum (melhor opção em produção)
        GerenciadorConfiguracao config = GerenciadorConfiguracao.INSTANCIA;
        config.exibirConfig();
        config.setNomeApp("Novo Sistema");
        GerenciadorConfiguracao.INSTANCIA.exibirConfig(); // reflete a mudança
    }


    // =========================================================================
    // 6. ADAPTER
    // =========================================================================
    //
    // CONCEITO: Permite que classes com interfaces incompatíveis trabalhem juntas.
    // Age como um "tradutor" entre dois sistemas.
    //
    // TIPOS:
    //   Adapter de Objeto (recomendado): usa composição — tem uma referência
    //     ao Adaptee e converte as chamadas para o formato dele.
    //   Adapter de Classe: usa herança múltipla (não disponível em Java).
    //
    // CASO REAL: Adaptadores de tomada, wrappers de API legada, JDBC drivers.
    // =========================================================================

    /** Target: interface que o cliente espera usar */
    interface TomadaEuropeia {
        void conectar();
    }

    /** Adaptee: classe existente com interface incompatível */
    static class TomadaBrasileira {
        public void ligarNaTomadaBrasileira() {
            System.out.println("Plug conectado na tomada brasileira!");
        }
    }

    /** Adapter: implementa Target e usa composição para chamar o Adaptee */
    static class AdapterTomada implements TomadaEuropeia {
        private TomadaBrasileira tomadaBrasileira; // composição

        public AdapterTomada(TomadaBrasileira tomadaBrasileira) {
            this.tomadaBrasileira = tomadaBrasileira;
        }

        @Override
        public void conectar() {
            // Converte a chamada do Target para o formato do Adaptee
            tomadaBrasileira.ligarNaTomadaBrasileira();
        }
    }

    static void testarAdapter() {
        TomadaBrasileira tomadaBR = new TomadaBrasileira();

        // Client usa TomadaEuropeia — não sabe que internamente é brasileira!
        TomadaEuropeia adaptador = new AdapterTomada(tomadaBR);
        adaptador.conectar(); // Plug conectado na tomada brasileira!
    }


    // =========================================================================
    // 7. BRIDGE
    // =========================================================================
    //
    // CONCEITO: Desacopla uma abstração da sua implementação para que ambas
    // possam variar de forma independente.
    //
    // PROBLEMA SEM BRIDGE:
    //   Tipo de notificação (Pedido, Suporte) × Forma de envio (Email, SMS)
    //   = PedidoEmail, PedidoSMS, SuporteEmail, SuporteSMS — explosão de classes!
    //
    // COM BRIDGE:
    //   Abstração (Notificacao) tem uma referência para o Implementor (Mensagem)
    //   → Podemos combinar qualquer tipo com qualquer forma de envio
    //
    // DIFERENÇA DO ADAPTER:
    //   Adapter: torna incompatíveis compatíveis (resolve problema existente)
    //   Bridge:  projeta a separação desde o início (evita problema futuro)
    // =========================================================================

    /** Implementor: interface de baixo nível para envio */
    interface Mensagem {
        void enviar(String texto);
    }

    /** Concrete Implementor 1 */
    static class MensagemEmail implements Mensagem {
        @Override
        public void enviar(String texto) {
            System.out.println("Enviando E-mail: " + texto);
        }
    }

    /** Concrete Implementor 2 */
    static class MensagemSMS implements Mensagem {
        @Override
        public void enviar(String texto) {
            System.out.println("Enviando SMS: " + texto);
        }
    }

    /** Abstraction: classe de alto nível, contém referência ao Implementor */
    static abstract class Notificacao {
        // A "ponte" — referência para o Implementor
        protected Mensagem mensagem;

        public Notificacao(Mensagem mensagem) {
            this.mensagem = mensagem;
        }

        public abstract void enviarNotificacao(String texto);
    }

    /** Refined Abstraction 1 */
    static class NotificacaoPedido extends Notificacao {
        public NotificacaoPedido(Mensagem mensagem) { super(mensagem); }

        @Override
        public void enviarNotificacao(String texto) {
            mensagem.enviar("Pedido: " + texto);
        }
    }

    /** Refined Abstraction 2 */
    static class NotificacaoSuporte extends Notificacao {
        public NotificacaoSuporte(Mensagem mensagem) { super(mensagem); }

        @Override
        public void enviarNotificacao(String texto) {
            mensagem.enviar("Suporte: " + texto);
        }
    }

    static void testarBridge() {
        // Pedido via Email
        Notificacao n1 = new NotificacaoPedido(new MensagemEmail());
        n1.enviarNotificacao("Seu pedido foi processado!");

        // Suporte via SMS
        Notificacao n2 = new NotificacaoSuporte(new MensagemSMS());
        n2.enviarNotificacao("Seu chamado foi aberto!");

        // Pedido via SMS — nova combinação sem criar nova classe!
        Notificacao n3 = new NotificacaoPedido(new MensagemSMS());
        n3.enviarNotificacao("Pedido cancelado.");
    }


    // =========================================================================
    // 8. DECORATOR
    // =========================================================================
    //
    // CONCEITO: Adiciona responsabilidades extras a um objeto dinamicamente,
    // sem alterar sua classe. Alternativa à herança para extensão de comportamento.
    //
    // MECANISMO: O Decorator "envolve" o objeto original (composição) e pode
    // adicionar comportamento antes e/ou depois da chamada do método original.
    //
    // EXEMPLO NA JDK:
    //   new BufferedReader(new InputStreamReader(new FileInputStream("f.txt")))
    //   → InputStreamReader decora FileInputStream (converte bytes → chars)
    //   → BufferedReader decora InputStreamReader (adiciona buffer)
    //
    // DIFERENÇA DO COMPOSITE:
    //   Composite: um objeto contém VÁRIOS filhos (estrutura de árvore)
    //   Decorator: um objeto envolve UM outro objeto (estrutura linear/pilha)
    // =========================================================================

    /** Component: interface base */
    interface Cafe {
        String getDescricao();
        double getCusto();
    }

    /** Concrete Component: implementação base */
    static class CafeSimples implements Cafe {
        @Override
        public String getDescricao() { return "Café Simples"; }

        @Override
        public double getCusto() { return 5.0; }
    }

    /** Decorator base: implementa Cafe E contém uma referência a outro Cafe */
    static abstract class CafeDecorator implements Cafe {
        protected Cafe cafeDecorado; // o objeto sendo "embrulhado"

        public CafeDecorator(Cafe cafeDecorado) {
            this.cafeDecorado = cafeDecorado;
        }

        // Por padrão, delega ao objeto decorado
        @Override
        public String getDescricao() { return cafeDecorado.getDescricao(); }

        @Override
        public double getCusto()     { return cafeDecorado.getCusto(); }
    }

    /** Concrete Decorator 1: adiciona leite */
    static class Leite extends CafeDecorator {
        public Leite(Cafe c) { super(c); }

        @Override
        public String getDescricao() {
            return super.getDescricao() + ", com Leite";
        }

        @Override
        public double getCusto() {
            return super.getCusto() + 2.0; // adiciona custo do leite
        }
    }

    /** Concrete Decorator 2: adiciona chocolate */
    static class Chocolate extends CafeDecorator {
        public Chocolate(Cafe c) { super(c); }

        @Override
        public String getDescricao() {
            return super.getDescricao() + ", com Chocolate";
        }

        @Override
        public double getCusto() {
            return super.getCusto() + 3.0; // adiciona custo do chocolate
        }
    }

    static void testarDecorator() {
        // Café simples
        Cafe meuCafe = new CafeSimples();
        System.out.println(meuCafe.getDescricao() + " - R$" + meuCafe.getCusto());

        // Café com leite
        meuCafe = new Leite(meuCafe);
        System.out.println(meuCafe.getDescricao() + " - R$" + meuCafe.getCusto());

        // Café com leite e chocolate
        meuCafe = new Chocolate(meuCafe);
        System.out.println(meuCafe.getDescricao() + " - R$" + meuCafe.getCusto());

        // Café com duplo chocolate (decorator empilhado duas vezes!)
        meuCafe = new Chocolate(meuCafe);
        System.out.println(meuCafe.getDescricao() + " - R$" + meuCafe.getCusto());
    }


    // =========================================================================
    // 9. COMPOSITE
    // =========================================================================
    //
    // CONCEITO: Permite tratar objetos individuais e grupos de objetos de forma
    // uniforme. Define uma estrutura de árvore de objetos.
    //
    // ANALOGIA:
    //   Um arquivo e uma pasta são tratados da mesma forma (exibir()).
    //   Uma pasta pode conter arquivos E outras pastas (recursividade).
    //
    // DIFERENÇA DO DECORATOR:
    //   Composite: estrutura de ÁRVORE (1 pai, N filhos)
    //   Decorator: estrutura LINEAR (cada objeto envolve exatamente 1 outro)
    // =========================================================================

    /** Component: interface comum para Arquivo e Pasta */
    interface ArquivoComponent {
        void exibir(String indent);
    }

    /** Leaf: objeto folha — não contém filhos */
    static class Arquivo implements ArquivoComponent {
        private String nome;

        public Arquivo(String nome) { this.nome = nome; }

        @Override
        public void exibir(String indent) {
            System.out.println(indent + "Arquivo: " + nome);
        }
    }

    /** Composite: pode conter Leaf e outros Composite */
    static class Pasta implements ArquivoComponent {
        private String nome;
        private List<ArquivoComponent> conteudo = new ArrayList<>();

        public Pasta(String nome) { this.nome = nome; }

        public void adicionar(ArquivoComponent c) { conteudo.add(c); }
        public void remover(ArquivoComponent c)   { conteudo.remove(c); }

        @Override
        public void exibir(String indent) {
            System.out.println(indent + "[Pasta: " + nome + "]");
            // Recursão: cada filho chama o próprio exibir() com indentação acrescida
            for (ArquivoComponent c : conteudo) {
                c.exibir(indent + "  ");
            }
        }
    }

    static void testarComposite() {
        // Arquivos individuais (folhas)
        Arquivo arq1 = new Arquivo("documento.txt");
        Arquivo arq2 = new Arquivo("foto.jpg");
        Arquivo arq3 = new Arquivo("planilha.xlsx");

        // Pasta raiz
        Pasta pastaRaiz = new Pasta("Meus Arquivos");
        pastaRaiz.adicionar(arq1);
        pastaRaiz.adicionar(arq2);

        // Subpasta dentro da raiz
        Pasta subPasta = new Pasta("Trabalho");
        subPasta.adicionar(arq3);

        // A pasta raiz contém arquivos E outra pasta
        pastaRaiz.adicionar(subPasta);

        // Uma única chamada exibir() navega toda a hierarquia recursivamente
        pastaRaiz.exibir("");
        // [Pasta: Meus Arquivos]
        //   Arquivo: documento.txt
        //   Arquivo: foto.jpg
        //   [Pasta: Trabalho]
        //     Arquivo: planilha.xlsx
    }


    // =========================================================================
    // 10. FACADE
    // =========================================================================
    //
    // CONCEITO: Fornece uma interface simplificada para um subsistema complexo.
    // Esconde a complexidade interna e expõe apenas o necessário.
    //
    // DIFERENÇA DO ADAPTER:
    //   Adapter: faz duas interfaces INCOMPATÍVEIS funcionarem juntas
    //   Facade:  SIMPLIFICA o acesso a um sistema que já funciona, mas é complexo
    //
    // EXEMPLO REAL: javax.mail (JavaMail) é uma Facade para o protocolo SMTP/IMAP
    // =========================================================================

    // --- Subsistema (classes "internas" que o cliente não precisa conhecer) ---

    static class Tela {
        public void ligar() {
            System.out.println("Tela ligada.");
        }
        public void ajustarBrilho() {
            System.out.println("Brilho ajustado.");
        }
    }

    static class Som {
        public void ligar() {
            System.out.println("Som ligado.");
        }
        public void ajustarVolume() {
            System.out.println("Volume ajustado.");
        }
    }

    static class Player {
        public void carregarFilme(String filme) {
            System.out.println("Carregando filme: " + filme);
        }
        public void reproduzir() {
            System.out.println("Filme iniciado!");
        }
    }

    // --- Facade: único ponto de contato com o subsistema ---

    static class HomeTheaterFacade {
        // Referências para todos os componentes do subsistema
        private Tela   tela   = new Tela();
        private Som    som    = new Som();
        private Player player = new Player();

        /**
         * Método de alto nível que orquestra todo o subsistema.
         * O cliente só precisa chamar este método — não sabe dos outros.
         */
        public void assistirFilme(String filme) {
            System.out.println("\nPreparando para assistir: " + filme);
            tela.ligar();
            tela.ajustarBrilho();
            som.ligar();
            som.ajustarVolume();
            player.carregarFilme(filme);
            player.reproduzir();
            System.out.println("Aproveite o filme!\n");
        }
    }

    static void testarFacade() {
        HomeTheaterFacade homeTheater = new HomeTheaterFacade();

        // Client não conhece Tela, Som, Player — só usa a Facade
        homeTheater.assistirFilme("O Poderoso Chefão");
        homeTheater.assistirFilme("Inception");
    }
}
