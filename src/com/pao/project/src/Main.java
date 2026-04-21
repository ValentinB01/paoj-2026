package com.pao.project.src;

import com.pao.project.src.exception.ClientNotFoundException;
import com.pao.project.src.exception.InsufficientFundsException;
import com.pao.project.src.model.*;
import com.pao.project.src.service.BankingService;
import com.pao.project.src.service.CardService;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BankingService bankingService = BankingService.getInstance();
        CardService cardService = CardService.getInstance();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("=== SISTEM BANCAR JAVA - ETAPA I ===");

        while (running) {
            System.out.println("\n--- MENIU PRINCIPAL ---");
            System.out.println("1.  Inregistreaza client nou");
            System.out.println("2.  Deschide cont bancar (Curent/Economii)");
            System.out.println("3.  Depunere numerar in cont");
            System.out.println("4.  Retragere numerar din cont");
            System.out.println("5.  Transfer intre conturi");
            System.out.println("6.  Afiseaza extrasul de cont (tranzactii)");
            System.out.println("7.  Calculeaza averea totala a unui client");
            System.out.println("8.  Emite card nou (Debit/Credit)");
            System.out.println("9.  Afiseaza toate conturile bancii (sortate)");
            System.out.println("10. Cauta client dupa CNP");
            System.out.println("0.  Iesire");
            System.out.print("Alege o optiune: ");

            int optiune = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (optiune) {
                    case 1: {
                        System.out.print("Nume: ");
                        String nume = scanner.nextLine();
                        System.out.print("Prenume: ");
                        String prenume = scanner.nextLine();
                        System.out.print("CNP: ");
                        String cnp = scanner.nextLine();
                        bankingService.inregistreazaClient(new Client(nume, prenume, cnp));
                        break;
                    }
                    case 2: {
                        System.out.print("CNP Client: ");
                        String cnpC = scanner.nextLine();
                        System.out.print("Tip Cont (1-Curent, 2-Economii): ");
                        int tip = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("IBAN: ");
                        String iban = scanner.nextLine();
                        if (tip == 1) {
                            bankingService.deschideCont(cnpC, new CurrentAccount(iban, 0, "RON", 500, 5));
                        } else {
                            bankingService.deschideCont(cnpC, new SavingsAccount(iban, 0, "RON", 3.0, 12));
                        }
                        break;
                    }
                    case 3: {
                        System.out.print("IBAN: ");
                        String ibanD = scanner.nextLine();
                        System.out.print("Suma: ");
                        double sumaD = scanner.nextDouble();
                        scanner.nextLine();
                        bankingService.depunereInCont(ibanD, sumaD);
                        break;
                    }
                    case 4: {
                        System.out.print("IBAN: ");
                        String ibanR = scanner.nextLine();
                        System.out.print("Suma: ");
                        double sumaR = scanner.nextDouble();
                        scanner.nextLine();
                        bankingService.efectueazaRetragere(ibanR, sumaR);
                        break;
                    }
                    case 5: {
                        System.out.print("IBAN Sursa: ");
                        String s = scanner.nextLine();
                        System.out.print("IBAN Destinatar: ");
                        String d = scanner.nextLine();
                        System.out.print("Suma: ");
                        double sumaT = scanner.nextDouble();
                        scanner.nextLine();
                        bankingService.transfer(s, d, sumaT);
                        break;
                    }
                    case 6: {
                        System.out.print("IBAN: ");
                        String ibanE = scanner.nextLine();
                        bankingService.afiseazaExtrasulContului(ibanE);
                        break;
                    }
                    case 7: {
                        System.out.print("CNP Client: ");
                        String cnpA = scanner.nextLine();
                        double avere = bankingService.calculeazaAvereTotalaClient(cnpA);
                        System.out.println("Averea totala: " + avere + " RON");
                        break;
                    }
                    case 8: {
                        System.out.print("Tip card (1-Debit, 2-Credit): ");
                        int tipCard = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Numar card: ");
                        String numarCard = scanner.nextLine();
                        System.out.print("Nume posesor: ");
                        String numePosesor = scanner.nextLine();
                        System.out.print("Data expirare (MM/YY): ");
                        String dataExp = scanner.nextLine();
                        System.out.print("PIN: ");
                        String pin = scanner.nextLine();
                        if (tipCard == 1) {
                            System.out.print("IBAN cont asociat: ");
                            String ibanAsociat = scanner.nextLine();
                            Account contAsociat = bankingService.gasesteContDupaIban(ibanAsociat);
                            if (contAsociat instanceof CurrentAccount) {
                                cardService.emiteCard(new DebitCard(numarCard, numePosesor, dataExp, pin,
                                        (CurrentAccount) contAsociat, 5000));
                            } else {
                                System.out.println("Eroare: Cardul de debit necesita un cont curent.");
                            }
                        } else {
                            System.out.print("Limita credit: ");
                            double limita = scanner.nextDouble();
                            scanner.nextLine();
                            cardService.emiteCard(new CreditCard(numarCard, numePosesor, dataExp, pin, limita, 19.9));
                        }
                        break;
                    }
                    case 9: {
                        bankingService.afiseazaRaportConturiSortate();
                        break;
                    }
                    case 10: {
                        System.out.print("Introdu CNP: ");
                        String cnpCautat = scanner.nextLine();
                        Client gasit = bankingService.gasesteClientDupaCnp(cnpCautat);
                        System.out.println(gasit);
                        break;
                    }
                    case 0:
                        running = false;
                        System.out.println("Aplicatie inchisa.");
                        break;
                    default:
                        System.out.println("Optiune invalida!");
                }
            } catch (ClientNotFoundException e) {
                System.out.println("Eroare: " + e.getMessage());
            } catch (InsufficientFundsException e) {
                System.out.println("Eroare: " + e.getMessage());
            }
        }
        scanner.close();
    }
}