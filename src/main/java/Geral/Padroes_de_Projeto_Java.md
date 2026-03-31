# Padrões de Projeto em Java (GoF)
> Prof. Kesede R. Julio | Notas de Estudo Detalhadas

---

## O que é um Padrão de Projeto?

Padrões de Projeto são **soluções típicas para problemas comuns** em projetos de software orientados a objetos. Eles foram catalogados no livro *"Design Patterns: Elements of Reusable Object-Oriented Software"* (1994), escrito pelo grupo chamado **Gang of Four (GoF)**, que apresenta **23 padrões**.

### Características importantes

- **Não é código**, mas um conceito de alto nível para solução de um problema específico.
- **Pode ser customizado** conforme a necessidade do projeto.
- O padrão permite ver o resultado e suas funcionalidades, mas a **ordem de implementação é decidida pelo desenvolvedor**.
- Já foram **testados e aprovados** — você aprende com a experiência de outros.
- Definem uma **linguagem comum** para comunicação eficiente da equipe.

### ⚠️ Atenção!
O encantamento com um padrão pode levar à sua aplicação em problemas que poderiam ter uma solução mais simples. **É necessário adaptar o padrão ao contexto do projeto.**

---

## Classificação dos Padrões

| Nível | Tipo | Descrição |
|-------|------|-----------|
| Baixo nível | **Idiomáticos** | Básicos, aplicados a uma única linguagem |
| Alto nível | **Arquitetônicos** | Universais, aplicados em qualquer linguagem e para todo o projeto |

### Por propósito (GoF)

| Categoria | Propósito | Padrões |
|-----------|-----------|---------|
| **Criacionais** | Mecanismos de criação de objetos | Factory Method, Abstract Factory, Builder, Prototype, Singleton |
| **Estruturais** | Como montar objetos e classes em estruturas maiores | Adapter, Bridge, Composite, Decorator, Facade, Flyweight, Proxy |
| **Comportamentais** | Comunicação e responsabilidades entre objetos | Chain of Responsibility, Command, Iterator, Mediator, Memento, Observer, State, Strategy, Template Method, Interpreter, Visitor |

---

---

# 🏗️ PADRÕES CRIACIONAIS

> Fornecem mecanismos de criação de objetos que aumentam a flexibilidade e a reutilização de código.

---

## 1. Factory Method (Método Fábrica / Construtor Virtual)

### Conceito
Fornece uma **interface para criar objetos em uma superclasse**, mas permite que as **subclasses alterem o tipo** de objetos que serão criados.

Em vez de chamar diretamente `new`, o cliente chama um **método fábrica**, que delega a criação para subclasses.

> 💡 Amplamente usado em **Spring** e bibliotecas como **JDBC**.

### Vantagens
- ✔ Reduz o acoplamento entre classes concretas
- ✔ Facilita a extensão do código sem modificar classes existentes
- ✔ Permite adicionar novos produtos facilmente
- ✔ Melhora a organização, separando a lógica de criação

### Estrutura

| Elemento | Papel |
|----------|-------|
| **Product** | Interface para os objetos que serão criados |
| **ConcreteProduct** | Implementa a interface do produto |
| **Creator** | Declara o método fábrica, retorna um `Product` |
| **ConcreteCreator** | Implementa o método fábrica, cria um `ConcreteProduct` |

### Case: Sistema de Logística

**Problema:** Sistema com apenas transporte por Caminhão. Com a expansão, precisa-se incluir Navio sem alterar o código existente.

**Solução:** Usar Factory Method com uma interface `Transporte`.

```
<<interface>> Transporte
       + entregar()
          /        \
    Caminhao       Navio
```

### Código Resumido

