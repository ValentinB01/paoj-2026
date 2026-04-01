package com.pao.laboratory06.exercise3;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== 1. Demonstrație Constante Financiare ===");
        System.out.println("TVA curent este: " + ConstanteFinanciare.TVA.getValoare() * 100 + "%");

        System.out.println("\n=== 2. Sortare Ingineri ===");
        Inginer[] ingineri = {
                new Inginer("Zaharia", "Ion", "0722", 5000, 1000),
                new Inginer("Avram", "Dan", "0733", 8000, 2000),
                new Inginer("Popescu", "Ana", null, 6500, 1500)
        };

        System.out.println("Sortare naturală (alfabetic):");
        Arrays.sort(ingineri);
        for (Inginer i : ingineri) System.out.println(i);

        System.out.println("\nSortare alternativă (salariu descrescător):");
        Arrays.sort(ingineri, new ComparatorInginerSalariu());
        for (Inginer i : ingineri) System.out.println(i);

        System.out.println("\n=== 3. Restricție prin Referință de tip Interfață ===");
        PlataOnline referintaInginer = new Inginer("Gheorghe", "Mihai", "0700", 4000, 500);
        referintaInginer.autentificare("user", "pass");

        System.out.println("\n=== 4. Tratarea Excepțiilor (Edge Cases) ===");

        try {
            System.out.print("Încercăm SMS pe inginer... ");
            referintaInginer.trimiteSMS("Salut!");
        } catch (UnsupportedOperationException e) {
            System.out.println("Eroare prinsă corect: " + e.getMessage());
        }

        try {
            System.out.print("Încercăm autentificare cu user null... ");
            referintaInginer.autentificare(null, "parola");
        } catch (IllegalArgumentException e) {
            System.out.println("Eroare prinsă corect: " + e.getMessage());
        }

        System.out.println("\n=== 5. Persoana Juridică și capabilitate SMS ===");
        PersoanaJuridica firmaCuTelefon = new PersoanaJuridica("Tech SRL", "0799123456", 50000);
        PersoanaJuridica firmaFaraTelefon = new PersoanaJuridica("Ghost SRL", null, 10000);

        PlataOnlineSMS refFirma = firmaCuTelefon;
        refFirma.autentificare("admin", "1234");

        boolean trimis1 = refFirma.trimiteSMS("Plata confirmata!");
        System.out.println("SMS trimis către Tech SRL? " + trimis1);
        firmaCuTelefon.afiseazaIstoricSMS();

        boolean trimis2 = firmaFaraTelefon.trimiteSMS("Plata confirmata!");
        System.out.println("SMS trimis către Ghost SRL (fără telefon)? " + trimis2);

        try {
            System.out.print("Încercăm să trimitem mesaj NULL... ");
            firmaCuTelefon.trimiteSMS(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Eroare prinsă corect: " + e.getMessage());
        }
    }
}