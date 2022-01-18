package App;

import App.HandyClasses.ConClass;
import Entities.Users.User;
import Services.ClerksService;

import java.sql.Connection;
import java.util.Locale;
import java.util.Scanner;

public class Executions {


    static Connection connection = ConClass.getInstance().getConnection();
    static Scanner scanner = new Scanner(System.in);
    static ClerksService cs = new ClerksService(connection);

    public static void main(String[] args) {
        while (true){
            System.out.println("Welcome to bank application.");
            System.out.print("Press L/l to Login or S/s to Sign up,or E/e to Exit: ");
            String opt = scanner.nextLine().toUpperCase(Locale.ROOT);
            switch (opt){
                case "L":
                    User user = login();
            }
        }
    }

    public static User login() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        if(cs.authentication(username,password)){
            return cs.login(username);
        }
        else return null;
    }
}
