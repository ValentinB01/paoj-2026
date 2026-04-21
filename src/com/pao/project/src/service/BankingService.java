package com.pao.project.src.service;

import com.pao.project.src.exception.ClientNotFoundException;
import com.pao.project.src.exception.InsufficientFundsException;
import com.pao.project.src.model.*;

import java.util.*;

public class BankingService {
    private static BankingService instance;

    private Map<String, Client> dictionarClienti = new HashMap<>();
    private TreeSet<Account> toateConturile = new TreeSet<>();

    private BankingService() {}

    public static BankingService getInstance() {
        if (instance == null) {
            instance = new BankingService();
        }
        return instance;
    }

    // --- Client CRUD ---

    public void inregistreazaClient(Client client) {
        dictionarClienti.put(client.getCnp(), client);
        System.out.println("Clientul " + client.getNume() + " a fost inregistrat.");
    }

    public void stergeClient(String cnp) throws ClientNotFoundException {
        Client client = dictionarClienti.remove(cnp);
        if (client == null) {
            throw new ClientNotFoundException("Clientul cu CNP " + cnp + " nu a fost gasit.");
        }
        for (Account cont : client.getConturi()) {
            toateConturile.remove(cont);
        }
        System.out.println("Clientul " + client.getNume() + " a fost sters din sistem.");
    }

    public Client gasesteClientDupaCnp(String cnp) throws ClientNotFoundException {
        Client client = dictionarClienti.get(cnp);
        if (client == null) {
            throw new ClientNotFoundException("Clientul cu CNP " + cnp + " nu a fost gasit.");
        }
        return client;
    }

    public List<Client> listeazaTotiClientii() {
        return new ArrayList<>(dictionarClienti.values());
    }

    // --- Cont operations ---

    public void deschideCont(String cnp, Account cont) throws ClientNotFoundException {
        Client client = gasesteClientDupaCnp(cnp);
        client.adaugaCont(cont);
        toateConturile.add(cont);
        System.out.println("Cont nou deschis pentru: " + client.getNume());
    }

    public void depunereInCont(String iban, double suma) throws ClientNotFoundException {
        Account cont = gasesteContDupaIban(iban);
        toateConturile.remove(cont);
        cont.depunere(suma);
        toateConturile.add(cont);
    }

    public void efectueazaRetragere(String iban, double suma) throws ClientNotFoundException, InsufficientFundsException {
        Account cont = gasesteContDupaIban(iban);
        toateConturile.remove(cont);
        boolean succes = cont.retragere(suma);
        toateConturile.add(cont);
        if (!succes) {
            throw new InsufficientFundsException("Fonduri insuficiente in contul cu IBAN: " + iban);
        }
    }

    public void transfer(String ibanSursa, String ibanDest, double suma) throws ClientNotFoundException, InsufficientFundsException {
        Account sursa = gasesteContDupaIban(ibanSursa);
        Account destinatar = gasesteContDupaIban(ibanDest);

        if (sursa.getSold() < suma) {
            throw new InsufficientFundsException("Fonduri insuficiente pentru transfer din contul " + ibanSursa);
        }

        toateConturile.remove(sursa);
        toateConturile.remove(destinatar);

        sursa.retragere(suma);
        destinatar.depunere(suma);

        toateConturile.add(sursa);
        toateConturile.add(destinatar);
        System.out.println("Transfer realizat cu succes!");
    }

    // --- Interogari ---

    public Account gasesteContDupaIban(String iban) throws ClientNotFoundException {
        for (Account acc : toateConturile) {
            if (acc.getIban().equals(iban)) return acc;
        }
        throw new ClientNotFoundException("Contul cu IBAN " + iban + " nu a fost gasit.");
    }

    public double calculeazaAvereTotalaClient(String cnp) throws ClientNotFoundException {
        Client c = gasesteClientDupaCnp(cnp);
        return c.getConturi().stream()
                .mapToDouble(Account::getSold)
                .sum();
    }

    public void afiseazaExtrasulContului(String iban) throws ClientNotFoundException {
        Account cont = gasesteContDupaIban(iban);
        List<Transaction> tranzactii = cont.getTranzactii();
        System.out.println("\n--- EXTRAS CONT " + iban + " ---");
        if (tranzactii.isEmpty()) {
            System.out.println("Nu exista tranzactii.");
        } else {
            for (Transaction t : tranzactii) {
                System.out.println(t);
            }
        }
        System.out.println("Sold curent: " + cont.getSold() + " " + cont.getMoneda());
    }

    public void afiseazaRaportConturiSortate() {
        System.out.println("\n--- RAPORT BANCAR (Conturi sortate dupa sold) ---");
        if (toateConturile.isEmpty()) {
            System.out.println("Nu exista conturi in sistem.");
        } else {
            for (Account acc : toateConturile) {
                System.out.println(acc.toString());
            }
        }
    }
}
