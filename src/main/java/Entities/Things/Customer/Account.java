package Entities.Things.Customer;

import Entities.Things.Bank.Bank;
import Entities.Things.Bank.Branch;
import Entities.Users.Client.Client;

public class Account {
    private int id,accountNumber;
    private double balance;
    private Bank bank;
    private Branch branch;
    private Card card;
    private Client client;
}
