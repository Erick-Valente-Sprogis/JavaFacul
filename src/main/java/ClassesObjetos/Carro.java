package ClassesObjetos;

public class Carro {
    public String marca;
    public String modelo;
    public int ano;

    // Método para acelerar o carro
    public void acelerar() {
        System.out.println("O carro está acelerando.");
    }

    // Método para frear o carro
    public void frear() {
        System.out.println("O carro está freando.");
    }

    // Método para mostrar as informações do carro
    public void mostrarInformacoes() {
        System.out.println("Marca: " + this.marca);
        System.out.println("Modelo: " + this.modelo);
        System.out.println("Ano: " + this.ano);
    }
}

