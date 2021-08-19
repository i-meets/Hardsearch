package hard.rh.model;

import java.sql.Date;

public class TesteCovid {

    private int codTeste;
    private int fr_codFuncionario;
    private String fr_nomeFuncionario;
    private Date dataTeste;
    private int resultadoTeste;

    public TesteCovid() {

    }

    public int getCodTeste() {
        return codTeste;
    }

    public void setCodTeste(int codTeste) {
        this.codTeste = codTeste;
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

    public Date getDataTeste() {
        return dataTeste;
    }

    public void setDataTeste(Date dataTeste) {
        this.dataTeste = dataTeste;
    }

    public int getResultadoTeste() {
        return resultadoTeste;
    }

    public void setResultadoTeste(int resultadoTeste) {
        this.resultadoTeste = resultadoTeste;
    }

}