```java
// 1. Produto (interface)
interface Transporte { void entregar(); }

// 2. Produtos Concretos
class Caminhao implements Transporte {
    public void entregar() { System.out.println("Entrega feita por caminhão!"); }
}
class Navio implements Transporte {
    public void entregar() { System.out.println("Entrega feita por navio!"); }
}

// 3. Criador abstrato
abstract class TransporteFactory {
    public abstract Transporte criarTransporte();
}

// 4. Criadores Concretos
class CaminhaoFactory extends TransporteFactory {
    public Transporte criarTransporte() { return new Caminhao(); }
}
class NavioFactory extends TransporteFactory {
    public Transporte criarTransporte() { return new Navio(); }
}

// 5. Client
TransporteFactory fabrica = new CaminhaoFactory();
Transporte t = fabrica.criarTransporte();
t.entregar(); // "Entrega feita por caminhão!"
```

---

## 2. Abstract Factory (Fábrica Abstrata)

### Conceito
Fornece uma **interface para criar famílias de objetos relacionados** sem especificar suas classes concretas.

Diferente do Factory Method (cria **um único tipo** de objeto), o Abstract Factory **agrupa fábricas** para produzir **conjuntos de objetos compatíveis**.

> 💡 Muito usado no **Spring**, em bibliotecas GUI (**Swing, JavaFX**) e sistemas que exigem flexibilidade.

### Vantagens
- ✔ Garante compatibilidade entre objetos criados
- ✔ Reduz acoplamento (o cliente não conhece as classes concretas)
- ✔ Facilita a criação de novas famílias sem modificar o código existente

### Diferença: Factory Method x Abstract Factory

| Característica | Factory Method | Abstract Factory |
|---------------|----------------|-----------------|
| Criação | Um único tipo de produto | Uma família de produtos relacionados |
| Estrutura | Subclasses decidem qual produto criar | Cada fábrica cria um conjunto de produtos compatíveis |
| Uso | Variações de um único objeto | Várias categorias de objetos interdependentes |

### Estrutura

| Elemento | Papel |
|----------|-------|
| **Abstract Factory** | Interface com métodos para criar produtos relacionados |
| **Concrete Factory** | Implementa a fábrica, criando objetos concretos |
| **Abstract Product** | Interface comum para os produtos |
| **Concrete Product** | Implementações específicas dos produtos |
| **Client** | Usa a fábrica abstrata sem conhecer as classes concretas |

### Case: Sistema de Entrega

Entregas **Terrestre** (Caminhão + CaixaPequena) e **Marítima** (Navio + CaixaGrande) — cada modo cria um conjunto compatível de veículo e pacote.

```java
// Produtos abstratos
interface Veiculo { void entregar(); }
interface Pacote  { void embalar();  }

// Fábrica abstrata
interface TransporteFactory {
    Veiculo criarVeiculo();
    Pacote  criarPacote();
}

// Fábricas concretas
class TerrestreFactory implements TransporteFactory {
    public Veiculo criarVeiculo() { return new Caminhao(); }
    public Pacote  criarPacote()  { return new CaixaPequena(); }
}
class MaritimoFactory implements TransporteFactory {
    public Veiculo criarVeiculo() { return new Navio(); }
    public Pacote  criarPacote()  { return new CaixaGrande(); }
}
```

---

## 3. Builder (Construtor)

### Conceito
**Separa a construção de um objeto complexo** da sua representação final.

Permite criar objetos **passo a passo**, evitando construtores longos com muitos parâmetros.

> 💡 Usado em objetos imutáveis (como `StringBuilder`), em **APIs Fluent** e no **Lombok** (`@Builder`).

### Vantagens
- ✔ Facilita a criação de objetos complexos
- ✔ Código mais legível e flexível (método encadeado / fluent)
- ✔ Evita construtores longos e cheios de parâmetros
- ✔ Permite imutabilidade se configurado corretamente

### Estrutura

| Elemento | Papel |
|----------|-------|
| **Product** | O objeto complexo que será construído |
| **Builder** | Interface com os passos para construir o objeto |
| **Concrete Builder** | Implementa o Builder e retorna o objeto final |
| **Director** *(Opcional)* | Controla a ordem de construção |
| **Client** | Usa o Builder para construir o objeto |

### Case: Pedido de Compra

