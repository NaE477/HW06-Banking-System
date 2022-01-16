package Entities.Things.Bank;

import Entities.Things.Customer.Account;
import Entities.Users.Bank.Clerk;
import Entities.Users.Bank.President;

import java.util.List;

public class Branch {
    private Bank bank;
    private President president;
    private List<Clerk> clerks;
    private List<Account> accounts;
}
