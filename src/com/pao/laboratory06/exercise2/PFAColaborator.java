package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class PFAColaborator extends Colaborator implements PersoanaFizica {
    private double cheltuieliLunare;

    public PFAColaborator() {
        super(TipColaborator.PFA);
    }

    @Override
    public void citeste(Scanner in) {
        this.nume = in.next();
        this.prenume = in.next();
        this.venitLunar = in.nextDouble();
        this.cheltuieliLunare = in.nextDouble();
    }

    @Override
    public double calculeazaVenitNetAnual() {
        double venitNet = (venitLunar - cheltuieliLunare) * 12;
        double impozit = 0.10 * venitNet;

        double smb = 48600;

        double cass = 0;
        if (venitNet < 6 * smb) {
            cass = 0.10 * (6 * smb);
        } else if (venitNet <= 72 * smb) {
            cass = 0.10 * venitNet;
        } else {
            cass = 0.10 * (72 * smb);
        }

        double cas = 0;
        if (venitNet < 12 * smb) {
            cas = 0;
        } else if (venitNet <= 24 * smb) {
            cas = 0.25 * (12 * smb);
        } else {
            cas = 0.25 * (24 * smb);
        }

        return venitNet - impozit - cass - cas;
    }
}