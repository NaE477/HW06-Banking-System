package Repositories;

import Entities.Users.User;
import Interfaces.Authentic;

import java.sql.Connection;

public class AccountsRep implements Authentic {
    Connection connection;
    public AccountsRep(Connection connection){
        this.connection = connection;
    }


    public User authentication(String username, String password) {
        return null;
    }
}
