package com.pao.laboratory05.angajati;
import java.util.Scanner;
/**
 * Exercise 3 — Angajați
 *
 * Cerințele complete se află în:
 *   src/com/pao/laboratory05/Readme.md  →  secțiunea "Exercise 3 — Angajați"
 *
 * Creează fișierele de la zero în acest pachet, apoi rulează Main.java
 * pentru a verifica output-ul așteptat din Readme.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AngajatService service = AngajatService.getInstance();

        while (true) {
            System.out.println("\n===== Gestionare Angajați =====");
            System.out.println("1. Adaugă angajat");
            System.out.println("2. Listare după salariu (descrescător)");
            System.out.println("3. Caută după departament");
            System.out.println("0. Ieșire");
            System.out.print("Opțiune: ");

            String optiune = scanner.nextLine();

            switch (optiune) {
                case "1":
                    System.out.print("Nume angajat: ");
                    String nume = scanner.nextLine();
                    System.out.print("Nume departament: ");
                    String numeDept = scanner.nextLine();
                    System.out.print("Locație departament: ");
                    String locatie = scanner.nextLine();
                    System.out.print("Salariu: ");
                    double salariu = Double.parseDouble(scanner.nextLine());

                    service.addAngajat(new Angajat(nume, new Departament(numeDept, locatie), salariu));
                    break;
                case "2":
                    service.listBySalary();
                    break;
                case "3":
                    System.out.print("Introduceți numele departamentului: ");
                    String deptCautat = scanner.nextLine();
                    service.findByDepartament(deptCautat);
                    break;
                case "0":
                    System.out.println("La revedere!");
                    return;
                default:
                    System.out.println("Opțiune invalidă!");
            }
        }
    }
}
