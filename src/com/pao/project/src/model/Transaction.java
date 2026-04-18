package com.pao.project.src.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Transaction {
    private static int contorId = 1000;

    private final int idTranzactie;
    private final double suma;
    private final LocalDateTime dataOra;
    private final String tip;
    private final String descriere;

    public Transaction(double suma, String tip, String descriere) {
        this.idTranzactie = contorId++;
        this.suma = suma;
        this.tip = tip;
        this.descriere = descriere;
        this.dataOra = LocalDateTime.now();
    }

    public int getIdTranzactie() { return idTranzactie; }
    public double getSuma() { return suma; }
    public String getTip() { return tip; }
    public String getDescriere() { return descriere; }

    public String getDataOraFormatata() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return dataOra.format(formatter);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return idTranzactie == that.idTranzactie;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTranzactie);
    }

    @Override
    public String toString() {
        return String.format("[%s] ID: %d | Tip: %s | Suma: %.2f | %s",
                getDataOraFormatata(), idTranzactie, tip, suma, descriere);
    }
}
