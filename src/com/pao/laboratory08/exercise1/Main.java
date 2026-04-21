package com.pao.laboratory08.exercise1;

import java.io.*;
import java.util.*;

public class Main {
    // Calea către fișierul cu date — relativă la rădăcina proiectului
    private static final String FILE_PATH = "src/com/pao/laboratory08/tests/studenti.txt";

    public static void main(String[] args) throws Exception {
        List<Student> studenti = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",");
                String nume = parts[0].trim();
                int varsta = Integer.parseInt(parts[1].trim());
                String oras = parts[2].trim();
                String strada = parts[3].trim();
                studenti.add(new Student(nume, varsta, new Adresa(oras, strada)));
            }
        }

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String comanda = stdin.readLine().trim();

        if (comanda.equals("PRINT")) {
            for (Student s : studenti) {
                System.out.println(s);
            }
        } else if (comanda.startsWith("SHALLOW ")) {
            String nume = comanda.split(" ", 2)[1];
            Student original = null;
            for (Student s : studenti) {
                if (s.getNume().equals(nume)) {
                    original = s;
                    break;
                }
            }
            Student clona = original.shallowClone();
            clona.getAdresa().setOras("MODIFICAT");
            System.out.println("Original: " + original);
            System.out.println("Clona: " + clona);
        } else if (comanda.startsWith("DEEP ")) {
            String nume = comanda.split(" ", 2)[1];
            Student original = null;
            for (Student s : studenti) {
                if (s.getNume().equals(nume)) {
                    original = s;
                    break;
                }
            }
            Student clona = original.deepClone();
            clona.getAdresa().setOras("MODIFICAT");
            System.out.println("Original: " + original);
            System.out.println("Clona: " + clona);
        }
    }
}
