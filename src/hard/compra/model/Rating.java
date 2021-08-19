package hard.compra.model;

import java.sql.Date;

public class Rating {

    private int cod;
    private int fk_codEmp;
    private String fk_nomeEmp;
    private String fk_mailEmp;
    private String condPgmt;
    private String condEntrega;
    private String qualPreco;
    private String qualidade;
    private String avaliaTotal;
    private Date dataRating;
    private int statusAvalia;

    public void Rating() {

    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public int getFk_codEmp() {
        return fk_codEmp;
    }

    public void setFk_codEmp(int fk_codEmp) {
        this.fk_codEmp = fk_codEmp;
    }

    public String getFk_nomeEmp() {
        return fk_nomeEmp;
    }

    public void setFk_nomeEmp(String fk_nomeEmp) {
        this.fk_nomeEmp = fk_nomeEmp;
    }

    public String getFk_mailEmp() {
        return fk_mailEmp;
    }

    public void setFk_mailEmp(String fk_mailEmp) {
        this.fk_mailEmp = fk_mailEmp;
    }

    public String getCondPgmt() {
        return condPgmt;
    }

    public void setCondPgmt(String condPgmt) {
        this.condPgmt = condPgmt;
    }

    public String getCondEntrega() {
        return condEntrega;
    }

    public void setCondEntrega(String condEntrega) {
        this.condEntrega = condEntrega;
    }

    public String getQualPreco() {
        return qualPreco;
    }

    public void setQualPreco(String qualPreco) {
        this.qualPreco = qualPreco;
    }

    public String getQualidade() {
        return qualidade;
    }

    public void setQualidade(String qualidade) {
        this.qualidade = qualidade;
    }

    public String getAvaliaTotal() {
        return avaliaTotal;
    }

    public void setAvaliaTotal(String avaliaTotal) {
        this.avaliaTotal = avaliaTotal;
    }

    public Date getDataRating() {
        return dataRating;
    }

    public void setDataRating(Date dataRating) {
        this.dataRating = dataRating;
    }

    public int getStatusAvalia() {
        return statusAvalia;
    }

    public void setStatusAvalia(int statusAvalia) {
        this.statusAvalia = statusAvalia;
    }

}
