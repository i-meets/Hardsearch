package hard.compra.model;

import java.sql.Date;

public class DivergeNFE {

    private int cod_nfe;
    private int num_nfe;
    private int fr_codDv;
    private String nomeForne;
    private String link_nfe;
    private String fr_tipoDiverge;
    private String fr_descricao;
    private Date dataEmiNfe;
    private Date dataIncluNfe;
    private Date fr_dataDv;
    private Date fr_dataAjuste;
    private String fr_hora;
    private int status_nfe;
    private int status_dv;
    private String obs_compra;

    public int getCod_nfe() {
        return cod_nfe;
    }

    public void setCod_nfe(int cod_nfe) {
        this.cod_nfe = cod_nfe;
    }

    public int getNum_nfe() {
        return num_nfe;
    }

    public void setNum_nfe(int num_nfe) {
        this.num_nfe = num_nfe;
    }

    public int getFr_codDv() {
        return fr_codDv;
    }

    public void setFr_codDv(int fr_codDv) {
        this.fr_codDv = fr_codDv;
    }

    public String getNomeForne() {
        return nomeForne;
    }

    public void setNomeForne(String nomeForne) {
        this.nomeForne = nomeForne;
    }

    public String getLink_nfe() {
        return link_nfe;
    }

    public void setLink_nfe(String link_nfe) {
        this.link_nfe = link_nfe;
    }

    public String getFr_tipoDiverge() {
        return fr_tipoDiverge;
    }

    public void setFr_tipoDiverge(String fr_tipoDiverge) {
        this.fr_tipoDiverge = fr_tipoDiverge;
    }

    public String getFr_descricao() {
        return fr_descricao;
    }

    public void setFr_descricao(String fr_descricao) {
        this.fr_descricao = fr_descricao;
    }

    public Date getDataEmiNfe() {
        return dataEmiNfe;
    }

    public void setDataEmiNfe(Date dataEmiNfe) {
        this.dataEmiNfe = dataEmiNfe;
    }

    public Date getDataIncluNfe() {
        return dataIncluNfe;
    }

    public void setDataIncluNfe(Date dataIncluNfe) {
        this.dataIncluNfe = dataIncluNfe;
    }

    public Date getFr_dataDv() {
        return fr_dataDv;
    }

    public void setFr_dataDv(Date fr_dataDv) {
        this.fr_dataDv = fr_dataDv;
    }

    public Date getFr_dataAjuste() {
        return fr_dataAjuste;
    }

    public void setFr_dataAjuste(Date fr_dataAjuste) {
        this.fr_dataAjuste = fr_dataAjuste;
    }

    public String getFr_hora() {
        return fr_hora;
    }

    public void setFr_hora(String fr_hora) {
        this.fr_hora = fr_hora;
    }

    public int getStatus_nfe() {
        return status_nfe;
    }

    public void setStatus_nfe(int status_nfe) {
        this.status_nfe = status_nfe;
    }

    public int getStatus_dv() {
        return status_dv;
    }

    public void setStatus_dv(int status_dv) {
        this.status_dv = status_dv;
    }

    public String getObs_compra() {
        return obs_compra;
    }

    public void setObs_compra(String obs_compra) {
        this.obs_compra = obs_compra;
    }

}
