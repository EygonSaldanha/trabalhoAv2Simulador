package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

class Grafico extends JPanel {
    private final Algoritimos algoritimos;
    private final Map<String, Color> coresAlgoritmos;

    public Grafico(Algoritimos algoritimos) {
        this.algoritimos = algoritimos;

        // Configuração das cores de cada algoritmo
        coresAlgoritmos = Map.of(
                "FIFO", Color.BLUE,
                "LRU", Color.GREEN,
                "Clock", Color.RED,
                "Optimal", new Color(0x00FFFF)
        );
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();
        int numBars = 4;
        int barWidth = width / (numBars + 1);
        int spacing = (width - numBars * barWidth) / 2;

        // Obter os resultados de page faults
        int fifoFaults = algoritimos.getPageFaultsFifo();
        int lruFaults = algoritimos.getPageFaultsLru();
        int clockFaults = algoritimos.getPageFaultsClock();
        int optimalFaults = algoritimos.getPageFaultsOptimal();

        // Calcular altura máxima
        int maxFaults = Math.max(Math.max(fifoFaults, lruFaults), Math.max(clockFaults, optimalFaults));
        int scalingFactor = (height - 60) / maxFaults;

        // Dados dos algoritimos e resultados
        String[] nomesAlgoritmos = {"FIFO", "LRU", "Clock", "Optimal"};
        int[] resultadosFaults = {fifoFaults, lruFaults, clockFaults, optimalFaults};

        // Desenhar barras e rótulos
        for (int i = 0; i < numBars; i++) {
            int barHeight = resultadosFaults[i] * scalingFactor;
            g.setColor(coresAlgoritmos.getOrDefault(nomesAlgoritmos[i], Color.GRAY));
            g.fillRect(spacing + i * barWidth, height - barHeight - 40, barWidth - 10, barHeight);

            // Rótulos das barras
            g.setColor(Color.BLACK);
            g.drawString(nomesAlgoritmos[i] + ": " + resultadosFaults[i] + " faults", spacing + i * barWidth, height - 20);
        }
    }
}
