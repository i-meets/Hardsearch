package hard.model;

public class Usuario {

    private int codUsuario;
    private String nomeUsuario;
    private String setorUsuario;
    private String cargoUsuario;
    private String loginUsuario;
    private String senhaUsuario;
    private int tipoUsuario;
    private int plataformaUsuario;

    public Usuario() {

    }

    public int getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(int codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSetorUsuario() {
        return setorUsuario;
    }

    public void setSetorUsuario(String setorUsuario) {
        this.setorUsuario = setorUsuario;
    }

    public String getCargoUsuario() {
        return cargoUsuario;
    }

    public void setCargoUsuario(String cargoUsuario) {
        this.cargoUsuario = cargoUsuario;
    }

    public String getLoginUsuario() {
        return loginUsuario;
    }

    public void setLoginUsuario(String loginUsuario) {
        this.loginUsuario = loginUsuario;
    }

    public String getSenhaUsuario() {
        return senhaUsuario;
    }

    public void setSenhaUsuario(String senhaUsuario) {
        this.senhaUsuario = senhaUsuario;
    }

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public int getPlataformaUsuario() {
        return plataformaUsuario;
    }

    public void setPlataformaUsuario(int plataformaUsuario) {
        this.plataformaUsuario = plataformaUsuario;
    }

}