```java
class Pedido {
    private String cliente, item, pagamento, entrega;
    private int quantidade;

    private Pedido() {} // construtor privado

    public static class PedidoBuilder {
        private Pedido pedido = new Pedido();

        public PedidoBuilder setCliente(String c)   { pedido.cliente   = c; return this; }
        public PedidoBuilder setItem(String i)       { pedido.item      = i; return this; }
        public PedidoBuilder setQuantidade(int q)    { pedido.quantidade= q; return this; }
        public PedidoBuilder setPagamento(String p)  { pedido.pagamento = p; return this; }
        public PedidoBuilder setEntrega(String e)    { pedido.entrega   = e; return this; }
        public Pedido build() { return pedido; }
    }
}

// Uso (API Fluent)
Pedido pedido = new Pedido.PedidoBuilder()
    .setCliente("João Silva")
    .setItem("Notebook")
    .setQuantidade(2)
    .setPagamento("Cartão de Crédito")
    .setEntrega("Entrega Expressa")
    .build();
```

---

## 4. Prototype (Protótipo)

### Conceito
Permite **copiar objetos existentes** sem precisar depender de suas classes concretas.

Em vez de criar do zero com `new`, o padrão usa **clonagem** para gerar cópias.

> 💡 Usado em bibliotecas gráficas (clone de objetos visuais), **games** (cópia de personagens) e frameworks que precisam duplicar objetos rapidamente.

### Vantagens
- ✔ Evita recriação de objetos complexos (melhor desempenho)
- ✔ Independente da classe concreta
- ✔ Permite criar variações do objeto rapidamente

### Estrutura

| Elemento | Papel |
|----------|-------|
| **Prototype** | Interface com o método `clone()` |
| **Concrete Prototype** | Implementa `clone()`, retornando cópia do objeto |
| **Client** | Duplica objetos chamando `clone()` |

### Case: Clonagem de Contrato

```java
interface Documento extends Cloneable {
    Documento clonar();
    void exibirInfo();
}

class Contrato implements Documento {
    private String tipo = "Contrato Padrão";
    private String cliente;

    public Contrato(String cliente) { this.cliente = cliente; }

    public Documento clonar() {
        try {
            return (Documento) super.clone(); // clonagem superficial
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Erro ao clonar", e);
        }
    }

    public void exibirInfo() {
        System.out.println("Documento: " + tipo + ", Cliente: " + cliente);
    }

    public void setCliente(String c) { this.cliente = c; }
}

// Uso
Contrato base  = new Contrato("Empresa XYZ");
Contrato clone = (Contrato) base.clonar();
clone.setCliente("Empresa ABC"); // só o clone muda
```

> ⚠️ `super.clone()` faz **cópia rasa (shallow copy)**. Para objetos com referências internas, implemente **deep copy** manualmente.

---

## 5. Singleton

### Conceito
**Garante que uma classe tenha apenas uma única instância** e fornece um ponto global de acesso a ela.

> 💡 Muito usado em **Connection Pool**, **configurações globais** e **cache**.

### Vantagens
- ✔ Garante uma única instância
- ✔ Evita consumo desnecessário de memória
- ✔ Facilita acesso global ao objeto
- ✔ Thread-safe quando bem implementado

### Versões em Java

#### Versão 1 — Lazy (Preguiçosa)
```java
class Singleton {
    private static Singleton instancia;
    private Singleton() {}
    public static Singleton getInstancia() {
        if (instancia == null) instancia = new Singleton();
        return instancia;
    }
}
```
> ⚠️ **Não é thread-safe!** Múltiplas threads podem criar instâncias diferentes.

#### Versão 2 — Thread-Safe com `synchronized`
```java
public static synchronized Singleton getInstancia() {
    if (instancia == null) instancia = new Singleton();
    return instancia;
}
```
> ⚠️ Funciona, mas `synchronized` tem custo de desempenho em alta concorrência.

#### Versão 3 — Eager (Antecipada)
```java
class Singleton {
    private static final Singleton instancia = new Singleton();
    private Singleton() {}
    public static Singleton getInstancia() { return instancia; }
}
```
> ✔ Thread-safe, mas **cria a instância mesmo que nunca seja usada**.

