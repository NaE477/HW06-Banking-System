package Entities.Users.Bank;

import Entities.Things.Bank.Branch;
import Entities.Users.User;

import java.util.List;

public class President extends User {
    private Branch branch;
    private List<Clerk> clerks;
    private double salary;
}
