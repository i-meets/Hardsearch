package hard.engenharia.model;

import java.sql.Date;

public class Ocorrencia {

    private int cod_bo;
    private int fr_codFer;
    private String fr_codFerramenta;
    private String fr_grupoFerramenta;
    private double valorUltCompra;
    private int fr_codMaq;
    private String fr_codMaquina;
    private int fr_codFun;
    private String fr_nomeFun;
    private String fr_turnoFun;
    private String motivoQuebra;
    private int fr_codItem;
    private String fr_codigoItem;
    private Date data;

    public int getCod_bo() {
        return cod_bo;
    }

    public void setCod_bo(int cod_bo) {
        this.cod_bo = cod_bo;
    }

    public int getFr_codFer() {
        return fr_codFer;
    }

    public void setFr_codFer(int fr_codFer) {
        this.fr_codFer = fr_codFer;
    }

    public String getFr_codFerramenta() {
        return fr_codFerramenta;
    }

    public void setFr_codFerramenta(String fr_codFerramenta) {
        this.fr_codFerramenta = fr_codFerramenta;
    }

    public String getFr_grupoFerramenta() {
        return fr_grupoFerramenta;
    }

    public void setFr_grupoFerramenta(String fr_grupoFerramenta) {
        this.fr_grupoFerramenta = fr_grupoFerramenta;
    }

    public double getValorUltCompra() {
        return valorUltCompra;
    }

    public void setValorUltCompra(double valorUltCompra) {
        this.valorUltCompra = valorUltCompra;
    }

    public int getFr_codMaq() {
        return fr_codMaq;
    }

    public void setFr_codMaq(int fr_codMaq) {
        this.fr_codMaq = fr_codMaq;
    }

    public String getFr_codMaquina() {
        return fr_codMaquina;
    }

    public void setFr_codMaquina(String fr_codMaquina) {
        this.fr_codMaquina = fr_codMaquina;
    }

    public int getFr_codFun() {
        return fr_codFun;
    }

    public void setFr_codFun(int fr_codFun) {
        this.fr_codFun = fr_codFun;
    }

    public String getFr_nomeFun() {
        return fr_nomeFun;
    }

    public void setFr_nomeFun(String fr_nomeFun) {
        this.fr_nomeFun = fr_nomeFun;
    }

    public String getFr_turnoFun() {
        return fr_turnoFun;
    }

    public void setFr_turnoFun(String fr_turnoFun) {
        this.fr_turnoFun = fr_turnoFun;
    }

    public String getMotivoQuebra() {
        return motivoQuebra;
    }

    public void setMotivoQuebra(String motivoQuebra) {
        this.motivoQuebra = motivoQuebra;
    }

    public int getFr_codItem() {
        return fr_codItem;
    }

    public void setFr_codItem(int fr_codItem) {
        this.fr_codItem = fr_codItem;
    }

    public String getFr_codigoItem() {
        return fr_codigoItem;
    }

    public void setFr_codigoItem(String fr_codigoItem) {
        this.fr_codigoItem = fr_codigoItem;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

}