#### Versão 4 — Enum ✅ (Melhor opção em Java)
```java
public enum GerenciadorConfiguracao {
    INSTANCIA;
    private String nomeApp = "Minha Aplicação";

    public void exibirConfig() {
        System.out.println("Configuração: " + nomeApp);
    }
}

// Uso
GerenciadorConfiguracao.INSTANCIA.exibirConfig();
```
> ✔ Thread-safe, protege contra reflexão e serialização. **Recomendado pelo Effective Java (Bloch).**

---

---

# 🔩 PADRÕES ESTRUTURAIS

> Explicam como montar objetos e classes em estruturas maiores, mantendo flexibilidade e eficiência.

---

## 6. Adapter

### Conceito
Permite que **interfaces incompatíveis trabalhem juntas**.

Age como um **"tradutor"**, permitindo que uma classe existente seja usada sem modificar seu código.

> 💡 Muito usado em **APIs**, **drivers de hardware** e **integração de sistemas legados**.

### Tipos de Adapter
- 🔹 **Adapter de Classe** — usa herança para adaptar a interface. (Menos flexível)
- 🔹 **Adapter de Objeto** — usa composição (referência ao Adaptee). (Mais flexível e recomendado)

### Estrutura

| Elemento | Papel |
|----------|-------|
| **Target** | Interface esperada pelo sistema |
| **Adaptee** | Classe existente com interface incompatível |
| **Adapter** | Classe intermediária que converte chamadas de Target para Adaptee |
| **Client** | Usa Target sem se preocupar com o Adaptee |

### Case: Tomada Europeia x Brasileira

```java
// Target: interface esperada
interface TomadaEuropeia { void conectar(); }

// Adaptee: classe existente incompatível
class TomadaBrasileira {
    public void ligarNaTomadaBrasileira() {
        System.out.println("Plug conectado na tomada brasileira!");
    }
}

// Adapter: faz a "tradução"
class AdapterTomada implements TomadaEuropeia {
    private TomadaBrasileira tomadaBR;
    public AdapterTomada(TomadaBrasileira t) { this.tomadaBR = t; }

    @Override
    public void conectar() {
        tomadaBR.ligarNaTomadaBrasileira(); // chama o método "antigo"
    }
}

// Client
TomadaEuropeia adaptador = new AdapterTomada(new TomadaBrasileira());
adaptador.conectar();
```

---

## 7. Bridge (Ponte)

### Conceito
**Separa uma abstração da sua implementação**, permitindo que ambas evoluam independentemente.

Evita a **explosão de subclasses** quando há múltiplas variações de uma classe.

> 💡 Muito usado em **frameworks gráficos** (UI com diferentes renderizadores) e **sistemas de comunicação**.

### Vantagens
- ✔ Separa abstração e implementação
- ✔ Evita explosão de subclasses
- ✔ Adicionar novos tipos de notificações ou formas de envio sem impactar o código existente

### Diferença: Bridge x Adapter

| Característica | Bridge | Adapter |
|---------------|--------|---------|
| Propósito | Abstração e implementação evoluem separadamente | Torna interfaces incompatíveis compatíveis |
| Uso | Muitas variações de uma classe | Integrar classes existentes com interface diferente |

### Estrutura

| Elemento | Papel |
|----------|-------|
| **Abstraction** | Interface de alto nível, referencia o Implementor |
| **Refined Abstraction** | Estende Abstraction com funcionalidades adicionais |
| **Implementor** | Interface para a implementação concreta |
| **Concrete Implementor** | Implementa a interface do Implementor |

### Case: Sistema de Notificações

**Sem Bridge:** precisaria de `PedidoEmail`, `PedidoSMS`, `SuporteEmail`, `SuporteSMS`... (explosão de subclasses)

**Com Bridge:** separa o **tipo de notificação** da **forma de envio**.

