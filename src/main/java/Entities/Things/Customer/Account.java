package Entities.Things.Customer;

import Entities.Things.Bank.Bank;
import Entities.Things.Bank.Branch;
import Entities.Users.Client.Client;

public class Account {
    private int id,clientId,bankId,branchId,accountNumber;
    private double balance;
    private Card card;
}
