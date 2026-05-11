package com.pao.laboratory09.exercise3;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        CoadaTranzactii banda = new CoadaTranzactii();

        ATMThread atm1 = new ATMThread(1, banda);
        ATMThread atm2 = new ATMThread(2, banda);
        ATMThread atm3 = new ATMThread(3, banda);

        ProcessorThread processorLogic = new ProcessorThread(banda);
        Thread processorThread = new Thread(processorLogic);

        processorThread.start();
        atm1.start();
        atm2.start();
        atm3.start();

        atm1.join();
        atm2.join();
        atm3.join();

        processorLogic.activ = false;
        synchronized (banda) {
            banda.notifyAll();
        }

        processorThread.join();

        System.out.println("Toate tranzactiile procesate. Total: 12");
    }
}