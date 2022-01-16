package App;

import Entities.Users.Bank.Clerk;
import Entities.Users.Bank.President;
import Entities.Users.User;
import Repositories.AccountsRep;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Executions {


    Connection connection = ConClass.getInstance().getConnection();
    Scanner scanner = new Scanner(System.in);
    AccountsRep ar = new AccountsRep(connection);

    public static void main(String[] args) {

    }

    public User login() throws SQLException {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        User user = ar.authentication(username, password);
        if (user instanceof Clerk) {

        } else if (user instanceof President) {

        }
    }
}
