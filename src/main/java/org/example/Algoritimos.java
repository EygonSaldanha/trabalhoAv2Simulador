package org.example;

import java.util.LinkedList;

public class Algoritimos {
    int[] pages;
    int frames;

    public Algoritimos(int[] pages, int frames) {
        this.pages = pages;
        this.frames = frames;
    }

    public int fifo() {
        LinkedList<Integer> memory = new LinkedList<>();
        int pageFaults = 0;

        for (int page : pages) {
            if (!memory.contains(page)) {
                if (memory.size() == frames) {
                    memory.poll();
                }
                memory.add(page);
                pageFaults++;
            }
        }

        return pageFaults;
    }

    public int lru() {
        LinkedList<Integer> memory = new LinkedList<>();
        int pageFaults = 0;

        for (int page : pages) {
            if (!memory.contains(page)) {
                if (memory.size() == frames) {
                    memory.poll();
                }
                memory.add(page);
                pageFaults++;
            } else {
                memory.remove((Integer) page);
                memory.add(page);
            }
        }
        return pageFaults;
    }

    public int clock() {
        LinkedList<Integer> memory = new LinkedList<>();
        LinkedList<Integer> bits = new LinkedList<>();
        int pageFaults = 0;
        int pointer = 0;

        for (int page : pages) {
            if (!memory.contains(page)) {
                if (memory.size() == frames) {
                    while (true) {
                        if (pointer >= frames) {
                            pointer = 0;
                        }
                        if (bits.get(pointer) == 1) {
                            bits.set(pointer, 0);
                            pointer++;
                        } else {
                            memory.set(pointer, page);
                            bits.set(pointer, 1);
                            pageFaults++;
                            pointer++;
                            break;
                        }
                    }
                } else {
                    memory.add(page);
                    bits.add(1);
                    pageFaults++;
                }
            } else {
                int index = memory.indexOf(page);
                bits.set(index, 1);
            }
        }
        return pageFaults;
    }

    public int optimal() {
        int[] fr = new int[frames];
        int hit = 0;
        int index = 0;

        for (int i = 0; i < pages.length; i++) {
            if (search(pages[i], fr)) {
                hit++;
                continue;
            }
            if (index < frames) {
                fr[index++] = pages[i];
            } else {
                int j = predict(pages, fr, pages.length, i + 1);
                fr[j] = pages[i];
            }
        }
        return pages.length - hit;
    }

    static boolean search(int key, int[] fr) {
        for (int i : fr) {
            if (i == key) return true;
        }
        return false;
    }

    static int predict(int[] pg, int[] fr, int pn, int index) {
        int res = -1, farthest = index;
        for (int i = 0; i < fr.length; i++) {
            int j;
            for (j = index; j < pn; j++) {
                if (fr[i] == pg[j]) {
                    if (j > farthest) {
                        farthest = j;
                        res = i;
                    }
                    break;
                }
            }
            if (j == pn) return i;
        }
        return (res == -1) ? 0 : res;
    }

    public int getPageFaultsFifo() {
        return fifo();
    }

    public int getPageFaultsLru() {
        return lru();
    }

    public int getPageFaultsClock() {
        return clock();
    }

    public int getPageFaultsOptimal() {
        return optimal();
    }

    // Exibir os resultados dos algoritmos no terminal
    @Override
    public String toString() {
        int fifoFaults = getPageFaultsFifo();
        int lruFaults = getPageFaultsLru();
        int clockFaults = getPageFaultsClock();
        int optimalFaults = getPageFaultsOptimal();

        return String.format(
                "Algoritimos Detalhes:\nPages: %s\nFrames: %d\nFIFO Page Faults: %d\nLRU Page Faults: %d\nCLOCK Page Faults: %d\nOptimal Page Faults: %d\n",
                String.join("-", toStringArray(pages)), frames, fifoFaults, lruFaults, clockFaults, optimalFaults
        );
    }

    // Método auxiliar para conversão do array de páginas para String
    private String[] toStringArray(int[] arr) {
        String[] strArray = new String[arr.length];
        for (int i = 0; i < arr.length; i++) {
            strArray[i] = String.valueOf(arr[i]);
        }
        return strArray;
    }
}
