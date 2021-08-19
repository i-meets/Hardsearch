package hard.rh.model;

import java.sql.Date;

public class Folha {

    private int codRelFolha;
    private int codFolha;
    private int fr_codFuncionario;
    private int fr_nrFuncionario;
    private String fr_nomeFuncionario;
    private String fr_cpfFuncionario;
    private String fr_cargoFuncionario;
    private String fr_setorFuncionario;
    private String fr_tipoSalarioFuncionario;
    private int fr_codEvento;
    private String fr_descriEvento;
    private String fr_tipoEvento;
    private String fr_tipoProcessaEvento;
    private Double conteudoFolha;
    private Date dataFolha;
    private int statusFolha;
    private int fr_codParcela;
    private int mesFolha;
    private int numFichas;

    public Folha() {

    }

    public int getCodRelFolha() {
        return codRelFolha;
    }

    public int getFr_nrFuncionario() {
        return fr_nrFuncionario;
    }

    public void setFr_nrFuncionario(int fr_nrFuncionario) {
        this.fr_nrFuncionario = fr_nrFuncionario;
    }

    public void setCodRelFolha(int codRelFolha) {
        this.codRelFolha = codRelFolha;
    }

    public int getCodFolha() {
        return codFolha;
    }

    public void setCodFolha(int codFolha) {
        this.codFolha = codFolha;
    }

    public int getFr_codFuncionario() {
        return fr_codFuncionario;
    }

    public void setFr_codFuncionario(int fr_codFuncionario) {
        this.fr_codFuncionario = fr_codFuncionario;
    }

    public String getFr_nomeFuncionario() {
        return fr_nomeFuncionario;
    }

    public void setFr_nomeFuncionario(String fr_nomeFuncionario) {
        this.fr_nomeFuncionario = fr_nomeFuncionario;
    }

    public String getFr_cpfFuncionario() {
        return fr_cpfFuncionario;
    }

    public void setFr_cpfFuncionario(String fr_cpfFuncionario) {
        this.fr_cpfFuncionario = fr_cpfFuncionario;
    }

    public String getFr_cargoFuncionario() {
        return fr_cargoFuncionario;
    }

    public void setFr_cargoFuncionario(String fr_cargoFuncionario) {
        this.fr_cargoFuncionario = fr_cargoFuncionario;
    }

    public String getFr_setorFuncionario() {
        return fr_setorFuncionario;
    }

    public void setFr_setorFuncionario(String fr_setorFuncionario) {
        this.fr_setorFuncionario = fr_setorFuncionario;
    }

    public int getFr_codEvento() {
        return fr_codEvento;
    }

    public void setFr_codEvento(int fr_codEvento) {
        this.fr_codEvento = fr_codEvento;
    }

    public String getFr_descriEvento() {
        return fr_descriEvento;
    }

    public void setFr_descriEvento(String fr_descriEvento) {
        this.fr_descriEvento = fr_descriEvento;
    }

    public String getFr_tipoEvento() {
        return fr_tipoEvento;
    }

    public void setFr_tipoEvento(String fr_tipoEvento) {
        this.fr_tipoEvento = fr_tipoEvento;
    }

    public String getFr_tipoProcessaEvento() {
        return fr_tipoProcessaEvento;
    }

    public void setFr_tipoProcessaEvento(String fr_tipoProcessaEvento) {
        this.fr_tipoProcessaEvento = fr_tipoProcessaEvento;
    }

    public Date getDataFolha() {
        return dataFolha;
    }

    public void setDataFolha(Date dataFolha) {
        this.dataFolha = dataFolha;
    }

    public int getStatusFolha() {
        return statusFolha;
    }

    public void setStatusFolha(int statusFolha) {
        this.statusFolha = statusFolha;
    }

    public String getFr_tipoSalarioFuncionario() {
        return fr_tipoSalarioFuncionario;
    }

    public void setFr_tipoSalarioFuncionario(String fr_tipoSalarioFuncionario) {
        this.fr_tipoSalarioFuncionario = fr_tipoSalarioFuncionario;
    }

    public Double getConteudoFolha() {
        return conteudoFolha;
    }

    public void setConteudoFolha(Double conteudoFolha) {
        this.conteudoFolha = conteudoFolha;
    }

    public int getFr_codParcela() {
        return fr_codParcela;
    }

    public void setFr_codParcela(int fr_codParcela) {
        this.fr_codParcela = fr_codParcela;
    }

    public int getMesFolha() {
        return mesFolha;
    }

    public void setMesFolha(int mesFolha) {
        this.mesFolha = mesFolha;
    }

    public int getNumFichas() {
        return numFichas;
    }

    public void setNumFichas(int numFichas) {
        this.numFichas = numFichas;
    }

}
