package Entities.Things;

import Entities.Things.Bank.Bank;
import Entities.Things.Customer.Account;
import Entities.Things.Customer.Card;

import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction {
    private int id,branch_id,bank_id,src_account_id,des_account_id;
    private double amount;
    private final double commission = 600.0;
    private Status status;
    private LocalDate transactionTime;

    public Transaction(int id, int branch_id, int bank_id, int src_account_id, int des_account_id, Double amount,LocalDate transactionTime) {
        this.id = id;
        this.branch_id = branch_id;
        this.bank_id = bank_id;
        this.src_account_id = src_account_id;
        this.des_account_id = des_account_id;
        this.amount = amount;
        this.status = Status.PENDING;
        this.transactionTime = transactionTime;
    }

    public Transaction(int id, int branch_id, int bank_id, int src_account_id, int des_account_id, Double amount,Status status,LocalDate localDate) {
        this.id = id;
        this.branch_id = branch_id;
        this.bank_id = bank_id;
        this.src_account_id = src_account_id;
        this.des_account_id = des_account_id;
        this.amount = amount;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(int branch_id) {
        this.branch_id = branch_id;
    }

    public int getBank_id() {
        return bank_id;
    }

    public void setBank_id(int bank_id) {
        this.bank_id = bank_id;
    }

    public int getSrc_account_id() {
        return src_account_id;
    }

    public void setSrc_account_id(int src_account_id) {
        this.src_account_id = src_account_id;
    }

    public int getDes_account_id() {
        return des_account_id;
    }

    public void setDes_account_id(int des_account_id) {
        this.des_account_id = des_account_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getCommission() {
        return commission;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDate transactionTime) {
        this.transactionTime = transactionTime;
    }

    @Override
    public String toString() {
        return "Transaction ID: " + id +
                " Branch ID: " + branch_id +
                " Bank ID: " + bank_id +
                " Source Account Number: " + src_account_id +
                " Destination Account Number: " + des_account_id +
                " Amount: " + amount +
                " Status: " + status;
    }
}
