package App;

import App.HandyClasses.ConClass;
import App.HandyClasses.Utilities;
import Entities.Things.Bank.Branch;
import Entities.Things.Customer.Account;
import Entities.Things.Customer.Card;
import Entities.Users.Clerk;
import Entities.Users.President;
import Entities.Users.Client;
import Entities.Users.User;
import Services.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.YearMonth;
import java.util.*;

public class Executions {

    static Connection connection = ConClass.getInstance().getConnection();
    static Utilities utilities = new Utilities(connection);
    static Scanner scanner = new Scanner(System.in);
    static ClerksService clerksService = new ClerksService(connection);
    static ClientsService clientsService = new ClientsService(connection);
    static TransactionsService transactionsService = new TransactionsService(connection);
    static PresidentsService presidentsService = new PresidentsService(connection);
    static AccountsService accountsService = new AccountsService(connection);
    static BanksService banksService = new BanksService(connection);
    static BranchesService branchesService = new BranchesService(connection);
    static CardsService cardsService = new CardsService(connection);

    public static void main(String[] args) {
        System.out.println("Welcome to bank application.");
        User user;
        while (true) {
            user = entry();
            if (user instanceof President) {
                presidentSection((President) user);
            } else if (user instanceof Clerk) {
                clerkSection((Clerk) user);
            } else if (user instanceof Client) {
                clientSection((Client) user);
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
        } else if (presidentsService.authentication(username, password)) {
            return presidentsService.login(username);
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
                        List<Clerk> clerkList = clerksService.findAllByBranch(president.getBranch_id());
                        utilities.iterateThrough(clerkList);
                        break;
                    case "3":
                        System.out.println("Enter Poor Clerk's ID: ");
                        int clerkId = utilities.intReceiver();
                        if (clerksService.fire(clerkId) != 0) {
                            utilities.printGreen("Employee was deleted.");
                        } else utilities.printRed("Employee does not exist!");
                        break;
                    case "4":
                        List<Object> transactions = transactionsService.findAllByBranch(president.getBranch_id());
                        utilities.iterateThrough(transactions);
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
    //=============================Clerk Section===========================
    //=====================================================================
    public static void clerkSection(Clerk clerk) {
        //getting the branch
        President president = presidentsService.findById(clerk.getPresident_id());
        Branch thisBranch = branchesService.findById(president.getBranch_id());
        int bankId = thisBranch.getBank().getId();
        try {
            System.out.println("Welcome " + clerk.getFirstname() + " " + clerk.getLastname());
            System.out.println("What do you wanna do?");
            label:
            while (true) {
                System.out.println("1-Create Client");
                System.out.println("2-Remove Client");
                System.out.println("3-View Clients in this Branch");
                System.out.println("4-Open Account");
                System.out.println("5-Close Account");
                System.out.println("6-View Accounts in this Branch");
                System.out.println("7-Create Card for an Account");
                System.out.println("8-View Transactions");
                System.out.println("9-View Profile");
                System.out.println("10-Exit");
                String opt = scanner.nextLine();
                switch (opt) {
                    case "1":
                        System.out.print("First Name: ");
                        String firstName = scanner.nextLine();
                        System.out.print("Last Name: ");
                        String lastName = scanner.nextLine();
                        String username = utilities.usernameReceiver();
                        System.out.print("Password: ");
                        String password = scanner.nextLine();
                        Integer newClientId = clientsService.signup(new Client(
                                0, firstName, lastName, username, password));
                        utilities.printGreen("Client was Created with ID: " + newClientId);
                        break;
                    case "2":
                        System.out.print("Enter Client ID: ");
                        int clientId = utilities.intReceiver();
                        if (clientsService.remove(clientId) != 0) {
                            if (clientsService.existsInBranch(president.getBranch_id(), clientId))
                                utilities.printGreen("Client with ID: " + clientId + " deleted.");
                            else utilities.printRed("ID INVALID");
                        } else utilities.printRed("ID INVALID");
                        break;
                    case "3":
                        List<Client> clients = clientsService.findAllByBranch(clerk.getBranch_id());
                        utilities.iterateThrough(clients);
                        break;
                    case "4":
                        System.out.print("Enter Client's ID: ");
                        int newAccountClientId = utilities.intReceiver();
                        if (clientsService.existsInBranch(president.getBranch_id(), newAccountClientId)) {
                            Account newAccount = new Account(0,
                                    utilities.accountNumberMaker(),
                                    clientsService.findById(newAccountClientId),
                                    banksService.findById(bankId),
                                    thisBranch, 20.0
                            );
                            accountsService.open(newAccount);
                        } else utilities.printRed("ID INVALID.");
                        break;
                    case "5":
                        System.out.print("Enter Account's ID: ");
                        int accountId = utilities.intReceiver();
                        if (accountsService.existsInBranch(president.getBranch_id(), accountId)) {
                            Account accountToCloseObj = new Account(accountId);
                            accountsService.closeAccount(accountToCloseObj);
                            utilities.printRed("Account deleted successfully.");
                            Integer deletedCardsIds = cardsService.removeByAccountAndBranch(accountId, clerk.getBranch_id());
                            utilities.printGreen("Account deleted successfully and Card with ID: " + deletedCardsIds  + " Been deleted.");
                        } else utilities.printRed("ID INVALID");
                        break;
                    case "6":
                        List<Account> accounts = accountsService.findAllByBranch(clerk.getBranch_id());
                        utilities.iterateThrough(accounts);
                        break;
                    case "7":
                        System.out.print("Account ID: ");
                        int accountIdForCard = utilities.intReceiver();
                        if (accountsService.exists(accountIdForCard)) {
                            Card card = new Card(0,
                                    utilities.cvv2Generator(),
                                    0, 0,
                                    accountsService.findById(accountIdForCard),
                                    YearMonth.now().plusYears(5),
                                    utilities.cardNumberGenerator()
                            );
                            cardsService.createNew(card);
                        } else utilities.printRed("ID INVALID.");
                        break;
                    case "8":
                        List<Object> transactions = transactionsService.findAllByBranch(clerk.getBranch_id());
                        utilities.iterateThrough(transactions);
                        break;
                    case "9":
                        utilities.printGreen(clerk.toString());
                        break;
                    case "10":
                        utilities.printGreen("Goodbye");
                        break label;
                    default:
                        utilities.printRed("Choose one of the options!");
                }
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    //=====================================================================
    //=============================Client Section===========================
    //=====================================================================
    public static void clientSection(Client client) {
        try {

            System.out.println("Welcome " + client.getFirstname() + " " + client.getLastname());
            System.out.println("What do you wanna do?");
            label:
            while (true) {
                System.out.println("1-View Accounts");
                System.out.println("2-View Cards");
                System.out.println("3-Remove Account");
                System.out.println("4-Remove Card");
                System.out.println("5-Card Section");
                System.out.println("6-Exit");
                String opt = scanner.nextLine();
                switch (opt) {
                    case "1":
                        List<Account> clientsAccounts = accountsService.findAllByClient(client.getUserId());
                        utilities.iterateThrough(clientsAccounts);
                        break;
                    case "2":
                        List<Card> cards = cardsService.findAllByClient(client.getUserId());
                        utilities.iterateThrough(cards);
                        break;
                    case "3":
                        System.out.println("Enter Account ID: ");
                        int accountId = utilities.intReceiver();
                        Account account = new Account(accountId);
                        if (accountsService.existsForClient(accountId)) {
                            accountsService.closeAccount(account);
                            cardsService.removeByAccount(accountId);
                            Card deletedCard = cardsService.findById(accountId);
                            cardsService.removeByAccount(accountId);
                            utilities.printGreen("Account Closed,Card with Number: " + deletedCard.getNumber() + " Been Deleted with it.");
                        } else System.out.println("ID INVALID");
                        break;
                    case "4":
                        System.out.print("Enter Card Number or Card ID: ");
                        String cardOrId = scanner.nextLine();
                        if (cardOrId.length() != 12) {
                            Integer cardId = Integer.parseInt(cardOrId);
                            if (cardsService.existsForClient(client.getUserId(),cardId)){
                                String deletedCardNumber = cardsService.removeById(cardId);
                                utilities.printGreen("Card with Number: " + deletedCardNumber + " Been Deleted.");
                            } else utilities.printRed("INVALID ID/CARD NUMBER");
                        } else {
                            Integer cardId = cardsService.findByNumber(cardOrId).getId();
                            if (cardsService.existsForClient(client.getUserId(),cardId)) {
                                Integer deletedCardId = cardsService.removeByCardNumber(cardOrId);
                                utilities.printGreen("Card with ID: " + deletedCardId + " Been Deleted");
                            }
                        }
                        break;
                    case "5":
                        List<Card> clientCards = cardsService.findAllByClient(client.getUserId());
                        utilities.iterateThrough(clientCards);
                        System.out.println("Choose Card ID you want to Enter: ");
                        Integer cardId = utilities.intReceiver();
                        if(cardsService.existsForClient(client.getUserId(),cardId)){
                            Card card = cardsService.findById(cardId);

                        }
                        break;
                    case "6":
                        utilities.printGreen("Goodbye");
                        break label;
                    default:
                        try {
                            utilities.printRed("Choose an Option!");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