```java
// Implementor
interface Mensagem { void enviar(String texto); }

// Concrete Implementors
class MensagemEmail implements Mensagem {
    public void enviar(String t) { System.out.println("E-mail: " + t); }
}
class MensagemSMS implements Mensagem {
    public void enviar(String t) { System.out.println("SMS: " + t); }
}

// Abstraction
abstract class Notificacao {
    protected Mensagem mensagem;
    public Notificacao(Mensagem m) { this.mensagem = m; }
    public abstract void enviarNotificacao(String texto);
}

// Refined Abstractions
class NotificacaoPedido extends Notificacao {
    public NotificacaoPedido(Mensagem m) { super(m); }
    public void enviarNotificacao(String t) { mensagem.enviar("Pedido: " + t); }
}
class NotificacaoSuporte extends Notificacao {
    public NotificacaoSuporte(Mensagem m) { super(m); }
    public void enviarNotificacao(String t) { mensagem.enviar("Suporte: " + t); }
}

// Client — combina abstração + implementação livremente
Notificacao n1 = new NotificacaoPedido(new MensagemEmail());
n1.enviarNotificacao("Pedido processado!"); // "E-mail: Pedido: Pedido processado!"

Notificacao n2 = new NotificacaoSuporte(new MensagemSMS());
n2.enviarNotificacao("Chamado aberto!");    // "SMS: Suporte: Chamado aberto!"
```

---

## 8. Decorator (Decorador)

### Conceito
Permite **adicionar comportamentos a objetos dinamicamente**, sem modificar suas classes.

Segue o **princípio da composição**: cria uma estrutura onde podemos empilhar funcionalidades.

> 💡 Muito usado em **streams Java** (`BufferedReader`, `InputStreamReader`), bibliotecas de UI e sistemas de logging.

### Vantagens
- ✔ Adiciona funcionalidades sem modificar classes existentes
- ✔ Flexível — pode empilhar vários decorators
- ✔ Segue o princípio **Open/Closed** (aberto para extensão, fechado para modificação)
- ✔ Evita criação excessiva de subclasses

### Diferença: Decorator x Adapter

| Característica | Decorator | Adapter |
|---------------|-----------|---------|
| Propósito | Adiciona funcionalidades a um objeto | Torna interfaces incompatíveis compatíveis |
| Uso | Modificar objeto de forma flexível | Integrar sistemas com interfaces diferentes |

### Estrutura

| Elemento | Papel |
|----------|-------|
| **Component** | Interface base com o comportamento principal |
| **Concrete Component** | Implementação concreta do Component |
| **Decorator** | Classe abstrata que implementa Component e contém referência para outro Component |
| **Concrete Decorator** | Adiciona funcionalidades extras |

### Case: Cafeteria

**Sem Decorator:** precisaria de `CaféComLeite`, `CaféComChocolate`, `CaféComLeiteEChocolate`...

**Com Decorator:** empilha ingredientes dinamicamente.

```java
interface Cafe {
    String getDescricao();
    double getCusto();
}

class CafeSimples implements Cafe {
    public String getDescricao() { return "Café Simples"; }
    public double getCusto()     { return 5.0; }
}

// Decorator base
abstract class CafeDecorator implements Cafe {
    protected Cafe cafeDecorado;
    public CafeDecorator(Cafe c) { this.cafeDecorado = c; }
    public String getDescricao() { return cafeDecorado.getDescricao(); }
    public double getCusto()     { return cafeDecorado.getCusto(); }
}

// Decorators concretos
class Leite extends CafeDecorator {
    public Leite(Cafe c) { super(c); }
    public String getDescricao() { return super.getDescricao() + ", com Leite"; }
    public double getCusto()     { return super.getCusto() + 2.0; }
}
class Chocolate extends CafeDecorator {
    public Chocolate(Cafe c) { super(c); }
    public String getDescricao() { return super.getDescricao() + ", com Chocolate"; }
    public double getCusto()     { return super.getCusto() + 3.0; }
}

// Client — empilhando dinamicamente
Cafe meuCafe = new CafeSimples();       // R$5.00
meuCafe = new Leite(meuCafe);           // R$7.00
meuCafe = new Chocolate(meuCafe);       // R$10.00
System.out.println(meuCafe.getDescricao() + " - R$" + meuCafe.getCusto());
// "Café Simples, com Leite, com Chocolate - R$10.0"
```

