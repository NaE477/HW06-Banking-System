package entities.things.customer;

import entities.things.bank.Bank;
import entities.things.bank.Branch;
import entities.users.Client;

public class Account {
    private int id;
    private String accountNumber;
    private Client client;
    private Bank bank;
    private Branch branch;
    private Double balance;

    public Account(int id, String accountNumber, Client client, Bank bank, Branch branch, Double balance) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.client = client;
        this.bank = bank;
        this.branch = branch;
        this.balance = balance;
    }
    public Account(int id){
        this.id = id;
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

    @Override
    public String toString() {
        return "ID: " + id +
                " Account Number: "  + accountNumber +
                " Owner: " + client.getUsername() +
                " Bank: " + bank.getName() +
                " Branch: " + branch.getBranch_name() +
                " Balance: " + balance.toString();
    }
}
