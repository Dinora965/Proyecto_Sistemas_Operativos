package algoritmos;

import modelo.Proceso;
import java.util.*;

public class RoundRobin {
    private static List<String> secuenciaEjecucion = new ArrayList<>();
    private static Map<String, Integer> bloquesEjecucion = new HashMap<>();

    public static List<Proceso> ejecutar(List<Proceso> procesosOriginales, int quantum) {
        secuenciaEjecucion.clear();
        bloquesEjecucion.clear();
        
        List<Proceso> procesos = new ArrayList<>();
        for (Proceso p : procesosOriginales) {
            procesos.add(new Proceso(p.getId(), p.getTiempoLlegada(), p.getRafaga(), p.getPrioridad()));
        }

        List<Proceso> resultado = new ArrayList<>();
        Queue<Proceso> cola = new LinkedList<>();
        int[] tiempoRestante = new int[procesos.size()];
        boolean[] iniciado = new boolean[procesos.size()];
        Map<String, Integer> indexMap = new HashMap<>();

        // Inicialización
        for (int i = 0; i < procesos.size(); i++) {
            Proceso p = procesos.get(i);
            tiempoRestante[i] = p.getRafaga();
            indexMap.put(p.getId(), i);
        }

        int tiempoActual = 0;

        while (!procesos.isEmpty() || !cola.isEmpty()) {
            // Añadir procesos que han llegado
            Iterator<Proceso> it = procesos.iterator();
            while (it.hasNext()) {
                Proceso p = it.next();
                if (p.getTiempoLlegada() <= tiempoActual) {
                    cola.add(p);
                    it.remove();
                }
            }

            if (!cola.isEmpty()) {
                Proceso actual = cola.poll();
                int index = indexMap.get(actual.getId());

                // Registrar primera ejecución
                if (!iniciado[index]) {
                    iniciado[index] = true;
                    actual.setTiempoRespuesta(tiempoActual - actual.getTiempoLlegada());
                }

                int ejecutar = Math.min(quantum, tiempoRestante[index]);
                
                // Registrar ejecución para Gantt
                for (int i = 0; i < ejecutar; i++) {
                    secuenciaEjecucion.add(actual.getId());
                }
                bloquesEjecucion.put(actual.getId() + (secuenciaEjecucion.size()-ejecutar), ejecutar);
                
                tiempoRestante[index] -= ejecutar;
                tiempoActual += ejecutar;

                // Verificar nuevos procesos que llegaron durante esta ejecución
                it = procesos.iterator();
                while (it.hasNext()) {
                    Proceso p = it.next();
                    if (p.getTiempoLlegada() <= tiempoActual) {
                        cola.add(p);
                        it.remove();
                    }
                }

                if (tiempoRestante[index] > 0) {
                    cola.add(actual); // Vuelve a la cola
                } else {
                    // Proceso terminado
                    actual.setTiempoRetorno(tiempoActual - actual.getTiempoLlegada());
                    actual.setTiempoEspera(actual.getTiempoRetorno() - actual.getRafaga());
                    resultado.add(actual);
                }
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
