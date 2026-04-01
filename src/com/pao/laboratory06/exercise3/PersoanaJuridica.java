package com.pao.laboratory06.exercise3;

import java.util.ArrayList;
import java.util.List;

public class PersoanaJuridica extends Persoana implements PlataOnlineSMS {
    private List<String> smsTrimise;
    private double soldCont;

    public PersoanaJuridica(String nume, String telefon, double soldInitial) {
        super(nume, "", telefon); // Companiile nu au prenume
        this.smsTrimise = new ArrayList<>();
        this.soldCont = soldInitial;
    }

    @Override
    public void autentificare(String user, String parola) {
        if (user == null || user.isBlank() || parola == null || parola.isBlank()) {
            throw new IllegalArgumentException("Date de autentificare invalide pentru PJ!");
        }
        System.out.println("Compania " + nume + " s-a autentificat.");
    }

    @Override
    public double consultareSold() {
        return soldCont;
    }

    @Override
    public boolean efectuarePlata(double suma) {
        if (suma <= 0) throw new IllegalArgumentException("Suma invalidă!");
        if (soldCont >= suma) {
            soldCont -= suma;
            return true;
        }
        return false;
    }

    @Override
    public boolean trimiteSMS(String mesaj) {
        if (mesaj == null || mesaj.isBlank()) {
            throw new IllegalArgumentException("Mesajul SMS nu poate fi gol!");
        }
        if (this.telefon == null || this.telefon.isBlank()) {
            return false;
        }
        smsTrimise.add(mesaj);
        return true;
    }

    public void afiseazaIstoricSMS() {
        System.out.println("Istoric SMS pentru " + nume + ": " + smsTrimise);
    }
}