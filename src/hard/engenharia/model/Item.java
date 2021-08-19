package hard.engenharia.model;

import java.sql.Date;

public class Item {

    private int cod;
    private String codItem;
    private int fr_codEmpresa;
    private String descricao;
    private int tipoContRev;
    private int revisaoInterna;
    private Date data_rev_interna;
    private String revisaoCliente;
    private Date data_rev_cliente;
    private String fr_usuario;
    private int status;
    private double valor;

    public void Item() {

    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getCodItem() {
        return codItem;
    }

    public void setCodItem(String codItem) {
        this.codItem = codItem;
    }

    public int getFr_codEmpresa() {
        return fr_codEmpresa;
    }

    public void setFr_codEmpresa(int fr_codEmpresa) {
        this.fr_codEmpresa = fr_codEmpresa;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getTipoContRev() {
        return tipoContRev;
    }

    public void setTipoContRev(int tipoContRev) {
        this.tipoContRev = tipoContRev;
    }

    public int getRevisaoInterna() {
        return revisaoInterna;
    }

    public void setRevisaoInterna(int revisaoInterna) {
        this.revisaoInterna = revisaoInterna;
    }

    public Date getData_rev_interna() {
        return data_rev_interna;
    }

    public void setData_rev_interna(Date data_rev_interna) {
        this.data_rev_interna = data_rev_interna;
    }

    public String getRevisaoCliente() {
        return revisaoCliente;
    }

    public void setRevisaoCliente(String revisaoCliente) {
        this.revisaoCliente = revisaoCliente;
    }

    public Date getData_rev_cliente() {
        return data_rev_cliente;
    }

    public void setData_rev_cliente(Date data_rev_cliente) {
        this.data_rev_cliente = data_rev_cliente;
    }

    public String getFr_usuario() {
        return fr_usuario;
    }

    public void setFr_usuario(String fr_usuario) {
        this.fr_usuario = fr_usuario;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

}
