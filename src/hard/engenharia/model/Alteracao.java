package hard.engenharia.model;

import java.sql.Date;

public class Alteracao {

    private int cod;
    private int fr_item_cod;
    private String fr_codItem;
    private String fr_descricaoItem;
    private int fr_codEmpresa;
    private String fr_nomeEmpresa;
    private int revInterna_a;
    private String revCliente_a;
    private int tipoRevisao;
    private int possuiExtencao;
    private Date dataRevInterna_a;
    private Date dataRevCliente_a;
    private String responsavel;
    private int status;

    public void Alteracao() {

    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public int getFr_item_cod() {
        return fr_item_cod;
    }

    public void setFr_item_cod(int fr_item_cod) {
        this.fr_item_cod = fr_item_cod;
    }

    public String getFr_codItem() {
        return fr_codItem;
    }

    public void setFr_codItem(String fr_codItem) {
        this.fr_codItem = fr_codItem;
    }

    public String getFr_descricaoItem() {
        return fr_descricaoItem;
    }

    public void setFr_descricaoItem(String fr_descricaoItem) {
        this.fr_descricaoItem = fr_descricaoItem;
    }

    public int getFr_codEmpresa() {
        return fr_codEmpresa;
    }

    public void setFr_codEmpresa(int fr_codEmpresa) {
        this.fr_codEmpresa = fr_codEmpresa;
    }

    public String getFr_nomeEmpresa() {
        return fr_nomeEmpresa;
    }

    public void setFr_nomeEmpresa(String fr_nomeEmpresa) {
        this.fr_nomeEmpresa = fr_nomeEmpresa;
    }

    public int getRevInterna_a() {
        return revInterna_a;
    }

    public void setRevInterna_a(int revInterna_a) {
        this.revInterna_a = revInterna_a;
    }

    public String getRevCliente_a() {
        return revCliente_a;
    }

    public void setRevCliente_a(String revCliente_a) {
        this.revCliente_a = revCliente_a;
    }

    public int getTipoRevisao() {
        return tipoRevisao;
    }

    public void setTipoRevisao(int tipoRevisao) {
        this.tipoRevisao = tipoRevisao;
    }

    public int getPossuiExtencao() {
        return possuiExtencao;
    }

    public void setPossuiExtencao(int possuiExtencao) {
        this.possuiExtencao = possuiExtencao;
    }

    public Date getDataRevInterna_a() {
        return dataRevInterna_a;
    }

    public void setDataRevInterna_a(Date dataRevInterna_a) {
        this.dataRevInterna_a = dataRevInterna_a;
    }

    public Date getDataRevCliente_a() {
        return dataRevCliente_a;
    }

    public void setDataRevCliente_a(Date dataRevCliente_a) {
        this.dataRevCliente_a = dataRevCliente_a;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
