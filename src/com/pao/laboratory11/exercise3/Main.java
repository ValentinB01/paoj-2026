package com.pao.laboratory11.exercise3;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Main {

    public static final class Transaction {
        private final int id;
        private final double amount;
        private final String country;
        private final String channel;

        public Transaction(int id, double amount, String country, String channel) {
            this.id = id;
            this.amount = amount;
            this.country = country;
            this.channel = channel;
        }

        public int getId() { return id; }
        public double getAmount() { return amount; }
        public String getCountry() { return country; }
        public String getChannel() { return channel; }

        @Override
        public String toString() {
            return String.format(Locale.US, "Tx[id=%d, amount=%.2f, country=%s, channel=%s]", id, amount, country, channel);
        }
    }

    public static final class Snapshot {
        private final Map<String, Long> countByCountry;
        private final Map<String, Long> countByChannel;
        private final double totalAmount;
        private final List<Transaction> topTransactions;

        public Snapshot(Map<String, Long> byCountry, Map<String, Long> byChannel, double totalAmount, List<Transaction> topTransactions) {
            this.countByCountry = Collections.unmodifiableMap(new HashMap<>(byCountry));
            this.countByChannel = Collections.unmodifiableMap(new HashMap<>(byChannel));
            this.totalAmount = totalAmount;
            this.topTransactions = List.copyOf(topTransactions);
        }

        public Map<String, Long> getCountByCountry() { return countByCountry; }
        public Map<String, Long> getCountByChannel() { return countByChannel; }
        public double getTotalAmount() { return totalAmount; }
        public List<Transaction> getTopTransactions() { return topTransactions; }
    }

    public static final class CustomCollectors {

        private static class Aggregator {
            Map<String, Long> byCountry = new HashMap<>();
            Map<String, Long> byChannel = new HashMap<>();
            double total = 0.0;
            List<Transaction> allTxs = new ArrayList<>();
        }

        public static Collector<Transaction, ?, Snapshot> toSnapshot(int topN) {
            return Collector.of(
                    Aggregator::new,
                    (agg, tx) -> {
                        agg.byCountry.merge(tx.getCountry(), 1L, Long::sum);
                        agg.byChannel.merge(tx.getChannel(), 1L, Long::sum);
                        agg.total += tx.getAmount();
                        agg.allTxs.add(tx);
                    },
                    (agg1, agg2) -> {
                        agg2.byCountry.forEach((k, v) -> agg1.byCountry.merge(k, v, Long::sum));
                        agg2.byChannel.forEach((k, v) -> agg1.byChannel.merge(k, v, Long::sum));
                        agg1.total += agg2.total;
                        agg1.allTxs.addAll(agg2.allTxs);
                        return agg1;
                    },
                    agg -> {
                        List<Transaction> top = agg.allTxs.stream()
                                .sorted(Comparator.comparingDouble(Transaction::getAmount).reversed()
                                        .thenComparingInt(Transaction::getId))
                                .limit(topN)
                                .collect(Collectors.toList());

                        return new Snapshot(agg.byCountry, agg.byChannel, agg.total, top);
                    }
            );
        }
    }

    public static void main(String[] args) {
        List<Transaction> data = List.of(
                new Transaction(1, 1500.50, "RO", "WEB"),
                new Transaction(2, 200.00, "UK", "APP"),
                new Transaction(3, 5000.00, "RO", "CRYPTO"),
                new Transaction(4, 50.00, "RO", "POS"),
                new Transaction(5, 5000.00, "FR", "WEB"),
                new Transaction(6, 1200.00, "UK", "APP")
        );

        Snapshot snap = data.stream().collect(CustomCollectors.toSnapshot(3));

        System.out.println("Top Tranzactii (din Snapshot)");
        snap.getTopTransactions().forEach(System.out::println);

        System.out.println("\n Tranzactii per Tara (Sortate Descrescator)");
        snap.getCountByCountry().entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry.comparingByKey()))
                .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));

        System.out.println("\n Tranzactii per Canal (Sortate Alfabetic)");
        snap.getCountByChannel().entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));

        System.out.println("\nTotal Procesat");
        System.out.printf(Locale.US, "Total Amount: %.2f%n", snap.getTotalAmount());
    }
}