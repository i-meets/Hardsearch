
package hard.model;

import java.sql.Date;

public class ContSaida {

    private int codSaida;
    private int fr_codFuncionario;
    private String fr_nomeFuncionario;
    private Date dataSaidaFuncio;
    private String horaSaidaFuncio;
    private int retornaTraba;
    private int motivoSaida;
    private String espLocal;
    private int statusSaida;

    public ContSaida() {
    }

    public int getCodSaida() {
        return codSaida;
    }

    public void setCodSaida(int codSaida) {
        this.codSaida = codSaida;
    }

    public String getFr_nomeFuncionario() {
        return fr_nomeFuncionario;
    }

    public void setFr_nomeFuncionario(String fr_nomeFuncionario) {
        this.fr_nomeFuncionario = fr_nomeFuncionario;
    }

    public int getFr_codFuncionario() {
        return fr_codFuncionario;
    }

    public void setFr_codFuncionario(int fr_codFuncionario) {
        this.fr_codFuncionario = fr_codFuncionario;
    }

    public Date getDataSaidaFuncio() {
        return dataSaidaFuncio;
    }

    public void setDataSaidaFuncio(Date dataSaidaFuncio) {
        this.dataSaidaFuncio = dataSaidaFuncio;
    }

    public String getHoraSaidaFuncio() {
        return horaSaidaFuncio;
    }

    public void setHoraSaidaFuncio(String horaSaidaFuncio) {
        this.horaSaidaFuncio = horaSaidaFuncio;
    }

    public int getRetornaTraba() {
        return retornaTraba;
    }

    public void setRetornaTraba(int retornaTraba) {
        this.retornaTraba = retornaTraba;
    }

    public int getMotivoSaida() {
        return motivoSaida;
    }

    public void setMotivoSaida(int motivoSaida) {
        this.motivoSaida = motivoSaida;
    }

    public String getEspLocal() {
        return espLocal;
    }

    public void setEspLocal(String espLocal) {
        this.espLocal = espLocal;
    }

    public int getStatusSaida() {
        return statusSaida;
    }

    public void setStatusSaida(int statusSaida) {
        this.statusSaida = statusSaida;
    }

}
