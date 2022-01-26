package app;

import app.handyClasses.ConClass;
import app.handyClasses.Utilities;
import entities.things.bank.Bank;
import entities.things.bank.Branch;
import entities.things.customer.Account;
import entities.things.customer.Card;
import entities.things.customer.CardStatus;
import entities.things.Transaction;
import entities.things.TransactionStatus;
import entities.users.Clerk;
import entities.users.President;
import entities.users.Client;
import entities.users.User;
import services.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
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

    public static void main(String[] args) throws SQLException {
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

    public static User entry() throws SQLException {
        while (true) {
            System.out.print("Press L/l to Login,or E/e to Exit: ");
            String opt = scanner.nextLine().toUpperCase(Locale.ROOT);
            if (opt.equals("L")) {
                return login();
            } else if (opt.equals("E")) {
                System.exit(1);
            } else {
                System.out.println("Only L/E is accepted.");
            }
        }
    }

    public static User login() throws SQLException {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        if (username.equals("admin") && password.equals("admin")) {
            adminSection();
        }
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
                            if (clerksService.existsForPresident(clerkId, president.getUserId())) {
                                utilities.printGreen("Employee was deleted.");
                            } else utilities.printRed("Employee does not exist in branch.");
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
        Bank thisBank = banksService.findById(thisBranch.getBank().getId());
        try {
            utilities.printGreen("Welcome " + clerk.getFirstname() + " " + clerk.getLastname());
            System.out.println("What do you wanna do?");
            label:
            while (true) {
                System.out.println("1-Create Client");
                System.out.println("2-View Clients in this Branch");
                System.out.println("3-View All Clients ( ͡❛ ͜ʖ ͡❛)");
                System.out.println("4-Open Account");
                System.out.println("5-Close Account");
                System.out.println("6-View Accounts in this Branch");
                System.out.println("7-Create Card for an Account");
                System.out.println("8-Change Password For Card and Open It");
                System.out.println("9-View Transactions");
                System.out.println("10-View Profile");
                System.out.println("11-Exit");
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
                        Client newClient = new Client(
                                0, firstName, lastName, username, password);
                        Integer newClientId = clientsService.signup(newClient);
                        newClient.setUserId(newClientId);
                        Account newAccountForClient = new Account(0, utilities.accountNumberMaker(), newClient, thisBank, thisBranch, 20.0);
                        Integer newAccountIDForClient = accountsService.open(newAccountForClient);
                        utilities.printGreen("Client was Created with ID: " + newClientId + " and Account ID: " + newAccountIDForClient);
                        break;
                    case "2":
                        List<Client> clients = clientsService.findAllByBranch(thisBranch.getId());
                        utilities.iterateThrough(clients);
                        break;
                    case "3":
                        List<Client> allClients = clientsService.findAll();
                        utilities.iterateThrough(allClients);
                        break;
                    case "4":
                        System.out.print("Enter Client's ID: ");
                        int newAccountsClientId = utilities.intReceiver();
                        if (clientsService.exists(newAccountsClientId)) {
                            if (!accountsService.clientExistsInBranch(newAccountsClientId, thisBranch.getId())) {
                                Account newAccount = new Account(0,
                                        utilities.accountNumberMaker(),
                                        clientsService.findById(newAccountsClientId),
                                        thisBank,
                                        thisBranch, 20000.0
                                );
                                Integer newAccountId = accountsService.open(newAccount);
                                utilities.printGreen("Account was created with ID: " + newAccountId + " And Account Number: " + newAccount.getAccountNumber());
                            } else utilities.printRed("Client Already Exists In Branch.");
                        } else utilities.printRed("ID does not exist.");
                        break;
                    case "5":
                        System.out.print("Enter Account's ID: ");
                        int accountId = utilities.intReceiver();
                        if (accountsService.existsInBranch(thisBranch.getId(), accountId)) {
                            Account accountToCloseObj = accountsService.findById(accountId);
                            if (cardsService.hasCard(accountId)) {
                                Integer deletedCardsIds = cardsService.removeByAccountAndBranch(accountId, clerk.getBranch_id());
                                accountsService.closeAccount(accountToCloseObj);
                                utilities.printRed("Account deleted successfully and Card with ID: " + deletedCardsIds + " Been deleted.");
                            }
                            accountsService.closeAccount(accountToCloseObj);
                            utilities.printRed("Account deleted successfully and had no Card");
                        } else utilities.printRed("Account does not exist in this branch.");
                        break;
                    case "6":
                        List<Account> accounts = accountsService.findAllByBranch(thisBranch.getId());
                        utilities.iterateThrough(accounts);
                        break;
                    case "7":
                        System.out.print("Account ID: ");
                        int accountIdForCard = utilities.intReceiver();
                        if (accountsService.exists(accountIdForCard)
                                && accountsService.existsInBranch(thisBranch.getId(), accountIdForCard)) {
                            Card card = new Card(0,
                                    utilities.cvv2Generator(),
                                    1234, 0,
                                    accountsService.findById(accountIdForCard),
                                    YearMonth.now().plusYears(5),
                                    utilities.cardNumberGenerator()
                            );
                            Integer newCardID = cardsService.createNew(card);
                            card.setId(newCardID);
                            utilities.printGreen("Card was created with" +
                                    "\n-ID: " + card.getId() +
                                    "\n- CARD NUMBER: " + card.getNumber() +
                                    "\n- First Pass: " + card.getFirstPass() +
                                    "\n CVV2: " + card.getCvv2() +
                                    "\n EXP. Date: " + card.getExpDate().toString());
                        } else utilities.printRed("ID INVALID.");
                        break;
                    case "8":
                        System.out.print("Enter Card ID or Card Number: ");
                        Card card;
                        String numberOrId = scanner.nextLine();
                        if (numberOrId.length() != 12) {
                            int cardId = Integer.parseInt(numberOrId);
                            if (cardsService.exists(cardId)) {
                                card = cardsService.findById(cardId);
                            } else {
                                utilities.printRed("Card ID Invalid.");
                                break;
                            }
                        } else {
                            if (cardsService.cardNumExists(numberOrId)) {
                                card = cardsService.findByNumber(numberOrId);
                            } else {
                                utilities.printRed("Card Number Invalid");
                                break;
                            }
                        }
                        if (accountsService.existsInBranch(thisBranch.getId(), card.getAccount().getId())) {
                            System.out.print("First Password: ");
                            int firstPass = utilities.fourDigitsReceiver();
                            System.out.print("Second Password: ");
                            int secondPass = utilities.sixDigitReceiver();
                            card.setFirstPass(firstPass);
                            card.setSecondPass(secondPass);
                            card.setCardStatus(CardStatus.OPEN);
                            cardsService.modifyPassAndStatus(card);
                            utilities.printGreen("Card Password and Status are now changed and can be given to Client.");
                        } else utilities.printRed("Card does not exist in this branch");
                        break;
                    case "9":
                        List<Object> transactions = transactionsService.findAllByBranch(clerk.getBranch_id());
                        utilities.iterateThrough(transactions);
                        break;
                    case "10":
                        utilities.printGreen(clerk.toString());
                        break;
                    case "11":
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
                System.out.println("5-View Transactions");
                System.out.println("6-Card Section");
                System.out.println("7-View Profile");
                System.out.println("8-Exit");
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
                        if (accountsService.existsForClient(client.getUserId(), accountId)) {
                            Account account = accountsService.findById(accountId);
                            Card deletedCard = cardsService.findByAccount(accountId);
                            if (deletedCard != null) {
                                cardsService.removeByAccountId(accountId);
                                utilities.printGreen("Account Closed,Card with Number: " + deletedCard.getNumber() + " Been Deleted with it.");
                            }
                            accountsService.closeAccount(account);
                            utilities.printGreen("Account Closed");
                        } else utilities.printRed("ID INVALID");
                        break;
                    case "4":
                        System.out.print("Enter Card Number or Card ID: ");
                        String cardOrId = scanner.nextLine();
                        if (cardOrId.length() != 12) {
                            int cardId;
                            try {
                                cardId = Integer.parseInt(cardOrId);
                                if (cardsService.existsForClient(client.getUserId(), cardId)) {
                                    String deletedCardNumber = cardsService.removeById(cardId);
                                    utilities.printGreen("Card with Number: " + deletedCardNumber + " Been Deleted.");
                                } else utilities.printRed("INVALID ID/CARD NUMBER");
                            } catch (NumberFormatException e) {
                                System.out.println("Only numbers allowed here.");
                            }
                        } else {
                            Integer cardId = cardsService.findByNumber(cardOrId).getId();
                            if (cardsService.existsForClient(client.getUserId(), cardId)) {
                                Integer deletedCardId = cardsService.removeByCardNumber(cardOrId);
                                utilities.printGreen("Card with ID: " + deletedCardId + " Been Deleted");
                            }
                        }
                        break;
                    case "5":
                        List<Transaction> transactions = transactionsService.findAllByClient(client.getUserId());
                        utilities.iterateThrough(transactions);
                        break;
                    case "6":
                        List<Card> clientCards = cardsService.findAllByClient(client.getUserId());
                        utilities.iterateThrough(clientCards);
                        System.out.println("Enter Card Number you want to Enter: ");
                        String cardNumber = scanner.nextLine();
                        Card card;
                        if (cardNumber.length() != 12) {
                            utilities.printRed("Enter a 12 digit Card Number");
                            break;
                        } else {
                            if (cardsService.cardNumExists(cardNumber)) {
                                card = cardsService.findByNumber(cardNumber);
                            } else {
                                System.out.println("Card Number Invalid");
                                break;
                            }
                        }
                        if (cardsService.cardBelongsToClient(card, client)) {
                            cardSection(card);
                        } else utilities.printRed("Card Number Invalid");
                        break;
                    case "7":
                        utilities.printGreen(client.toString());
                        break;
                    case "8":
                        utilities.printGreen("Goodbye");
                        break label;
                    default:
                        utilities.printRed("Choose an Option!");
                }
            }
        } catch (InterruptedException | NumberFormatException e) {
            e.printStackTrace();
        }
    }


    //=====================================================================
    //==============================Card Section===========================
    //=====================================================================

    public static void cardSection(Card card) {
        try {
            int count = 0;
            label:
            while (true) {
                System.out.print("Enter First Pass for Card: ");
                int firstPassForAuth = utilities.fourDigitsReceiver();
                if (cardsService.firstPassAuthenticate(card.getId(), firstPassForAuth)) {
                    if (card.getSecondPass() == -11111) {
                        System.out.println("Please set your passwords: ");
                        System.out.print("First Password: ");
                        Integer firstPass = utilities.fourDigitsReceiver();
                        System.out.print("Second Password: ");
                        Integer secondPass = utilities.sixDigitReceiver();
                        card.setFirstPass(firstPass);
                        card.setSecondPass(secondPass);
                        card.setCardStatus(CardStatus.OPEN);
                        cardsService.modifyPassAndStatus(card);
                        utilities.printGreen("Card Password Changed and is Open now.");
                    }
                    if (card.getCardStatus().equals(CardStatus.BLOCKED)) {
                        utilities.printRed("Your Card is Blocked,an employee can open it.");
                        break;
                    } else {
                        Account account = accountsService.findById(card.getAccount().getId());
                        Bank bank = banksService.findById(account.getBank().getId());
                        Branch branch = branchesService.findById(account.getBranch().getId());
                        Client client = clientsService.findById(account.getClient().getUserId());
                        while (true) {
                            System.out.println("Welcome to Card Section,Everything is ok now.");
                            System.out.println("What do you want to do?");
                            System.out.println("1-Make a Transition");
                            System.out.println("2-Change Passwords");
                            System.out.println("3-View Transactions");
                            System.out.println("4-View Transactions By Date");
                            System.out.println("5-Exit");
                            String opt = scanner.nextLine();
                            switch (opt) {
                                case "1":
                                    transactionSection(card);
                                    break;
                                case "2":
                                    System.out.print("New First Password: ");
                                    int firstPass = utilities.fourDigitsReceiver();
                                    System.out.print("New Second Password: ");
                                    int secondPass = utilities.sixDigitReceiver();
                                    card.setFirstPass(firstPass);
                                    card.setSecondPass(secondPass);
                                    cardsService.modifyPassAndStatus(card);
                                    utilities.printGreen("Passwords were changed successfully.");
                                    break;
                                case "3":
                                    List<Transaction> transactions = transactionsService.findAllByAccount(card.getAccount().getId());
                                    utilities.iterateThrough(transactions);
                                    break;
                                case "4":
                                    int month = utilities.monthReceiver();
                                    int day = utilities.dayReceiver(month);
                                    int hour = utilities.hourReceiver();
                                    int minute = utilities.minuteReceiver();
                                    Timestamp from = new Timestamp(2022 - 1900, month, day, hour, minute, 0, 0);
                                    List<Transaction> fromTransactions = transactionsService.findAllByCardAndDate(card.getAccount().getId(), from);
                                    utilities.iterateThrough(fromTransactions);
                                    break;
                                case "5":
                                    break label;
                                default:
                                    utilities.printRed("Choose an Option.");
                            }
                        }
                    }

                } else {
                    System.out.println("Wrong Password.");
                    count++;
                }
                if (count == 3) {
                    card.setCardStatus(CardStatus.BLOCKED);
                    cardsService.modifyPassAndStatus(card);
                    System.out.println("Your Card is blocked now. A clerk can open it.");
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //=====================================================================
    //==========================Transaction Section========================
    //=====================================================================

    public static void transactionSection(Card card) {
        try {
            Account thisAccount = accountsService.findById(card.getAccount().getId());
            Account destinationAccount;
            Bank bank = banksService.findById(thisAccount.getBank().getId());
            Branch branch = branchesService.findById(thisAccount.getBranch().getId());

            double accountBalance = thisAccount.getBalance();
            String cardNumber = card.getNumber();
            if (accountBalance > 601) {
                System.out.println("Welcome to Transaction Section.");
                System.out.println("Fill Below fields.");
                label:
                while (true) {
                    System.out.print("Destination Card Number: ");
                    String desCardNum = utilities.twelveDigitReceiver();
                    Card destinationCard;
                    if (!(cardsService.cardNumAvailable(desCardNum)) || desCardNum.equals(cardNumber)) {
                        utilities.printRed("Card Number is unavailable!");
                        break;
                    } else {
                        destinationCard = cardsService.findByNumber(desCardNum);
                        destinationAccount = accountsService.findById(destinationCard.getAccount().getId());
                        System.out.println("Amount: ");
                        Double amount;
                        while (true) {
                            amount = utilities.doubleReceiver();
                            if (amount + 600 > accountBalance) {
                                System.out.println("موجودی کافی نیست");
                                System.out.print("مقدار کمتر وارد کنید");
                                System.out.println(" :موجودی شما" + accountBalance);
                            } else break;
                        }
                        Transaction transaction = new Transaction(
                                0, branch.getId(), bank.getId()
                                , card.getAccount().getId(),
                                destinationCard.getAccount().getId()
                                , amount, TransactionStatus.PENDING
                                , new Timestamp(System.currentTimeMillis()));
                        int transactionId = transactionsService.makeNew(transaction);
                        transaction.setId(transactionId);
                        utilities.printGreen("Transaction with ID: " + transactionId + " was made");

                        int count = 0;
                        while (true) {
                            System.out.println("Second Password: ");
                            int secondPass = utilities.sixDigitReceiver();
                            System.out.print("CVV2: ");
                            int cvv2 = utilities.threeDigitsReceiver();
                            System.out.print("Exp. ");
                            int expYear = utilities.yearReceiver();
                            System.out.print("Exp. ");
                            int expMonth = utilities.monthReceiver();
                            YearMonth expDateEntered = YearMonth.of(expYear, expMonth);
                            if (card.getSecondPass() == secondPass && card.getCvv2() == cvv2 &&
                                    card.getExpDate().equals(expDateEntered)) {
                                break;
                            } else {
                                count++;
                                System.out.println("Wrong initials. Card will be blocked after 3 wrong attempts. Attempt " + count + "/3");
                            }
                            if (count == 3) {
                                transaction.setStatus(TransactionStatus.CANCELLED);
                                card.setCardStatus(CardStatus.BLOCKED);
                                cardsService.modifyPassAndStatus(card);
                                int canceledTransaction = transactionsService.getDone(transaction);
                                utilities.printRed("Transaction " + canceledTransaction + " got Cancelled!\n" +
                                        "Your Card is blocked till a Clerk Opens it again.");
                                break label;
                            }
                        }
                        transaction.setStatus(TransactionStatus.DONE);
                        int preformedTransactionId = transactionsService.getDone(transaction);
                        thisAccount.setBalance(accountBalance - amount - 600);
                        destinationAccount.setBalance(destinationAccount.getBalance() + amount);
                        accountsService.doTransaction(thisAccount);
                        accountsService.doTransaction(destinationAccount);
                        utilities.printGreen("Transaction " + preformedTransactionId + " Done , " + amount.intValue() + "was withdrawn from your account with ID:" + thisAccount.getId());
                        break;
                    }
                }
            } else System.out.println("موجودی کلا کافی نیست");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    //++++++++++++++++++++++++++++++Secret+++++++++++++++++++++++++++++
    //+++++++++++++++++++++++++++Admin Section+++++++++++++++++++++++++
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public static void adminSection() throws SQLException {
        System.out.println("Bank or Branch? (b/br)");
        String bankOrBranch = scanner.nextLine();
        if (bankOrBranch.equals("b")) {
            System.out.println("Bank Name: ");
            String bankName = scanner.nextLine();
            System.out.println("Branch Name: ");
            String branchName = scanner.nextLine();
            Bank bank = new Bank(bankName);
            Integer bankId = banksService.createNew(bank);
            try {
                utilities.printGreen("Bank with ID " + bankId + " Created.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            bank.setId(bankId);
            Branch newBranch = new Branch(0, bank, branchName);
            Integer branchId = branchesService.createNew(newBranch);
            try {
                utilities.printGreen("Branch with ID " + branchId + " Created.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print("President First Name: ");
            String presidentFirstName = scanner.nextLine();
            System.out.print("Last Name: ");
            String presidentLastName = scanner.nextLine();
            String username = utilities.usernameReceiver();
            System.out.print("Password: ");
            String password = scanner.nextLine();
            System.out.print("Salary: ");
            double salary = utilities.doubleReceiver();
            President president = new President(0, presidentFirstName, presidentLastName, username, password, branchId, salary);
            Integer presidentId = presidentsService.signup(president);
            try {
                utilities.printGreen("President with ID " + presidentId + " Created.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (bankOrBranch.equals("br")) {
            List<Bank> banks = banksService.findAll();
            utilities.iterateThrough(banks);
            System.out.println("Enter bank ID: ");
            int bankId = utilities.intReceiver();
            if (banksService.exists(bankId)) {
                Bank newBank = banksService.findById(bankId);
                System.out.println("Branch Name: ");
                String branchName = scanner.nextLine();
                Branch branch = new Branch(0, newBank, branchName);
                Integer branchId = branchesService.createNew(branch);
                try {
                    utilities.printGreen("Branch with ID " + branchId + " Created.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print("President First Name: ");
                String presidentFirstName = scanner.nextLine();
                System.out.print("Last Name: ");
                String presidentLastName = scanner.nextLine();
                String username = utilities.usernameReceiver();
                System.out.print("Password: ");
                String password = scanner.nextLine();
                System.out.print("Salary: ");
                double salary = utilities.doubleReceiver();
                President president = new President(0, presidentFirstName, presidentLastName, username, password, branchId, salary);
                Integer presidentId = presidentsService.signup(president);
                try {
                    utilities.printGreen("President with ID " + presidentId + " Created.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else System.out.println("Bank ID Invalid.");
        }
    }

}
