package com.pao.laboratory09.exercise2;

import com.pao.laboratory09.exercise1.TipTranzactie;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

public class Main {
    private static final String OUTPUT_FILE = "output/lab09_ex2.bin";
    private static final int RECORD_SIZE = 32;

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        if (!scanner.hasNextInt()) return;

        int n = scanner.nextInt();

        new File("output").mkdirs();

        try (FileOutputStream fos = new FileOutputStream(OUTPUT_FILE)) {
            for (int i = 0; i < n; i++) {
                int id = scanner.nextInt();
                double suma = scanner.nextDouble();
                String data = scanner.next();
                TipTranzactie tip = TipTranzactie.valueOf(scanner.next());

                byte[] record = new byte[RECORD_SIZE];
                ByteBuffer bb = ByteBuffer.wrap(record).order(ByteOrder.LITTLE_ENDIAN);

                bb.putInt(id);
                bb.putDouble(suma);
                String paddedData = String.format("%-10s", data).substring(0, 10);
                bb.position(12);
                bb.put(paddedData.getBytes());
                bb.put(22, (byte) (tip == TipTranzactie.CREDIT ? 0 : 1));
                bb.put(23, (byte) 0);

                fos.write(record);
            }
        }

        try (RandomAccessFile raf = new RandomAccessFile(OUTPUT_FILE, "rw")) {
            while (scanner.hasNext()) {
                String command = scanner.next();
                if (command.equals("READ")) {
                    int idx = scanner.nextInt();
                    printRecord(raf, idx);
                } else if (command.equals("UPDATE")) {
                    int idx = scanner.nextInt();
                    String statusStr = scanner.next();
                    byte statusByte = (byte) (statusStr.equals("PENDING") ? 0 : statusStr.equals("PROCESSED") ? 1 : 2);

                    raf.seek((long) idx * RECORD_SIZE + 23);
                    raf.write(statusByte);
                    System.out.println("Updated [" + idx + "]: " + statusStr);
                } else if (command.equals("PRINT_ALL")) {
                    long totalRecords = raf.length() / RECORD_SIZE;
                    for (int i = 0; i < totalRecords; i++) {
                        printRecord(raf, i);
                    }
                }
            }
        }
    }

    private static void printRecord(RandomAccessFile raf, int idx) throws IOException {
        if (idx * RECORD_SIZE >= raf.length()) return;

        byte[] record = new byte[RECORD_SIZE];
        raf.seek((long) idx * RECORD_SIZE);
        raf.readFully(record);

        ByteBuffer bb = ByteBuffer.wrap(record).order(ByteOrder.LITTLE_ENDIAN);
        int id = bb.getInt();
        double suma = bb.getDouble();

        byte[] dataBytes = new byte[10];
        bb.position(12);
        bb.get(dataBytes);
        String data = new String(dataBytes).trim();

        int tipCode = bb.get(22);
        String tip = (tipCode == 0) ? "CREDIT" : "DEBIT";

        int statusCode = bb.get(23);
        String status = (statusCode == 0) ? "PENDING" : (statusCode == 1) ? "PROCESSED" : "REJECTED";

        System.out.printf(Locale.US, "[%d] id=%d data=%s tip=%s suma=%.2f RON status=%s\n",
                idx, id, data, tip, suma, status);
    }
}