package App.HandyClasses;


import Services.ClerksService;
import Services.ClientsService;
import Services.PresidentsService;

import java.sql.*;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Utilities {

    Connection connection;
    Scanner scanner = new Scanner(System.in);
    ClientsService clientsService;
    ClerksService clerksService;
    PresidentsService presidentsService;

    public Utilities(Connection connection){
        this.connection = connection;
        clerksService = new ClerksService(connection);
        clientsService = new ClientsService(connection);
        presidentsService = new PresidentsService(connection);
    }

    public String usernameReceiver() throws SQLException {
        while (true) {
            System.out.print("Username: ");
            String username = scanner.nextLine();
            if (clerksService.exists(username) || clientsService.exists(username) || presidentsService.exists(username)) {
                System.out.println("This username Already Exists! Try another one: ");
            }
            else {
                return username;
            }
        }
    }


    private boolean checkExistenceByString(String usernames, String username) {
        boolean flag = true;
        for (int i = 0; i < usernames.split(" ").length; i++) {
            if (usernames.split(" ")[i].equals(username)) {
                flag = true;
                break;
            } else flag = false;
        }
        return flag;
    }

    public String roleReceiver() {
        while (true) {
            System.out.print("Role(Admin,Cinema,Customer): ");
            String input = scanner.nextLine();
            if (
                    input.toUpperCase(Locale.ROOT).equals("PRESIDENT") ||
                    input.toUpperCase(Locale.ROOT).equals("CLERK") ||
                    input.toUpperCase(Locale.ROOT).equals("CLIENT")
            ) {
                return input;
            } else {
                System.out.println("Wrong Role!");
            }
        }
    }




    public String regexAdder(String regex, String tag, int leastChars) {
        while (true) {
            System.out.print(tag + "(least length:" + leastChars + "): ");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if (checkRegex(input, regex)) {
                return input;
            } else {
                System.out.println("Wrong " + tag + " Format. Enter a Correct " + tag + " Format:");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean checkRegex(String input, String regexPattern) {
        return Pattern.compile(regexPattern).matcher(input).matches();
    }

    public int intReceiver() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.print("You should enter a number here: ");
            intReceiver();
        }
        return 0;
    }

    public Double doubleReceiver() {
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("You should enter a number here:");
            doubleReceiver();
        }
        return 0.0;
    }

    public int monthReceiver() {
        while (true) {
            System.out.print("Month: ");
            int monthNum = intReceiver();
            if (monthNum < 1 || monthNum > 12) {
                System.out.println("Enter a number between 1 and 12.");
            } else {
                return monthNum;
            }
        }
    }

    public int dayReceiver(int month) {
        while (true) {
            System.out.print("Day: ");
            int day = intReceiver();
            if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                if (day > 31 || day < 0) {
                    System.out.println("Enter a number between 1 and 31 for this month.");
                } else return day;
            } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                if (day > 30 || day < 0) {
                    System.out.println("Enter a number between 1 and 30 for this month.");
                } else return day;
            } else if (month == 2) {
                if (day > 28 || day < 0) {
                    System.out.println("Enter a number between 1 and 28 for this month.");
                } else return day;
            }
        }
    }

    public int hourReceiver() {
        while (true) {
            System.out.print("Hour: ");
            int hour = intReceiver();
            if (hour > 23 || hour < 0) {
                System.out.print("Enter a valid hour: ");
            } else return hour;
        }
    }

    public int minuteReceiver() {
        while (true) {
            System.out.print("Minute: ");
            int minute = intReceiver();
            if (minute > 59 || minute < 0) {
                System.out.print("Enter a valid minute: ");
            } else return minute;
        }
    }

    public double percentageReceiver() {
        while (true) {
            System.out.println("Percentage: ");
            double percent = doubleReceiver();
            if (percent > 100 || percent < 0) {
                System.out.print("Enter a percent between 0 to 100.");
            } else return percent;
        }
    }

    public boolean isNumeric(String input) {
        if (input == null) {
            return false;
        }
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public void printRed(String input) throws InterruptedException {
        System.out.print("------------------------------\n" + ANSI_RED + input + ANSI_RESET + "\n------------------------------\n");
        Thread.sleep(1000);
    }

    public void printGreen(String input) throws InterruptedException {
        System.out.print("------------------------------\n" + ANSI_GREEN + input + ANSI_RESET + "\n------------------------------\n");
        Thread.sleep(1000);
    }

    public void printYellow(String input) throws InterruptedException {
        System.out.print("------------------------------\n" + ANSI_YELLOW + input + ANSI_RESET + "\n------------------------------\n");
        Thread.sleep(1000);
    }

    public void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private final String ANSI_RESET = "\u001B[0m";
    private final String ANSI_RED = "\u001B[31m";
    private final String ANSI_GREEN = "\u001B[32m";
    private final String ANSI_YELLOW = "\u001B[33m";
    private final String cinemaNameRegex = "^[a-zA-Z]{4,}(?: [a-zA-Z]+){0,2}$";
}