---

## 9. Composite

### Conceito
Permite **tratar objetos individuais e composições de objetos de forma uniforme**.

Útil para **estruturas hierárquicas** (árvores) onde grupos de objetos são manipulados como um único objeto.

> 💡 Usado em: **Sistemas de arquivos** (pastas/arquivos), **componentes de UI** (layouts), **estruturas organizacionais**.

### Vantagens
- ✔ Trata objetos individuais e composições de forma uniforme
- ✔ Facilita a manipulação de estruturas hierárquicas
- ✔ Reduz a complexidade ao lidar com grupos de objetos

### Diferença: Composite x Decorator

| Característica | Composite | Decorator |
|---------------|-----------|-----------|
| Propósito | Estruturas hierárquicas (árvore) | Adicionar funcionalidades dinamicamente |
| Relação | Componentes podem conter outros componentes | Um objeto "envolve" outro |
| Exemplo | Pastas e arquivos | Café com leite e chocolate |

### Estrutura

| Elemento | Papel |
|----------|-------|
| **Component** | Interface/classe base com operações comuns |
| **Leaf** | Objeto individual (sem filhos) |
| **Composite** | Grupo de objetos, pode conter Leaf e outros Composite |
| **Client** | Usa Component sem se preocupar com a complexidade |

### Case: Sistema de Arquivos

```java
// Component
interface ArquivoComponent { void exibir(); }

// Leaf
class Arquivo implements ArquivoComponent {
    private String nome;
    public Arquivo(String nome) { this.nome = nome; }
    public void exibir() { System.out.println("Arquivo: " + nome); }
}

// Composite
class Pasta implements ArquivoComponent {
    private String nome;
    private List<ArquivoComponent> conteudo = new ArrayList<>();

    public Pasta(String nome) { this.nome = nome; }
    public void adicionar(ArquivoComponent c) { conteudo.add(c); }
    public void remover(ArquivoComponent c)   { conteudo.remove(c); }

    public void exibir() {
        System.out.println("Pasta: " + nome);
        for (ArquivoComponent c : conteudo) c.exibir(); // recursivo!
    }
}

// Saída ao montar e exibir:
// Pasta: Meus Arquivos
//   Arquivo: documento.txt
//   Arquivo: foto.jpg
//   Pasta: Trabalho
//     Arquivo: planilha.xlsx
```

---

## 10. Facade (Fachada)

### Conceito
Fornece uma **interface simplificada para um sistema complexo** de classes, bibliotecas ou subsistemas.

Encapsula as interações complexas e expõe apenas os métodos necessários.

> 💡 Muito usado em **APIs/SDKs**, **acesso a banco de dados** e **integração com sistemas legados**.

### Vantagens
- ✔ Reduz a complexidade para o usuário
- ✔ Encapsula a lógica do sistema
- ✔ Facilita a manutenção
- ✔ Promove acoplamento mais fraco entre cliente e subsistema

### Diferença: Facade x Adapter

| Característica | Facade | Adapter |
|---------------|--------|---------|
| Propósito | Simplifica interação com sistema complexo | Faz interfaces incompatíveis funcionar juntas |
| Como funciona | Fornece API de alto nível | Converte uma interface para outra |
| Exemplo | HomeTheater simplifica o sistema | Adapter faz API antiga funcionar com código novo |

### Estrutura

| Elemento | Papel |
|----------|-------|
| **Subsystem Classes** | Classes que executam a lógica de negócio |
| **Facade** | Classe que fornece interface simplificada para o subsistema |
| **Client** | Interage através da Facade |

### Case: Home Theater

**Sem Facade:** cliente precisa chamar Tela, Som e Player separadamente.

**Com Facade:** uma única chamada `assistirFilme()` faz tudo.

