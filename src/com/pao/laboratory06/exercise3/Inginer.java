package com.pao.laboratory06.exercise3;

public class Inginer extends Angajat implements PlataOnline, Comparable<Inginer> {
    private double soldCont;

    public Inginer(String nume, String prenume, String telefon, double salariu, double soldInitial) {
        super(nume, prenume, telefon, salariu);
        this.soldCont = soldInitial;
    }

    @Override
    public void autentificare(String user, String parola) {
        if (user == null || user.isEmpty() || parola == null || parola.isEmpty()) {
            throw new IllegalArgumentException("User-ul și parola nu pot fi nule sau goale!");
        }
        System.out.println("Inginerul " + nume + " s-a autentificat cu succes.");
    }

    @Override
    public double consultareSold() {
        return soldCont;
    }

    @Override
    public boolean efectuarePlata(double suma) {
        if (suma <= 0) throw new IllegalArgumentException("Suma de plată trebuie să fie pozitivă!");
        if (soldCont >= suma) {
            soldCont -= suma;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(Inginer altul) {
        return this.nume.compareTo(altul.nume);
    }

    @Override
    public String toString() {
        return "Inginer{" + "nume='" + nume + '\'' + ", salariu=" + salariu + '}';
    }
}