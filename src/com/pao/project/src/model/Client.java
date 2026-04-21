package com.pao.project.src.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class Client {
    private static int contorId = 1;

    private final int idClient;
    private String nume;
    private String prenume;
    private final String cnp;
    private List<Account> conturi;

    public Client(String nume, String prenume, String cnp){
        this.idClient = contorId++;
        this.nume = nume;
        this.prenume = prenume;
        this.cnp = cnp;
        this.conturi = new ArrayList<>();
    }

    public String getNume(){return nume;}
    public void setNume(String nume){this.nume = nume;}

    public String getPrenume(){return prenume;}
    public void setPrenume(String prenume){this.prenume = prenume;}

    public String getCnp(){return cnp;}

    public List<Account> getConturi(){return new ArrayList<>(conturi);}

    public int getIdClient(){return idClient; }

    public void adaugaCont(Account cont)
    {
        this.conturi.add(cont);
    }

    public void stergeCont(Account cont) {
        this.conturi.remove(cont);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(cnp, client.cnp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cnp);
    }

    @Override
    public String toString(){
        return "Client: " + nume + " " + prenume + " | CNP: " + cnp + " | Conturi: " + conturi.size();
    }
}
