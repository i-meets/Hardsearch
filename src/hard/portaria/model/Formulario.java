package hard.portaria.model;

import java.sql.Date;

public class Formulario {

    private int codFormulario;
    private int fr_codFuncionario;
    private String fr_nomeFuncionario;
    private String fr_setorFuncionario;
    private String telefone;
    private int sintomas;
    private int contato;
    private int febre;
    private int tosse;
    private int d_respirar;
    private int calafrios;
    private int dor_muscular;
    private int dor_garganta;
    private int p_olfato;
    private int nausea;
    private int dor_cabeca;
    private Date data_sintoma;
    private int metros_2;
    private Date data_p;
    private int status;
    private double temp;

    public int getCodFormulario() {
        return codFormulario;
    }

    public void setCodFormulario(int codFormulario) {
        this.codFormulario = codFormulario;
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

    public String getFr_setorFuncionario() {
        return fr_setorFuncionario;
    }

    public void setFr_setorFuncionario(String fr_setorFuncionario) {
        this.fr_setorFuncionario = fr_setorFuncionario;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getSintomas() {
        return sintomas;
    }

    public void setSintomas(int sintomas) {
        this.sintomas = sintomas;
    }

    public int getContato() {
        return contato;
    }

    public void setContato(int contato) {
        this.contato = contato;
    }

    public int getFebre() {
        return febre;
    }

    public void setFebre(int febre) {
        this.febre = febre;
    }

    public int getTosse() {
        return tosse;
    }

    public void setTosse(int tosse) {
        this.tosse = tosse;
    }

    public int getD_respirar() {
        return d_respirar;
    }

    public void setD_respirar(int d_respirar) {
        this.d_respirar = d_respirar;
    }

    public int getCalafrios() {
        return calafrios;
    }

    public void setCalafrios(int calafrios) {
        this.calafrios = calafrios;
    }

    public int getDor_muscular() {
        return dor_muscular;
    }

    public void setDor_muscular(int dor_muscular) {
        this.dor_muscular = dor_muscular;
    }

    public int getDor_garganta() {
        return dor_garganta;
    }

    public void setDor_garganta(int dor_garganta) {
        this.dor_garganta = dor_garganta;
    }

    public int getP_olfato() {
        return p_olfato;
    }

    public void setP_olfato(int p_olfato) {
        this.p_olfato = p_olfato;
    }

    public int getNausea() {
        return nausea;
    }

    public void setNausea(int nausea) {
        this.nausea = nausea;
    }

    public int getDor_cabeca() {
        return dor_cabeca;
    }

    public void setDor_cabeca(int dor_cabeca) {
        this.dor_cabeca = dor_cabeca;
    }

    public Date getData_sintoma() {
        return data_sintoma;
    }

    public void setData_sintoma(Date data_sintoma) {
        this.data_sintoma = data_sintoma;
    }

    public int getMetros_2() {
        return metros_2;
    }

    public void setMetros_2(int metros_2) {
        this.metros_2 = metros_2;
    }

    public Date getData_p() {
        return data_p;
    }

    public void setData_p(Date data_p) {
        this.data_p = data_p;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

}
