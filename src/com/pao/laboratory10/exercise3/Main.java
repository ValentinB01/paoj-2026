package com.pao.laboratory10.exercise3;

import com.pao.laboratory10.exercise1.TipTranzactie;
import java.util.*;
import java.util.stream.Collectors;

class Tranzactie {
    private int id;
    private double suma;
    private String data;
    private TipTranzactie tip;
    private String contSursa;

    public Tranzactie(int id, double suma, String data, TipTranzactie tip, String contSursa) {
        this.id = id;
        this.suma = suma;
        this.data = data;
        this.tip = tip;
        this.contSursa = contSursa;
    }

    public double getSuma() { return suma; }
    public String getData() { return data; }
    public TipTranzactie getTip() { return tip; }
    public String getContSursa() { return contSursa; }

    @Override
    public String toString() {
        return String.format(Locale.US, "[%d] %s %s: %.2f RON (%s)", id, data, tip, suma, contSursa);
    }
}

public class Main {
    public static void main(String[] args) {
        List<Tranzactie> tranzactii = Arrays.asList(
                new Tranzactie(1, 1500.0, "2024-01-10", TipTranzactie.CREDIT, "RO01BANK"),
                new Tranzactie(2, 200.0, "2024-01-15", TipTranzactie.DEBIT, "RO02USER"),
                new Tranzactie(3, 50.0, "2024-01-20", TipTranzactie.DEBIT, "RO02USER"),
                new Tranzactie(4, 3000.0, "2024-02-05", TipTranzactie.CREDIT, "RO01BANK"),
                new Tranzactie(5, 120.0, "2024-02-12", TipTranzactie.DEBIT, "RO03MISC"),
                new Tranzactie(6, 450.0, "2024-02-25", TipTranzactie.DEBIT, "RO02USER"),
                new Tranzactie(7, 100.0, "2024-03-01", TipTranzactie.DEBIT, "RO03MISC"),
                new Tranzactie(8, 2200.0, "2024-03-10", TipTranzactie.CREDIT, "RO01BANK"),
                new Tranzactie(9, 35.5, "2024-03-15", TipTranzactie.DEBIT, "RO02USER"),
                new Tranzactie(10, 800.0, "2024-03-20", TipTranzactie.CREDIT, "RO01BANK"),
                new Tranzactie(11, 150.0, "2024-03-25", TipTranzactie.DEBIT, "RO03MISC"),
                new Tranzactie(12, 10.0, "2024-03-28", TipTranzactie.DEBIT, "RO02USER")
        );

        System.out.println(" DEMONSTRATIE STREAM API \n");

        System.out.println("1. Lista tuturor tranzactiilor CREDIT:");
        tranzactii.stream()
                .filter(t -> t.getTip() == TipTranzactie.CREDIT)
                .forEach(System.out::println);

        System.out.println("\n2. Total procesat:");
        double total = tranzactii.stream()
                .mapToDouble(Tranzactie::getSuma)
                .sum();
        System.out.printf(Locale.US, "Total procesat: %.2f RON\n", total);

        System.out.println("\n3. Per luna (yyyy-MM):");
        Map<String, Double> sumaPeLuna = tranzactii.stream()
                .collect(Collectors.groupingBy(
                        t -> t.getData().substring(0, 7),
                        TreeMap::new,
                        Collectors.summingDouble(Tranzactie::getSuma)
                ));
        sumaPeLuna.forEach((luna, suma) ->
                System.out.printf(Locale.US, "%s: %.2f RON\n", luna, suma));

        System.out.println("\n4. Top 3 tranzactii:");
        tranzactii.stream()
                .sorted(Comparator.comparingDouble(Tranzactie::getSuma).reversed())
                .limit(3)
                .forEach(System.out::println);

        System.out.println("\n5. Conturi sursa unice:");
        List<String> conturiUnice = tranzactii.stream()
                .map(Tranzactie::getContSursa)
                .distinct()
                .collect(Collectors.toList());
        System.out.println(conturiUnice);

        System.out.println("\n6. Suma medie:");
        double medie = tranzactii.stream()
                .mapToDouble(Tranzactie::getSuma)
                .average()
                .orElse(0.0);
        System.out.printf(Locale.US, "Suma medie: %.2f RON\n", medie);

        System.out.println("\n7. Extrase de cont lunare:");
        Map<String, List<Tranzactie>> grupate = tranzactii.stream()
                .collect(Collectors.groupingBy(t -> t.getData().substring(0, 7), TreeMap::new, Collectors.toList()));

        grupate.forEach((luna, lista) -> {
            double totalLuna = lista.stream().mapToDouble(Tranzactie::getSuma).sum();
            System.out.printf(Locale.US, "EXTRAS DE CONT - %s: %d tranzactii, total: %.2f RON\n",
                    luna, lista.size(), totalLuna);
        });
    }
}