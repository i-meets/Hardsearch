package hard.model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Date;

public class ContAcesso {

    private int codAcesso;
    private String nomeEmp;
    private String veiculo;
    private String placa;
    private String nomePessoa;
    private String rg;
    private String observa;
    private String horaEntra;
    private String horaSai;
    private int statusAcesso;
    private Date dataAcesso;


    public ContAcesso() {
    }

    public int getCodAcesso() {
        return codAcesso;
    }

    public void setCodAcesso(int codAcesso) {
        this.codAcesso = codAcesso;
    }

    public String getNomeEmp() {
        return nomeEmp;
    }

    public void setNomeEmp(String nomeEmp) {
        this.nomeEmp = nomeEmp;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getObserva() {
        return observa;
    }

    public void setObserva(String observa) {
        this.observa = observa;
    }

    public String getHoraEntra() {
        return horaEntra;
    }

    public void setHoraEntra(String horaEntra) {
        this.horaEntra = horaEntra;
    }

    public String getHoraSai() {
        return horaSai;
    }

    public void setHoraSai(String horaSai) {
        this.horaSai = horaSai;
    }

    public int getStatusAcesso() {
        return statusAcesso;
    }

    public void setStatusAcesso(int statusAcesso) {
        this.statusAcesso = statusAcesso;
    }

    public Date getDataAcesso() {
        return dataAcesso;
    }

    public void setDataAcesso(Date dataAcesso) {
        this.dataAcesso = dataAcesso;
    }

}
