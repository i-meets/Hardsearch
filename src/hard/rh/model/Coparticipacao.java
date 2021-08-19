package hard.rh.model;

import java.sql.Date;

public class Coparticipacao {

    private int cod;
    private int codCop;
    private int fr_codFuncionario;
    private int fr_codBeneficiario;
    private String fr_nomeFuncionario;
    private String fr_nomeBeneficiario;
    private String fr_cpfFuncionario;
    private String fr_cargoFuncionario;
    private String fr_setorFuncionario;
    private int fr_codPro;
    private String fr_nomePro;
    private double fr_valorPro;
    private double valorCop;
    private int fr_parcelaPro;
    private Double fr_valorPar;
    private Date fr_dataVencPar;
    private String localPro;
    private String medicoPro;
    private Date dataPro;
    private Date dataVencPro;
    private int geradoPar;
    private int nfe;
    private Date dataImporta;
    private Date dataVenciNfe;
    private int numeroTotalCop;

    public Coparticipacao() {

    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public int getCodCop() {
        return codCop;
    }

    public void setCodCop(int codCop) {
        this.codCop = codCop;
    }

    public int getFr_codFuncionario() {
        return fr_codFuncionario;
    }

    public void setFr_codFuncionario(int fr_codFuncionario) {
        this.fr_codFuncionario = fr_codFuncionario;
    }

    public int getFr_codBeneficiario() {
        return fr_codBeneficiario;
    }

    public void setFr_codBeneficiario(int fr_codBeneficiario) {
        this.fr_codBeneficiario = fr_codBeneficiario;
    }

    public String getFr_nomeFuncionario() {
        return fr_nomeFuncionario;
    }

    public void setFr_nomeFuncionario(String fr_nomeFuncionario) {
        this.fr_nomeFuncionario = fr_nomeFuncionario;
    }

    public String getFr_nomeBeneficiario() {
        return fr_nomeBeneficiario;
    }

    public void setFr_nomeBeneficiario(String fr_nomeBeneficiario) {
        this.fr_nomeBeneficiario = fr_nomeBeneficiario;
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

    public int getFr_codPro() {
        return fr_codPro;
    }

    public void setFr_codPro(int fr_codPro) {
        this.fr_codPro = fr_codPro;
    }

    public String getFr_nomePro() {
        return fr_nomePro;
    }

    public void setFr_nomePro(String fr_nomePro) {
        this.fr_nomePro = fr_nomePro;
    }

    public double getFr_valorPro() {
        return fr_valorPro;
    }

    public void setFr_valorPro(double fr_valorPro) {
        this.fr_valorPro = fr_valorPro;
    }

    public double getValorCop() {
        return valorCop;
    }

    public void setValorCop(double valorCop) {
        this.valorCop = valorCop;
    }

    public int getFr_parcelaPro() {
        return fr_parcelaPro;
    }

    public void setFr_parcelaPro(int fr_parcelaPro) {
        this.fr_parcelaPro = fr_parcelaPro;
    }

    public Double getFr_valorPar() {
        return fr_valorPar;
    }

    public void setFr_valorPar(Double fr_valorPar) {
        this.fr_valorPar = fr_valorPar;
    }

    public Date getFr_dataVencPar() {
        return fr_dataVencPar;
    }

    public void setFr_dataVencPar(Date fr_dataVencPar) {
        this.fr_dataVencPar = fr_dataVencPar;
    }

    public String getLocalPro() {
        return localPro;
    }

    public void setLocalPro(String localPro) {
        this.localPro = localPro;
    }

    public String getMedicoPro() {
        return medicoPro;
    }

    public void setMedicoPro(String medicoPro) {
        this.medicoPro = medicoPro;
    }

    public Date getDataPro() {
        return dataPro;
    }

    public void setDataPro(Date dataPro) {
        this.dataPro = dataPro;
    }

    public Date getDataVencPro() {
        return dataVencPro;
    }

    public void setDataVencPro(Date dataVencPro) {
        this.dataVencPro = dataVencPro;
    }

    public int getGeradoPar() {
        return geradoPar;
    }

    public void setGeradoPar(int geradoPar) {
        this.geradoPar = geradoPar;
    }

    public int getNfe() {
        return nfe;
    }

    public void setNfe(int nfe) {
        this.nfe = nfe;
    }

    public Date getDataImporta() {
        return dataImporta;
    }

    public void setDataImporta(Date dataImporta) {
        this.dataImporta = dataImporta;
    }

    public Date getDataVenciNfe() {
        return dataVenciNfe;
    }

    public void setDataVenciNfe(Date dataVenciNfe) {
        this.dataVenciNfe = dataVenciNfe;
    }

    public int getNumeroTotalCop() {
        return numeroTotalCop;
    }

    public void setNumeroTotalCop(int numeroTotalCop) {
        this.numeroTotalCop = numeroTotalCop;
    }

}
