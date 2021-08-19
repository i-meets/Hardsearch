package hard.model;

public class Procedimento {

    private int codPro;
    private String nomePro;
    private double valorPro;
    private int parcelaPro;

    public Procedimento() {

    }

    public int getCodPro() {
        return codPro;
    }

    public void setCodPro(int codPro) {
        this.codPro = codPro;
    }

    public String getNomePro() {
        return nomePro;
    }

    public void setNomePro(String nomePro) {
        this.nomePro = nomePro;
    }

    public double getValorPro() {
        return valorPro;
    }

    public void setValorPro(double valorPro) {
        this.valorPro = valorPro;
    }

    public int getParcelaPro() {
        return parcelaPro;
    }

    public void setParcelaPro(int parcelaPro) {
        this.parcelaPro = parcelaPro;
    }

    @Override
    public String toString() {
        return "Procedimento{" + "codPro=" + codPro + ", nomePro=" + nomePro + ", valorPro=" + valorPro + ", parcelaPro=" + parcelaPro + '}';
    }

}
