package com.pao.laboratory06.exercise1;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        scanner.useLocale(Locale.US);

        if (!scanner.hasNext()) return;
        String optiune = scanner.next();

        int numarAngajati = scanner.nextInt();
        Angajat[] angajati = new Angajat[numarAngajati];

        for (int i = 0; i < numarAngajati; i++) {
            String nume = scanner.next();
            double salariu = scanner.nextDouble();
            angajati[i] = new Angajat(nume, salariu);
        }

        switch (optiune) {
            case "by_salary":
                Arrays.sort(angajati);
                break;

            case "by_name":
                Arrays.sort(angajati, Comparator.comparing(Angajat::getNume));
                break;

            case "by_salary_desc":
                Arrays.sort(angajati, (a1, a2) -> Double.compare(a2.getSalariu(), a1.getSalariu()));
                break;

            default:
                break;
        }

        for (Angajat angajat : angajati) {
            System.out.println(angajat);
        }

        scanner.close();
    }
}
