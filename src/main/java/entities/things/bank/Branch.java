package entities.things.bank;

import entities.things.customer.Account;
import entities.users.Clerk;
import entities.users.President;

import java.util.List;

public class Branch {
    private int id;
    private Bank bank;
    private President president;
    private String branch_name;
    private List<Clerk> clerks;
    private List<Account> accounts;

    public Branch(int id, Bank bank,String branch_name) {
        this.id = id;
        this.bank = bank;
        this.president = president;
        this.branch_name = branch_name;
    }

    public Branch(int branchId) {
        this.id = branchId;
    }

    public Branch(int branch_id, String branch_name) {
        this.branch_name = branch_name;
        this.id = branch_id;
    }

    public President getPresident() {
        return president;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank_id(Bank bank) {
        this.bank = bank;
    }

    public President president() {
        return president;
    }

    public void setPresident(President president) {
        this.president = president;
    }

    public List<Clerk> getClerks() {
        return clerks;
    }

    public void setClerks(List<Clerk> clerks) {
        this.clerks = clerks;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String bank_name) {
        this.branch_name = bank_name;
    }
}
