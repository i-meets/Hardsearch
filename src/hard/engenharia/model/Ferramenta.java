package hard.engenharia.model;

public class Ferramenta {

    private int cod_fer;
    private String codFerramenta;
    private String grupo;
    private double valor_compra;
    private int numeroOc;

    public int getCod_fer() {
        return cod_fer;
    }

    public void setCod_fer(int cod_fer) {
        this.cod_fer = cod_fer;
    }

    public String getCodFerramenta() {
        return codFerramenta;
    }

    public void setCodFerramenta(String codFerramenta) {
        this.codFerramenta = codFerramenta;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public double getValor_compra() {
        return valor_compra;
    }

    public void setValor_compra(double valor_compra) {
        this.valor_compra = valor_compra;
    }

    public int getNumeroOc() {
        return numeroOc;
    }

    public void setNumeroOc(int numeroOc) {
        this.numeroOc = numeroOc;
    }

}
