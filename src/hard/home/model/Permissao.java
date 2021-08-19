package hard.home.model;

public class Permissao {

    private int cod;
    private int fr_codUser;
    private String fr_codTela;
    private boolean permite;

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public int getFr_codUser() {
        return fr_codUser;
    }

    public void setFr_codUser(int fr_codUser) {
        this.fr_codUser = fr_codUser;
    }

    public String getFr_codTela() {
        return fr_codTela;
    }

    public void setFr_codTela(String fr_codTela) {
        this.fr_codTela = fr_codTela;
    }

    public boolean isPermite() {
        return permite;
    }

    public void setPermite(boolean permite) {
        this.permite = permite;
    }

}
