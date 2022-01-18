package Entities.Users;

import Entities.Things.Customer.Account;
import Entities.Users.User;

import java.util.List;

public class Client extends User {
    private List<Integer> accountIds;

    public Client(int userId, String firstname, String lastname,
                  String username, String password,
                  List<Integer> accountIds) {
        super(userId, firstname, lastname, username, password);
        this.accountIds = accountIds;
    }
    public Client(int userId, String firstname, String lastname,
                  String username, String password){
        super(userId, firstname, lastname, username, password);
    }
    public Client(int userId,String username,String password){
        super(userId,username,password);
    }

    public List<Integer> getAccountIds() {
        return accountIds;
    }

    public void setAccountIds(List<Integer> accountIds) {
        this.accountIds = accountIds;
    }
}
