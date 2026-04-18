package com.pao.project.src.service;

import com.pao.project.src.model.*;

import java.util.*;

public class CardService {
    private static CardService instance;

    private Map<String, Card> carduri = new HashMap<>();

    private CardService() {}

    public static CardService getInstance() {
        if (instance == null) {
            instance = new CardService();
        }
        return instance;
    }

    public void emiteCard(Card card) {
        carduri.put(card.getNumarCard(), card);
        System.out.println("Card emis cu succes: " + card.getNumarCard());
        card.afiseazaTipCard();
    }

    public void stergeCard(String numarCard) {
        Card card = carduri.remove(numarCard);
        if (card != null) {
            System.out.println("Cardul " + numarCard + " a fost sters.");
        } else {
            System.out.println("Cardul cu numarul " + numarCard + " nu exista.");
        }
    }

    public Card cautaCard(String numarCard) {
        return carduri.get(numarCard);
    }

    public List<Card> listeazaToateCardurile() {
        return new ArrayList<>(carduri.values());
    }

    public void blocheazaCard(String numarCard) {
        Card card = carduri.get(numarCard);
        if (card != null) {
            card.setEsteActiv(false);
            System.out.println("Cardul " + numarCard + " a fost blocat.");
        } else {
            System.out.println("Cardul nu a fost gasit.");
        }
    }

    public void deblocheazaCard(String numarCard) {
        Card card = carduri.get(numarCard);
        if (card != null) {
            card.setEsteActiv(true);
            System.out.println("Cardul " + numarCard + " a fost deblocat.");
        } else {
            System.out.println("Cardul nu a fost gasit.");
        }
    }
}
