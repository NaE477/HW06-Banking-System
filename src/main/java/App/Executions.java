package App;

import App.HandyClasses.ConClass;
import App.HandyClasses.Utilities;
import Entities.Things.Transaction;
import Entities.Users.Clerk;
import Entities.Users.President;
import Entities.Users.Client;
import Entities.Users.User;
import Services.ClerksService;
import Services.ClientsService;
import Services.PresidentsService;
import Services.TransactionsService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Executions {

    static Connection connection = ConClass.getInstance().getConnection();
    static Utilities utilities = new Utilities(connection);
    static Scanner scanner = new Scanner(System.in);
    static ClerksService clerksService = new ClerksService(connection);
    static ClientsService clientsService = new ClientsService(connection);
    static TransactionsService transactionsService = new TransactionsService(connection);
    static PresidentsService ps = new PresidentsService(connection);

    public static void main(String[] args) {
        System.out.println("Welcome to bank application.");
        User user = null;
        while (true) {
            user = entry();
            if (user instanceof President) {
                presidentSection((President) user);
            } else if (user instanceof Clerk) {
                clerkSection((Clerk) user);
            } else if (user instanceof Client) {

            } else if (user == null) {
                System.out.println("Wrong Username/Password!\nTry Again.");
            }
        }
    }

    public static User entry() {
        while (true) {
            System.out.print("Press L/l to Login,or E/e to Exit: ");
            String opt = scanner.nextLine().toUpperCase(Locale.ROOT);
            User user;
            if (opt.equals("L")) {
                return user = login();
            } else if (opt.equals("E")) {
                System.exit(1);
            } else {
                System.out.println("Only L/E is accepted.");
            }
        }
    }

    public static User login() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        if (clerksService.authentication(username, password)) {
            return clerksService.login(username);
        } else if (ps.authentication(username, password)) {
            return ps.login(username);
        } else if (clientsService.authentication(username, password)) {
            return clientsService.login(username);
        } else return null;
    }

    //=====================================================================
    //==========================President Section==========================
    //=====================================================================
    public static void presidentSection(President president) {
        try {
            System.out.println("Welcome " + president.getFirstname() + " " + president.getLastname());
            System.out.println("What do you wanna do?");
            label:
            while (true) {
                System.out.println("1-Create Clerk");
                System.out.println("2-View Clerks");
                System.out.println("3-Fire Clerk :))");
                System.out.println("4-View Transactions");
                System.out.println("5-View Profile");
                System.out.println("6-Exit");
                String opt = scanner.nextLine().toUpperCase(Locale.ROOT);
                switch (opt) {
                    case "1":
                        System.out.print("Firstname: ");
                        String firstname = scanner.nextLine();
                        System.out.print("Lastname: ");
                        String lastname = scanner.nextLine();
                        String username = utilities.usernameReceiver();
                        System.out.print("Password: ");
                        String password = scanner.nextLine();
                        System.out.print("Salary: ");
                        Double salary = utilities.doubleReceiver();
                        Integer newClerkId = clerksService.signup(
                                new Clerk(firstname, lastname, username, password, president.getBranch_id(), president.getUserId(), salary));
                        utilities.printGreen("Clerk was created with ID: " + newClerkId);
                        break;
                    case "2":
                        List<Clerk> clerkList = clerksService.findAll();
                        if (clerkList.size() > 0) {
                            for (Clerk clerk : clerkList) {
                                System.out.println(clerk.toString());
                            }
                        } else utilities.printYellow("No Employee has been employed yet.");
                        break;
                    case "3":
                        System.out.println("Enter Poor Clerk's ID: ");
                        int clerkId = utilities.intReceiver();
                        if (clerksService.fire(clerkId) != 0) {
                            utilities.printGreen("Employee was deleted.");
                        } else utilities.printRed("Employee does not exist!");
                        break;
                    case "4":
                        List<Transaction> transactions = transactionsService.findAll();
                        if (transactions.size() > 0) {
                            for (Transaction transaction : transactions) {
                                System.out.println(transaction.toString());
                            }
                        } else System.out.println("No transactions were made yet.");
                        break;
                    case "5":
                        utilities.printGreen(president.toString());
                        break;
                    case "6":
                        utilities.printGreen("Goodbye.");
                        break label;
                }
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    //=====================================================================
//============================Clerk Section===========================
//=====================================================================
    public static void clerkSection(Clerk clerk) {
        try {
            System.out.println("Welcome " + clerk.getFirstname() + " " + clerk.getLastname());
            System.out.println("What do you wanna do?");
            label:
            while (true) {
                System.out.println("1-Create Client");
                System.out.println("2-Delete Client");
                System.out.println("3-View Client");
                System.out.println("4-Open Account");
                System.out.println("5-Close Account");
                System.out.println("6-View Account");
                System.out.println("7-View Transactions");
                System.out.println("8-View Profile");
                String opt = scanner.nextLine();
                switch (opt) {
                    case "1":
                        System.out.print("First Name: ");
                        String firstName = scanner.nextLine();
                        System.out.print("Last Name: ");
                        String lastName = scanner.nextLine();
                        String username = utilities.usernameReceiver();
                        System.out.print("First Name: ");
                        String password = scanner.nextLine();
                        Integer newClientId = clientsService.signup(new Client(
                                0, firstName, lastName, username, password));
                        utilities.printGreen("Client was Created with ID: " + newClientId);
                        break;
                    case "2":
                        System.out.print("Enter Client ID: ");
                        int clientId = utilities.intReceiver();
                        if(clientsService.remove(clientId) != 0){
                            utilities.printGreen("Client with ID: " + clientId + " deleted.");
                        } else utilities.printRed("Client ID doesn't exist.");
                        break;
                    case "3":

                }
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
