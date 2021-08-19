package hard.model;

public class Evento {

    private int codEvento;
    private String descriEvento;
    private String tipoEvento;
    private String tipoProcessaEvento;

    public void Evento() {

    }

    public int getCodEvento() {
        return codEvento;
    }

    public void setCodEvento(int codEvento) {
        this.codEvento = codEvento;
    }

    public String getDescriEvento() {
        return descriEvento;
    }

    public void setDescriEvento(String descriEvento) {
        this.descriEvento = descriEvento;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public String getTipoProcessaEvento() {
        return tipoProcessaEvento;
    }

    public void setTipoProcessaEvento(String tipoProcessaEvento) {
        this.tipoProcessaEvento = tipoProcessaEvento;
    }

}
