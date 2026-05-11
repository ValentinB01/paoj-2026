package com.pao.laboratory09.exercise1;

import java.io.*;
import java.util.*;

public class Main {
    private static final String OUTPUT_FILE = "output/lab09_ex1.ser";
    
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) return;

        int n = scanner.nextInt();
        List<Tranzactie> tranzactii = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int id = scanner.nextInt();
            double suma = scanner.nextDouble();
            String data = scanner.next();
            String contSursa = scanner.next();
            String contDestinatie = scanner.next();
            TipTranzactie tip = TipTranzactie.valueOf(scanner.next());

            Tranzactie t = new Tranzactie(id, suma, data, contSursa, contDestinatie, tip);
            t.note = "procesat";
            tranzactii.add(t);
        }

        File file = new File(OUTPUT_FILE);
        file.getParentFile().mkdirs();

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(tranzactii);
        }

        List<Tranzactie> deserializedTranzactii;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            deserializedTranzactii = (List<Tranzactie>) ois.readObject();
        }

        while (scanner.hasNext()) {
            String command = scanner.next();

            if (command.equals("LIST")) {
                for (Tranzactie t : deserializedTranzactii) {
                    System.out.println(t);
                }

            } else if (command.equals("FILTER")) {
                String filterDate = scanner.next();
                boolean found = false;
                for (Tranzactie t : deserializedTranzactii) {
                    if (t.data.startsWith(filterDate)) {
                        System.out.println(t);
                        found = true;
                    }
                }
                if (!found) {
                    System.out.println("Niciun rezultat.");
                }

            } else if (command.equals("NOTE")) {
                int searchId = scanner.nextInt();
                boolean found = false;
                for (Tranzactie t : deserializedTranzactii) {
                    if (t.id == searchId) {
                        System.out.println("NOTE[" + searchId + "]: " + t.note);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("NOTE[" + searchId + "]: not found");
                }
            }
        }

        scanner.close();
    }
}