package Entities.Things;

import Entities.Things.Bank.Bank;
import Entities.Things.Customer.Account;
import Entities.Things.Customer.Card;

public class Transaction {
    private int id;
    private double amount;
    private final double commission = 600.0;
    private Card card;
    private Account account;
    private Bank bank;
}
