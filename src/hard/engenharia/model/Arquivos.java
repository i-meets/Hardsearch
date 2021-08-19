package hard.engenharia.model;

public class Arquivos {

    private int codArquivo;
    private int fr_tipoArquivoCod;
    private String link;
    private int fr_alteracaoRevCod;
    private int codTipo;
    private String descricao;

    public int getCodArquivo() {
        return codArquivo;
    }

    public void setCodArquivo(int codArquivo) {
        this.codArquivo = codArquivo;
    }

    public int getFr_tipoArquivoCod() {
        return fr_tipoArquivoCod;
    }

    public void setFr_tipoArquivoCod(int fr_tipoArquivoCod) {
        this.fr_tipoArquivoCod = fr_tipoArquivoCod;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getFr_alteracaoRevCod() {
        return fr_alteracaoRevCod;
    }

    public void setFr_alteracaoRevCod(int fr_alteracaoRevCod) {
        this.fr_alteracaoRevCod = fr_alteracaoRevCod;
    }

    public int getCodTipo() {
        return codTipo;
    }

    public void setCodTipo(int codTipo) {
        this.codTipo = codTipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
