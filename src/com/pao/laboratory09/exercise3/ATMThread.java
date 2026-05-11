package com.pao.laboratory09.exercise3;

public class ATMThread extends Thread {
    private final int id;
    private final CoadaTranzactii banda;

    public ATMThread(int id, CoadaTranzactii banda) {
        this.id = id;
        this.banda = banda;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 4; i++) {
                int trId = id * 100 + i;
                Tranzactie t = new Tranzactie(trId, 100.0 * i, "2024-05-20");
                banda.adauga(t, id);
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}