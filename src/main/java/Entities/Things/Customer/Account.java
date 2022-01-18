package Entities.Things.Customer;

import Entities.Things.Bank.Bank;
import Entities.Things.Bank.Branch;
import Entities.Users.Client;

public class Account {
    private int id;
    private String accountNumber;
    private Client client;
    private Bank bank;
    private Branch branch;
    private Double balance;
    private Card card;

    public Account(int id, String accountNumber, Client client, Bank bank, Branch branch, Double balance, Card card) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.client = client;
        this.bank = bank;
        this.branch = branch;
        this.balance = balance;
        this.card = card;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
