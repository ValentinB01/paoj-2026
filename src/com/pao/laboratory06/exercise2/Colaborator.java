package com.pao.laboratory06.exercise2;
import java.util.Locale;

public abstract class Colaborator implements IOperatiiCitireScriere, Comparable<Colaborator>{
    protected String nume;
    protected String prenume;
    protected double venitLunar;
    protected TipColaborator tip;

    public Colaborator(TipColaborator tip)
    {
        this.tip = tip;
    }
    public abstract double calculeazaVenitNetAnual();

    @Override
    public int compareTo(Colaborator altul) {
        return Double.compare(altul.calculeazaVenitNetAnual(), this.calculeazaVenitNetAnual());
    }

    public TipColaborator getTip() {
        return tip;
    }

    @Override
    public void afiseaza() {
        System.out.printf(Locale.US, "%s: %s %s, venit net anual: %.2f lei\n",
                tipContract(), nume, prenume, calculeazaVenitNetAnual());
    }

    @Override
    public String tipContract() {
        return tip.name();
    }
}
