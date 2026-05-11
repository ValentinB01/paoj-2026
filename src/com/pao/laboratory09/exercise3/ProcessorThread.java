package com.pao.laboratory09.exercise3;

public class ProcessorThread implements Runnable {
    private final CoadaTranzactii banda;
    public volatile boolean activ = true;

    public ProcessorThread(CoadaTranzactii banda) {
        this.banda = banda;
    }

    @Override
    public void run() {
        try {
            while (activ || !banda.isEmpty()) {
                if (banda.isEmpty() && !activ) break;

                try {
                    Tranzactie t = banda.extrage();
                    System.out.println("[Processor] Factura #" + t.id + " - " + t.suma + " RON | " + t.data);
                    Thread.sleep(80);
                } catch (InterruptedException e) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}