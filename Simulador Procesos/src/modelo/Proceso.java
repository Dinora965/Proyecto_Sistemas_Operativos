package modelo;

public class Proceso {
    private String id;
    private int tiempoLlegada;
    private int rafaga;
    private int prioridad;

    // Tiempos calculados
    private int tiempoEspera;
    private int tiempoRespuesta;
    private int tiempoRetorno;

    // Constructor
    public Proceso(String id, int tiempoLlegada, int rafaga, int prioridad) {
        this.id = id;
        this.tiempoLlegada = tiempoLlegada;
        this.rafaga = rafaga;
        this.prioridad = prioridad;
    }

    // Getters y Setters
    public String getId() { return id; }
    public int getTiempoLlegada() { return tiempoLlegada; }
    public int getRafaga() { return rafaga; }
    public int getPrioridad() { return prioridad; }
    
    public int getTiempoEspera() { return tiempoEspera; }
    public void setTiempoEspera(int tiempoEspera) { this.tiempoEspera = tiempoEspera; }

    public int getTiempoRespuesta() { return tiempoRespuesta; }
    public void setTiempoRespuesta(int tiempoRespuesta) { this.tiempoRespuesta = tiempoRespuesta; }

    public int getTiempoRetorno() { return tiempoRetorno; }
    public void setTiempoRetorno(int tiempoRetorno) { this.tiempoRetorno = tiempoRetorno; }
}
