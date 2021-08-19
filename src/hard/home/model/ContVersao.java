package hard.home.model;

import java.sql.Date;

public class ContVersao {

    private String codTela;
    private int idVersao;
    private String fk_codTela;
    private String novaVersao;
    private Date dataLibVersao;
    private String versaoAtualTela;
    private Date dataVersao;
    private String observaVersao;

    public ContVersao() {
    }

    public String getCodTela() {
        return codTela;
    }

    public void setCodTela(String codTela) {
        this.codTela = codTela;
    }

    public int getIdVersao() {
        return idVersao;
    }

    public void setIdVersao(int idVersao) {
        this.idVersao = idVersao;
    }

    public String getFk_codTela() {
        return fk_codTela;
    }

    public void setFk_codTela(String fk_codTela) {
        this.fk_codTela = fk_codTela;
    }

    public String getNovaVersao() {
        return novaVersao;
    }

    public void setNovaVersao(String novaVersao) {
        this.novaVersao = novaVersao;
    }

    public Date getDataLibVersao() {
        return dataLibVersao;
    }

    public void setDataLibVersao(Date dataLibVersao) {
        this.dataLibVersao = dataLibVersao;
    }

    public String getVersaoAtualTela() {
        return versaoAtualTela;
    }

    public void setVersaoAtualTela(String versaoAtualTela) {
        this.versaoAtualTela = versaoAtualTela;
    }

    public Date getDataVersao() {
        return dataVersao;
    }

    public void setDataVersao(Date dataVersao) {
        this.dataVersao = dataVersao;
    }

    public String getObservaVersao() {
        return observaVersao;
    }

    public void setObservaVersao(String observaVersao) {
        this.observaVersao = observaVersao;
    }

    @Override
    public String toString() {
        return "ContVersao{" + "codTela=" + codTela + ", idVersao=" + idVersao + ", fk_codTela=" + fk_codTela + ", novaVersao=" + novaVersao + ", dataLibVersao=" + dataLibVersao + ", versaoAtualTela=" + versaoAtualTela + ", dataVersao=" + dataVersao + ", observaVersao=" + observaVersao + '}';
    }

}
