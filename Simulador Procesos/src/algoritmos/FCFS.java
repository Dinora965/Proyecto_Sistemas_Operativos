package algoritmos;

import modelo.Proceso;
import java.util.*;

public class FCFS {
    private static List<String> secuenciaEjecucion = new ArrayList<>();
    private static Map<String, Integer> bloquesEjecucion = new HashMap<>();

    public static List<Proceso> ejecutar(List<Proceso> procesos) {
        secuenciaEjecucion.clear();
        bloquesEjecucion.clear();
        
        List<Proceso> listaOrdenada = new ArrayList<>();
        for (Proceso p : procesos) {
            listaOrdenada.add(new Proceso(p.getId(), p.getTiempoLlegada(), p.getRafaga(), p.getPrioridad()));
        }
        listaOrdenada.sort(Comparator.comparingInt(Proceso::getTiempoLlegada));
        
        int tiempoActual = 0;
        for (Proceso p : listaOrdenada) {
            // Manejar tiempo de inactividad
            if (tiempoActual < p.getTiempoLlegada()) {
                int tiempoInactivo = p.getTiempoLlegada() - tiempoActual;
                secuenciaEjecucion.add("Idle");
                bloquesEjecucion.put("Idle" + (secuenciaEjecucion.size()-1), tiempoInactivo);
                tiempoActual = p.getTiempoLlegada();
            }
            
            // Registrar ejecución del proceso
            secuenciaEjecucion.add(p.getId());
            bloquesEjecucion.put(p.getId() + (secuenciaEjecucion.size()-1), p.getRafaga());
            
            // Calcular métricas
            p.setTiempoEspera(tiempoActual - p.getTiempoLlegada());
            p.setTiempoRespuesta(p.getTiempoEspera());
            tiempoActual += p.getRafaga();
            p.setTiempoRetorno(tiempoActual - p.getTiempoLlegada());
        }

        return listaOrdenada;
    }

    public static List<String> getSecuenciaEjecucion() {
        return secuenciaEjecucion;
    }
    
    public static Map<String, Integer> getBloquesEjecucion() {
        return bloquesEjecucion;
    }
}