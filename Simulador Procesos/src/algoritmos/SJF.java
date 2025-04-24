package algoritmos;

import modelo.Proceso;
import java.util.*;

public class SJF {
    private static List<String> secuenciaEjecucion = new ArrayList<>();
    private static Map<String, Integer> bloquesEjecucion = new HashMap<>();

    public static List<Proceso> ejecutar(List<Proceso> procesosOriginales) {
        secuenciaEjecucion.clear();
        bloquesEjecucion.clear();
        
        List<Proceso> procesos = new ArrayList<>();
        for (Proceso p : procesosOriginales) {
            procesos.add(new Proceso(p.getId(), p.getTiempoLlegada(), p.getRafaga(), p.getPrioridad()));
        }

        List<Proceso> resultado = new ArrayList<>();
        PriorityQueue<Proceso> colaPrioridad = new PriorityQueue<>(
            Comparator.comparingInt(Proceso::getRafaga)
        );

        int tiempoActual = 0;

        while (!procesos.isEmpty() || !colaPrioridad.isEmpty()) {
            // Mover procesos que ya llegaron a la cola de prioridad
            Iterator<Proceso> it = procesos.iterator();
            while (it.hasNext()) {
                Proceso p = it.next();
                if (p.getTiempoLlegada() <= tiempoActual) {
                    colaPrioridad.add(p);
                    it.remove();
                }
            }

            if (!colaPrioridad.isEmpty()) {
                Proceso actual = colaPrioridad.poll();
                
                // Registrar ejecución para Gantt
                secuenciaEjecucion.add(actual.getId());
                bloquesEjecucion.put(actual.getId() + (secuenciaEjecucion.size()-1), actual.getRafaga());
                
                // Calcular métricas
                actual.setTiempoEspera(tiempoActual - actual.getTiempoLlegada());
                actual.setTiempoRespuesta(actual.getTiempoEspera());
                tiempoActual += actual.getRafaga();
                actual.setTiempoRetorno(tiempoActual - actual.getTiempoLlegada());
                
                resultado.add(actual);
            } else {
                // Tiempo inactivo
                secuenciaEjecucion.add("Idle");
                bloquesEjecucion.put("Idle" + (secuenciaEjecucion.size()-1), 1);
                tiempoActual++;
            }
        }

        return resultado;
    }

    public static List<String> getSecuenciaEjecucion() {
        return secuenciaEjecucion;
    }
    
    public static Map<String, Integer> getBloquesEjecucion() {
        return bloquesEjecucion;
    }
}