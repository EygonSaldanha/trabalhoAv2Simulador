package org.example;

import javax.swing.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int[] pages = getPagesFromUser();
        int frames = getFramesFromUser();

        Algoritimos output = new Algoritimos(pages, frames);
        displayResults(output);
        displayChart(output);
    }

    private static int[] getPagesFromUser() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Informe uma cadeia de números inteiros separados por espaço:");
        String[] inputs = sc.nextLine().split(" ");
        int[] pages = new int[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            pages[i] = Integer.parseInt(inputs[i]);
        }
        return pages;
    }

    private static int getFramesFromUser() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Informe o número de páginas (frames):");
        return sc.nextInt();
    }

    private static void displayResults(Algoritimos output) {
        System.out.println(output);
    }

    private static void displayChart(Algoritimos output) {
        JFrame frame = new JFrame("Gráfico de Page Faults");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1820, 1020);
        frame.add(new Grafico(output));
        frame.setVisible(true);
    }
}