```java
// Subsistema (3 classes internas)
class Tela   { void ligar(){}  void ajustarBrilho(){} }
class Som    { void ligar(){}  void ajustarVolume(){} }
class Player { void carregarFilme(String f){} void reproduzir(){} }

// Facade
class HomeTheaterFacade {
    private Tela tela     = new Tela();
    private Som som       = new Som();
    private Player player = new Player();

    public void assistirFilme(String filme) {
        tela.ligar();
        tela.ajustarBrilho();
        som.ligar();
        som.ajustarVolume();
        player.carregarFilme(filme);
        player.reproduzir();
        System.out.println("Aproveite o filme!");
    }
}

// Client — usa apenas a Facade!
new HomeTheaterFacade().assistirFilme("O Poderoso Chefão");
```

---

## 11. Flyweight (Peso-Mosca)

### Conceito
Reduz o **consumo de memória** ao compartilhar objetos entre múltiplas instâncias, evitando a criação redundante de dados imutáveis.

Útil quando há muitos objetos semelhantes que ocupam muita memória.

> 💡 Usado em: **renderização de textos/fontes**, **sprites em jogos** (árvores em mapa gigante), **sistemas de cache**.

### Vantagens
- ✔ Economiza memória ao compartilhar objetos imutáveis
- ✔ Aumenta eficiência em sistemas com milhares de objetos semelhantes
- ✔ Reduz sobrecarga de criação e destruição de objetos

### Estrutura

| Elemento | Papel |
|----------|-------|
| **Flyweight** | Interface com operações comuns |
| **ConcreteFlyweight** | Implementação compartilhada do objeto imutável |
| **FlyweightFactory** | Gerencia e reutiliza objetos Flyweight (evita duplicações) |
| **Client** | Usa o Flyweight, mas armazena estados específicos separadamente |

> O estado do objeto é dividido em:
> - **Estado Intrínseco** — compartilhado, imutável (armazenado no Flyweight)
> - **Estado Extrínseco** — único para cada instância (passado pelo Client)

---

---

# ⚙️ PADRÕES COMPORTAMENTAIS

> Cuidam da comunicação eficiente e da assinalação de responsabilidades entre objetos.

*(Os seguintes padrões foram listados no curso mas ainda não detalhados com exemplos de código)*

| Padrão | Propósito resumido |
|--------|-------------------|
| **Chain of Responsibility** | Encadeia objetos receptores para tratar requisições |
| **Command** | Encapsula ações em objetos (desfazer/refazer, filas) |
| **Iterator** | Percorre coleções sem expor a representação interna |
| **Mediator** | Reduz dependências entre objetos através de um mediador central |
| **Memento** | Salva e restaura estados anteriores de objetos |
| **Observer** | Define dependência um-para-muitos (notificações automáticas) |
| **State** | Permite que o objeto altere seu comportamento conforme muda de estado |
| **Strategy** | Define família de algoritmos intercambiáveis |
| **Template Method** | Define o esqueleto de um algoritmo, deixando etapas para subclasses |
| **Interpreter** | Define uma gramática para uma linguagem e interpreta sentenças |
| **Visitor** | Separa algoritmos dos objetos sobre os quais operam |

---

## Resumo Visual: Quando usar cada padrão

```
PROBLEMA                              → PADRÃO
----------------------------------------------
Criar objetos de um tipo variável     → Factory Method
Criar famílias de objetos compatíveis → Abstract Factory
Construir objeto complexo passo a passo → Builder
Clonar objetos existentes             → Prototype
Garantir instância única              → Singleton

Adaptar interface incompatível        → Adapter
Separar abstração de implementação    → Bridge
Adicionar funcionalidade dinamicamente → Decorator
Tratar hierarquias uniformemente      → Composite
Simplificar sistema complexo          → Facade
Economizar memória com objetos repetidos → Flyweight
Controlar acesso a um objeto          → Proxy
```

---

*Notas geradas a partir do material: "Padrões de Projeto em Java — GoF & GRASP" — Prof. Kesede R. Julio*
