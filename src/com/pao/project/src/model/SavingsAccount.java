package com.pao.project.src.model;

public class SavingsAccount extends Account {
    private double rataDobanda;
    private int perioadaLuni;

    public SavingsAccount(String iban, double sold, String moneda, double rataDobanda, int perioadaLuni) {
        super(iban, sold, moneda);
        this.rataDobanda = rataDobanda;
        this.perioadaLuni = perioadaLuni;
    }

    public int getPerioadaLuni() { return perioadaLuni; }
    public void setPerioadaLuni(int perioadaLuni) { this.perioadaLuni = perioadaLuni; }

    public double getRataDobanda() { return rataDobanda; }

    public double calculeazaDobanda() {
        return getSold() * (rataDobanda / 100) * (perioadaLuni / 12.0);
    }

    @Override
    public void afiseazaDetaliiSpecific() {
        System.out.println("CONT ECONOMII | Perioada: " + perioadaLuni + " luni | Dobanda: " + rataDobanda + "%");
    }
}
