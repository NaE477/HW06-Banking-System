package Entities.Users;

import Entities.Things.Customer.Account;
import Entities.Users.User;

import java.util.List;

public class Client extends User {

    public Client(int userId, String firstname, String lastname,
                  String username, String password){
        super(userId, firstname, lastname, username, password);
    }
    public Client(int userId,String username,String password){
        super(userId,username,password);
    }



    @Override
    public String toString() {
        return "Client ID: " + getUserId() +
                " Full Name: "+ getFirstname() + " " + getLastname();
    }
}
