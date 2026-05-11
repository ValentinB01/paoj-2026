package com.pao.laboratory10.exercise2;

import com.pao.laboratory10.exercise1.Tranzactie;
import com.pao.laboratory10.exercise1.TipTranzactie;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        if (!scanner.hasNextInt()) return;

        int n = scanner.nextInt();
        List<Tranzactie> lista = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int id = scanner.nextInt();
            double suma = scanner.nextDouble();
            String data = scanner.next();
            TipTranzactie tip = TipTranzactie.valueOf(scanner.next());
            lista.add(new Tranzactie(id, suma, data, tip));
        }

        while (scanner.hasNext()) {
            String comanda = scanner.next();

            switch (comanda) {
                case "UNIQUE_IDS":
                    LinkedHashSet<Integer> ids = new LinkedHashSet<>();
                    for (Tranzactie t : lista) {
                        ids.add(t.getId());
                    }
                    System.out.println("IDs unice (" + ids.size() + "): " + ids);
                    break;

                case "MONTHLY_REPORT":
                    Map<String, double[]> raport = new TreeMap<>();
                    for (Tranzactie t : lista) {
                        String luna = t.getData().substring(0, 7);
                        raport.putIfAbsent(luna, new double[2]);
                        if (t.getTip() == TipTranzactie.CREDIT) {
                            raport.get(luna)[0] += t.getSuma();
                        } else {
                            raport.get(luna)[1] += t.getSuma();
                        }
                    }
                    raport.forEach((luna, sume) ->
                            System.out.printf(Locale.US, "%s: CREDIT %.2f RON, DEBIT %.2f RON\n",
                                    luna, sume[0], sume[1]));
                    break;

                case "TOP":
                    int count = scanner.nextInt();
                    List<Tranzactie> copieTop = new ArrayList<>(lista);
                    copieTop.sort(Comparator.comparingDouble(Tranzactie::getSuma).reversed());
                    System.out.println("Top " + count + ":");
                    copieTop.subList(0, Math.min(count, copieTop.size()))
                            .forEach(System.out::println);
                    break;

                case "SORT_ASC":
                    lista.sort(Comparator.comparingDouble(Tranzactie::getSuma));
                    lista.forEach(System.out::println);
                    break;

                case "SORT_DESC":
                    lista.sort(Comparator.comparingDouble(Tranzactie::getSuma).reversed());
                    lista.forEach(System.out::println);
                    break;

                case "REVERSE":
                    Collections.reverse(lista);
                    lista.forEach(System.out::println);
                    break;

                case "MIN_MAX":
                    Tranzactie min = Collections.min(lista, Comparator.comparingDouble(Tranzactie::getSuma));
                    Tranzactie max = Collections.max(lista, Comparator.comparingDouble(Tranzactie::getSuma));
                    System.out.println("MIN: " + min);
                    System.out.println("MAX: " + max);
                    break;

                case "CME_DEMO":
                    try {
                        for (Tranzactie t : lista) {
                            lista.remove(t);
                        }
                    } catch (ConcurrentModificationException e) {
                        System.out.println("ConcurrentModificationException prins: modificare in iteratie detectata.");
                    }
                    break;
            }
        }
        scanner.close();
    }
}