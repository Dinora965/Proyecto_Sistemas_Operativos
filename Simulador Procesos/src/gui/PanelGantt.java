package gui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class PanelGantt extends JPanel {
    private List<String> secuencia;
    private Map<String, Integer> bloquesEjecucion;
    private int anchoUnidad = 30; // Ancho base por unidad de tiempo
    
    public void setSecuencia(List<String> secuencia) {
        this.secuencia = secuencia;
        this.bloquesEjecucion = null;
        repaint();
    }
    
    public void setSecuencia(List<String> secuencia, Map<String, Integer> bloquesEjecucion) {
        this.secuencia = secuencia;
        this.bloquesEjecucion = bloquesEjecucion;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (secuencia == null || secuencia.isEmpty()) return;

        Map<String, Color> colores = new HashMap<>();
        Random random = new Random();
        int x = 10;
        int y = 20;
        int alturaProceso = 30;
        int alturaTiempo = 15;
        
        // Calcular ancho total necesario
        int anchoTotal = calcularAnchoTotal();
        this.setPreferredSize(new Dimension(anchoTotal, 80));

        // Dibujar línea de tiempo
        g.setColor(Color.BLACK);
        g.drawLine(10, y + alturaProceso + 10, 
                 10 + anchoTotal - 20, 
                 y + alturaProceso + 10);

        if (bloquesEjecucion != null) {
            dibujarConBloques(g, colores, random, x, y, alturaProceso, alturaTiempo);
        } else {
            dibujarSinBloques(g, colores, random, x, y, alturaProceso, alturaTiempo);
        }
    }

    private int calcularAnchoTotal() {
        if (bloquesEjecucion != null) {
            int total = 20; // Margen inicial
            for (int i = 0; i < secuencia.size(); i++) {
                String key = secuencia.get(i) + i;
                total += bloquesEjecucion.getOrDefault(key, 1) * anchoUnidad;
            }
            return Math.max(700, total);
        }
        return Math.max(700, secuencia.size() * anchoUnidad + 20);
    }

    private void dibujarConBloques(Graphics g, Map<String, Color> colores, Random random, 
                                 int x, int y, int alturaProceso, int alturaTiempo) {
        int posicionActual = 0;
        
        for (int i = 0; i < secuencia.size(); i++) {
            String proceso = secuencia.get(i);
            String key = proceso + i;
            int duracion = bloquesEjecucion.getOrDefault(key, 1);
            int anchoBloque = duracion * anchoUnidad;

            // Dibujar bloque
            dibujarBloque(g, colores, random, x + posicionActual, y, 
                         anchoBloque, alturaProceso, proceso);

            // Dibujar marca de tiempo al inicio del bloque
            g.setColor(Color.BLACK);
            g.drawLine(x + posicionActual, y + alturaProceso + 5, 
                      x + posicionActual, y + alturaProceso + 15);
            g.drawString(String.valueOf(i), x + posicionActual - 5, y + alturaProceso + 30);

            posicionActual += anchoBloque;
        }

        // Dibujar tiempo final
        g.drawString(String.valueOf(secuencia.size()), 
                   x + posicionActual - 5, 
                   y + alturaProceso + 30);
    }

    private void dibujarSinBloques(Graphics g, Map<String, Color> colores, Random random, 
                                 int x, int y, int alturaProceso, int alturaTiempo) {
        String procesoAnterior = null;
        int inicioBloque = 0;

        for (int i = 0; i < secuencia.size(); i++) {
            String procesoActual = secuencia.get(i);

            if (procesoAnterior == null || !procesoActual.equals(procesoAnterior)) {
                if (procesoAnterior != null) {
                    int duracionBloque = i - inicioBloque;
                    dibujarBloque(g, colores, random, x + inicioBloque * anchoUnidad, y, 
                                duracionBloque * anchoUnidad, alturaProceso, procesoAnterior);
                }
                procesoAnterior = procesoActual;
                inicioBloque = i;
            }

            // Dibujar marca de tiempo
            g.setColor(Color.BLACK);
            g.drawLine(x + i * anchoUnidad, y + alturaProceso + 5, 
                      x + i * anchoUnidad, y + alturaProceso + 15);
            g.drawString(String.valueOf(i), x + i * anchoUnidad - 5, y + alturaProceso + 30);
        }

        // Dibujar último bloque
        if (procesoAnterior != null) {
            int duracionBloque = secuencia.size() - inicioBloque;
            dibujarBloque(g, colores, random, x + inicioBloque * anchoUnidad, y, 
                         duracionBloque * anchoUnidad, alturaProceso, procesoAnterior);
        }

        // Dibujar tiempo final
        g.drawString(String.valueOf(secuencia.size()), 
                   x + secuencia.size() * anchoUnidad - 5, 
                   y + alturaProceso + 30);
    }

    private void dibujarBloque(Graphics g, Map<String, Color> colores, Random random, 
                             int x, int y, int ancho, int alto, String proceso) {
        if (!colores.containsKey(proceso)) {
            if (proceso.equalsIgnoreCase("Idle")) {
                colores.put(proceso, Color.LIGHT_GRAY);
            } else {
                colores.put(proceso, new Color(random.nextInt(200), 
                                      random.nextInt(200), 
                                      random.nextInt(200)));
            }
        }

        g.setColor(colores.get(proceso));
        g.fillRect(x, y, ancho, alto);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, ancho, alto);
        
        // Centrar texto en el bloque
        FontMetrics fm = g.getFontMetrics();
        int textoX = x + (ancho - fm.stringWidth(proceso)) / 2;
        int textoY = y + (alto + fm.getAscent()) / 2;
        g.drawString(proceso, textoX, textoY);
    }
    
    @Override
    public Dimension getPreferredSize() {
        if (secuencia == null || secuencia.isEmpty()) {
            return new Dimension(700, 80);
        }
        return new Dimension(calcularAnchoTotal(), 80);
    }
}
