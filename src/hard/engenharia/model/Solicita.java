package hard.engenharia.model;

import java.sql.Date;

public class Solicita {

    private int cod;
    private int fr_codAlteracao;
    private String titulo;
    private String descricao;
    private String usuarioCriador;
    private Date dataCriacao;
    private String usuarioRetorno;
    private Date dataRetorno;
    private String fr_codItem;
    private int status;

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public int getFr_codAlteracao() {
        return fr_codAlteracao;
    }

    public void setFr_codAlteracao(int fr_codAlteracao) {
        this.fr_codAlteracao = fr_codAlteracao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUsuarioCriador() {
        return usuarioCriador;
    }

    public void setUsuarioCriador(String usuarioCriador) {
        this.usuarioCriador = usuarioCriador;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getUsuarioRetorno() {
        return usuarioRetorno;
    }

    public void setUsuarioRetorno(String usuarioRetorno) {
        this.usuarioRetorno = usuarioRetorno;
    }

    public Date getDataRetorno() {
        return dataRetorno;
    }

    public void setDataRetorno(Date dataRetorno) {
        this.dataRetorno = dataRetorno;
    }

    public String getFr_codItem() {
        return fr_codItem;
    }

    public void setFr_codItem(String fr_codItem) {
        this.fr_codItem = fr_codItem;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
