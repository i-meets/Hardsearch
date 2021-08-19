package hard.rh.model;

public class Dependente {

    private int codDependente;
    private String nomeDependente;
    private String tipoDependente;
    private int fk_codFuncionario;
    private String cpfDependente;
    private int statusDependente;

    public void dependente() {

    }

    public int getCodDependente() {
        return codDependente;
    }

    public void setCodDependente(int codDependente) {
        this.codDependente = codDependente;
    }

    public String getNomeDependente() {
        return nomeDependente;
    }

    public void setNomeDependente(String nomeDependente) {
        this.nomeDependente = nomeDependente;
    }

    public String getTipoDependente() {
        return tipoDependente;
    }

    public void setTipoDependente(String tipoDependente) {
        this.tipoDependente = tipoDependente;
    }

    public int getFk_codFuncionario() {
        return fk_codFuncionario;
    }

    public void setFk_codFuncionario(int fk_codFuncionario) {
        this.fk_codFuncionario = fk_codFuncionario;
    }

    public String getCpfDependente() {
        return cpfDependente;
    }

    public void setCpfDependente(String cpfDependente) {
        this.cpfDependente = cpfDependente;
    }

    public int getStatusDependente() {
        return statusDependente;
    }

    public void setStatusDependente(int statusDependente) {
        this.statusDependente = statusDependente;
    }

    @Override
    public String toString() {
        return getNomeDependente(); //serve para listar o nome do dependente na tela copart
    }

}
