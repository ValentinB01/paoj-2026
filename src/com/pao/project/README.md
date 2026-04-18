# Sistem Bancar — Proiect PAOJ 2026

> **Tema:** Aplicatie bancara (conturi, carduri, tranzactii, extrase de cont)

---

## 1. Definirea sistemului

### 1.1 — Actiuni / interogari posibile in sistem

1. **Inregistreaza un client nou** — adauga un client in baza de date a bancii
2. **Deschide un cont bancar** (Curent sau Economii) — asociat unui client existent
3. **Depunere numerar in cont** — adauga fonduri intr-un cont pe baza IBAN-ului
4. **Retragere numerar din cont** — retrage fonduri, cu validare sold/overdraft
5. **Transfer intre conturi** — muta bani dintr-un cont in altul
6. **Afiseaza extrasul de cont** — listeaza toate tranzactiile unui cont
7. **Calculeaza averea totala a unui client** — insumeaza soldurile tuturor conturilor
8. **Emite un card nou** (Debit sau Credit) — asociat unui cont sau independent
9. **Afiseaza toate conturile bancii (sortate dupa sold)** — raport general
10. **Cauta un client dupa CNP** — interogare rapida in baza de date

### 1.2 — Tipuri de obiecte din domeniu

| # | Clasa              | Descriere                                      |
|---|--------------------|-------------------------------------------------|
| 1 | `Account`          | Clasa abstracta — cont bancar generic           |
| 2 | `CurrentAccount`   | Cont curent (extinde Account, cu overdraft)      |
| 3 | `SavingsAccount`   | Cont de economii (extinde Account, cu dobanda)   |
| 4 | `Client`           | Clientul bancii (detine conturi)                 |
| 5 | `Transaction`      | Inregistrare imutabila a unei tranzactii         |
| 6 | `Card`             | Clasa abstracta — card bancar generic            |
| 7 | `DebitCard`        | Card de debit (extinde Card)                     |
| 8 | `CreditCard`       | Card de credit (extinde Card)                    |
| 9 | `Loan`             | Credit/imprumut bancar                           |
| 10| `BankBranch`       | Sucursala bancara                                |

---

## 2. Implementare Java

### 2.1 — Clase si OOP

- [x] **10 clase** de domeniu (Account, CurrentAccount, SavingsAccount, Client, Transaction, Card, DebitCard, CreditCard, Loan, BankBranch)
- [x] Atribute `private` / `protected`, cu getteri si setteri
- [x] `toString()`, `equals()`, `hashCode()` suprascrise in clasele `Client` si `Transaction`
- [x] **2 ierarhii de mostenire:**
  - `Account` (abstract) → `CurrentAccount`, `SavingsAccount`
  - `Card` (abstract) → `DebitCard`, `CreditCard`
- [x] **2 clase abstracte:** `Account` (cu metoda abstracta `afiseazaDetaliiSpecific()`) si `Card` (cu `afiseazaTipCard()`)
- [x] **Clasa imutabila:** `Transaction` — toate atributele `final`, fara setteri, initializate complet in constructor
- [x] **2 exceptii custom:** `ClientNotFoundException`, `InsufficientFundsException` — aruncate si tratate in servicii si Main

### 2.2 — Colectii

- [x] **3 tipuri de colectii:** `List` (tranzactii, conturi client), `TreeSet` (conturi sortate), `Map` (indexare clienti/carduri)
- [x] **Colectie sortata:** `TreeSet<Account>` — prin implementarea `Comparable<Account>` (comparare dupa sold)
- [x] **Map pentru indexare:** `Map<String, Client>` (clienti indexati dupa CNP), `Map<String, Card>` (carduri indexate dupa numar)

### 2.3 — Servicii

- [x] **2 clase de serviciu:** `BankingService` (gestioneaza clienti si conturi) si `CardService` (gestioneaza carduri)
- [x] Ambele implementate ca **Singleton** (constructor privat + `getInstance()`)
- [x] Operatii CRUD: adauga, sterge, cauta dupa id/nume, listeaza toate
- [x] Clasa `Main` apeleaza toate cele **10 actiuni** prin meniu interactiv

### 2.4 — Organizare si calitate

- [x] Cod organizat in sub-pachete:
  ```
  com.pao.project.src/
  ├── model/        ← Account, Client, Card, Transaction, Loan, BankBranch, ...
  ├── service/      ← BankingService, CardService
  ├── exception/    ← ClientNotFoundException, InsufficientFundsException
  └── Main.java
  ```
- [x] Fara cod duplicat — logica comuna in clasele de baza (`Account`, `Card`)
- [x] Fara `NullPointerException` — validari in servicii + exceptii custom aruncate la cautari esuate

