package hard.rh.model;

import java.sql.Date;
import java.text.NumberFormat;
import java.util.Locale;

public class Parcela {

    NumberFormat formatter = NumberFormat.getInstance(new Locale("pt", "BR"));

    private int codParcela;
    private int fr_codCop;
    private int fr_codFuncionario;
    private int fr_codBeneficiario;
    private String fr_nomeFuncionario;
    private String fr_nomeBeneficiario;
    private int fr_codPro;
    private double valorParcela;
    private double valorTotalPar;
    private int numeroDeParcela;
    private Date dataVencPar;
    private int statusParcela;
    private String statusParcelaAtual;
    private int numMes;
    private double valorTotal;
    private int numMesParcela;
    private int diasAtrasoPar;

    public Parcela() {

    }

    public int getCodParcela() {
        return codParcela;
    }

    public void setCodParcela(int codParcela) {
        this.codParcela = codParcela;
    }

    public int getFr_codCop() {
        return fr_codCop;
    }

    public void setFr_codCop(int fr_codCop) {
        this.fr_codCop = fr_codCop;
    }

    public int getFr_codFuncionario() {
        return fr_codFuncionario;
    }

    public void setFr_codFuncionario(int fr_codFuncionario) {
        this.fr_codFuncionario = fr_codFuncionario;
    }

    public int getFr_codBeneficiario() {
        return fr_codBeneficiario;
    }

    public String getFr_nomeFuncionario() {
        return fr_nomeFuncionario;
    }

    public void setFr_nomeFuncionario(String fr_nomeFuncionario) {
        this.fr_nomeFuncionario = fr_nomeFuncionario;
    }

    public void setFr_codBeneficiario(int fr_codBeneficiario) {
        this.fr_codBeneficiario = fr_codBeneficiario;
    }

    public String getFr_nomeBeneficiario() {
        return fr_nomeBeneficiario;
    }

    public void setFr_nomeBeneficiario(String fr_nomeBeneficiario) {
        this.fr_nomeBeneficiario = fr_nomeBeneficiario;
    }

    public int getFr_codPro() {
        return fr_codPro;
    }

    public void setFr_codPro(int fr_codPro) {
        this.fr_codPro = fr_codPro;
    }

    public double getValorParcela() {
        return valorParcela;
    }

    public void setValorParcela(double valorParcela) {
        this.valorParcela = valorParcela;
    }

    public double getValorTotalPar() {
        return valorTotalPar;
    }

    public void setValorTotalPar(double valorTotalPar) {
        this.valorTotalPar = valorTotalPar;
    }

    public int getNumeroDeParcela() {
        return numeroDeParcela;
    }

    public void setNumeroDeParcela(int numeroDeParcela) {
        this.numeroDeParcela = numeroDeParcela;
    }

    public Date getDataVencPar() {
        return dataVencPar;
    }

    public void setDataVencPar(Date dataVencPar) {
        this.dataVencPar = dataVencPar;
    }

    public int getStatusParcela() {
        return statusParcela;
    }

    public void setStatusParcela(int statusParcela) {
        this.statusParcela = statusParcela;
    }

    public String getStatusParcelaAtual() {
        return statusParcelaAtual;
    }

    public void setStatusParcelaAtual(String statusParcelaAtual) {
        this.statusParcelaAtual = statusParcelaAtual;
    }

    public int getNumMes() {
        return numMes;
    }

    public void setNumMes(int numMes) {
        this.numMes = numMes;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public int getNumMesParcela() {
        return numMesParcela;
    }

    public void setNumMesParcela(int numMesParcela) {
        this.numMesParcela = numMesParcela;
    }

    public int getDiasAtrasoPar() {
        return diasAtrasoPar;
    }

    public void setDiasAtrasoPar(int diasAtrasoPar) {
        this.diasAtrasoPar = diasAtrasoPar;
    }

}
