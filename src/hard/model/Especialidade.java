package hard.model;

public class Especialidade {

    private int cod;
    private String nome;

    public Especialidade() {

    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Especialidade{" + "cod=" + cod + ", nome=" + nome + '}';
    }

}
