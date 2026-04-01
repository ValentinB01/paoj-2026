package com.pao.laboratory06.exercise2;
import java.util.Scanner;

public class CIMColaborator extends Colaborator implements PersoanaFizica {
    private boolean bonus;

    public CIMColaborator() {
        super(TipColaborator.CIM);
    }

    @Override
    public void citeste(Scanner in) {
        this.nume = in.next();
        this.prenume = in.next();
        this.venitLunar = in.nextDouble();

        if (in.hasNext()) {
            String b = in.next();
            this.bonus = b.equalsIgnoreCase("DA");
        } else {
            this.bonus = false;
        }
    }

    @Override
    public boolean areBonus() {
        return bonus;
    }

    @Override
    public double calculeazaVenitNetAnual() {
        double net = venitLunar * 12 * 0.55;
        if (areBonus()) {
            net += net * 0.10;
        }
        return net;
    }
}
