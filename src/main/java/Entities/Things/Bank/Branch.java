package Entities.Things.Bank;

import Entities.Things.Customer.Account;
import Entities.Users.Bank.Clerk;
import Entities.Users.Bank.President;

import java.util.List;

public class Branch {
    private int id;
    private int bank_id;
    private int president_id;
    private String branch_name;
    private List<Clerk> clerks;
    private List<Account> accounts;

    public Branch(int id, int bank_id, int president_id,String bank_name) {
        this.id = id;
        this.bank_id = bank_id;
        this.president_id = president_id;
        this.branch_name = bank_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBank_id() {
        return bank_id;
    }

    public void setBank_id(int bank_id) {
        this.bank_id = bank_id;
    }

    public int getPresident_id() {
        return president_id;
    }

    public void setPresident_id(int president_id) {
        this.president_id = president_id;
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
