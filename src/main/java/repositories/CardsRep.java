package repositories;

import entities.things.bank.Bank;
import entities.things.bank.Branch;
import entities.things.customer.Account;
import entities.things.customer.Card;
import entities.things.customer.CardStatus;
import entities.users.Client;
import interfaces.ThingCRUD;

import java.sql.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class CardsRep implements ThingCRUD<Card> {

    Connection connection;

    public CardsRep(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create() {
        String createStmt = "CREATE TABLE IF NOT EXISTS cards (" +
                "id         SERIAL PRIMARY KEY ," +
                "card_number     CHAR(12)," +
                "first_pass      CHAR(4)," +
                "second_pass     CHAR(6)," +
                "exp_date       DATE," +
                "account_id     INTEGER," +
                "cvv2           CHAR(3)," +
                "card_status varchar," +
                "FOREIGN KEY (account_id) REFERENCES accounts(id)" +
                ")";
        try {
            PreparedStatement ps = connection.prepareStatement(createStmt);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer insert(Card card) {
        String insStmt = "INSERT INTO cards (card_number,first_pass,second_pass,exp_date,account_id,cvv2,card_status)" +
                " VALUES (?,?,?,?,?,?,?) RETURNING id;";
        try {
            Date expDate = new Date(card.getExpDate().getYear() - 1900,card.getExpDate().getMonthValue(),5);

            PreparedStatement ps = connection.prepareStatement(insStmt);
            ps.setString(1, card.getNumber());
            ps.setString(2, String.valueOf(card.getFirstPass()));
            ps.setString(3, "-11111");
            ps.setDate(4, expDate);
            ps.setInt(5, card.getAccount().getId());
            ps.setString(6, String.valueOf(card.getCvv2()));
            ps.setString(7,CardStatus.BLOCKED.toString());
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Card read(Card card) {
        String selectStmt = "SELECT *," +
                " DATE_PART('year',exp_date) AS year, " +
                " DATE_PART('month',exp_date) AS month " +
                "FROM cards" +
                " INNER JOIN accounts a on a.id = cards.account_id " +
                " INNER JOIN clients c on c.id = a.client_id " +
                " INNER JOIN banks b on b.id = a.bank_id " +
                " INNER JOIN branches b2 on b2.id = a.branch_id" +
                " WHERE card_number = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(selectStmt);
            ps.setString(1, card.getNumber());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Card(
                        rs.getInt("id"),
                        rs.getInt("cvv2"),
                        Integer.parseInt(rs.getString("first_pass")),
                        Integer.parseInt(rs.getString("second_pass")),
                        new Account(
                                rs.getInt("account_id"),
                                rs.getString("accountnumber"),
                                new Client(
                                        rs.getInt("c.id"),
                                        rs.getString("username"),
                                        rs.getString("password")
                                ),
                                new Bank(
                                        rs.getInt("b.id")
                                ),
                                new Branch(
                                        rs.getInt("b2.bank_id")),
                                rs.getDouble("balance")
                        ),
                        YearMonth.of(rs.getInt("year"), rs.getInt("month")),
                        rs.getString("card_number")
                );
            } else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Card read(Integer cardId) {
        String selectStmt = "SELECT *," +
                " DATE_PART('year',exp_date) AS year, " +
                " DATE_PART('month',exp_date) AS month " +
                "FROM cards" +
                " INNER JOIN accounts a on a.id = cards.account_id " +
                " INNER JOIN clients c on c.id = a.client_id " +
                " INNER JOIN banks b on b.id = a.bank_id " +
                " INNER JOIN branches on branches.id = a.branch_id" +
                " WHERE cards.id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(selectStmt);
            ps.setInt(1, cardId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Card(
                        rs.getInt("id"),
                        rs.getInt("cvv2"),
                        Integer.parseInt(rs.getString("first_pass")),
                        Integer.parseInt(rs.getString("second_pass")),
                        new Account(
                                rs.getInt("account_id"),
                                rs.getString("accountnumber"),
                                new Client(
                                        rs.getInt("client_id"),
                                        rs.getString("username"),
                                        rs.getString("password")
                                ),
                                new Bank(
                                        rs.getInt(20)
                                ),
                                new Branch(
                                        rs.getInt(22)),
                                rs.getDouble("balance")
                        ),
                        YearMonth.of(rs.getInt("year"), rs.getInt("month")),
                        rs.getString("card_number")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Card> readAll() {
        List<Card> cards = new ArrayList<>();
        String selectStmt = "SELECT id,card_number,first_pass,second_pass,account_id,cvv2," +
                " DATE_PART('year',exp_date) AS year, " +
                " DATE_PART('month',exp_date) AS month " +
                "FROM cards" +
                " INNER JOIN accounts a on a.id = cards.account_id " +
                " INNER JOIN clients c on c.id = a.client_id " +
                " INNER JOIN banks b on b.id = a.bank_id " +
                " INNER JOIN branches b2 on b2.id = a.branch_id" +
                ";";
        try {
            PreparedStatement ps = connection.prepareStatement(selectStmt);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cards.add(new Card(
                                rs.getInt("id"),
                                rs.getInt("cvv2"),
                                Integer.parseInt(rs.getString("first_pass")),
                                Integer.parseInt(rs.getString("second_pass")),
                                new Account(
                                        rs.getInt("account_id"),
                                        rs.getString("accountnumber"),
                                        new Client(
                                                rs.getInt("c.id"),
                                                rs.getString("username"),
                                                rs.getString("password")
                                        ),
                                        new Bank(
                                                rs.getInt("b.id")
                                        ),
                                        new Branch(
                                                rs.getInt("b2.bank_id")),
                                        rs.getDouble("balance")
                                ),
                                YearMonth.of(rs.getInt("year"), rs.getInt("month")),
                                rs.getString("card_number")
                        )
                );
            }
            return cards;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Card readByClient(Integer clientId,Integer cardId){
        String selectStmt = "SELECT *," +
                " DATE_PART('year',exp_date) AS year, " +
                " DATE_PART('month',exp_date) AS month " +
                " FROM cards " +
                "INNER JOIN accounts a on a.id = cards.account_id " +
                "INNER JOIN clients c on c.id = a.client_id " +
                "WHERE client_id = ? AND cards.id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(selectStmt);
            ps.setInt(1,clientId);
            ps.setInt(2,cardId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Card(
                        rs.getInt(1),
                        Integer.parseInt(rs.getString("cvv2")),
                        Integer.parseInt(rs.getString("first_pass")),
                        Integer.parseInt(rs.getString("second_pass")),
                        new Account(
                                rs.getInt("account_id"),
                                rs.getString("accountnumber"),
                                new Client(
                                        rs.getInt(15),
                                        rs.getString("username"),
                                        rs.getString("password")
                                ),
                                new Bank(
                                        rs.getInt(12)
                                ),
                                new Branch(
                                        rs.getInt(13)),
                                rs.getDouble("balance")
                        ),
                        YearMonth.of(rs.getInt("year"), rs.getInt("month")),
                        rs.getString("card_number"),
                        CardStatus.valueOf(rs.getString("card_status"))
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Card> readAllByClient(Integer clientId){
        List<Card> cards = new ArrayList<>();
        String selectStmt = "SELECT *," +
                " DATE_PART('year',exp_date) AS year, " +
                " DATE_PART('month',exp_date) AS month " +
                " FROM cards " +
                "INNER JOIN accounts a on a.id = cards.account_id" +
                " INNER JOIN clients c on c.id = a.client_id " +
                "WHERE c.id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(selectStmt);
            ps.setInt(1,clientId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                cards.add(new Card(
                        rs.getInt(1),
                        Integer.parseInt(rs.getString("cvv2")),
                        Integer.parseInt(rs.getString("first_pass")),
                        Integer.parseInt(rs.getString("second_pass")),
                                new Account(rs.getInt(10)),
                                YearMonth.of(rs.getInt("year"), rs.getInt("month")),
                                rs.getString("card_number"),
                        CardStatus.valueOf(rs.getString("card_status"))
                ));
            }
            return cards;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Card readByNumber(String cardNumber){
        String selectStmt = "SELECT *," +
                "  DATE_PART('year',exp_date) AS year," +
                " DATE_PART('month',exp_date) AS month " +
                " FROM cards " +
                "INNER JOIN accounts a on a.id = cards.account_id" +
                " INNER JOIN clients c on c.id = a.client_id " +
                "WHERE card_number = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(selectStmt);
            ps.setString(1,cardNumber);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new Card(
                        rs.getInt(1),
                        Integer.parseInt(rs.getString("cvv2")),
                        Integer.parseInt(rs.getString("first_pass")),
                        Integer.parseInt(rs.getString("second_pass")),
                        new Account(
                                rs.getInt("account_id"),
                                rs.getString("accountnumber"),
                                new Client(
                                        rs.getInt(15),
                                        rs.getString("username"),
                                        rs.getString("password")
                                ),
                                new Bank(
                                        rs.getInt(12)
                                ),
                                new Branch(
                                        rs.getInt(13)),
                                rs.getDouble("balance")
                        ),
                        YearMonth.of(rs.getInt("year"), rs.getInt("month")),
                        rs.getString("card_number"),
                        CardStatus.valueOf(rs.getString("card_status"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Card readByAccount(Integer accountId){
        String selectStmt = "SELECT * FROM cards " +
                " INNER JOIN accounts a on a.id = cards.account_id" +
                " WHERE account_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(selectStmt);
            ps.setInt(1,accountId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new Card(
                        rs.getInt("cards.id"),
                        Integer.parseInt(rs.getString("cvv2")),
                        Integer.parseInt(rs.getString("first_pass")),
                        Integer.parseInt(rs.getString("second_pass")),
                        new Account(
                                rs.getInt("account_id"),
                                rs.getString("accountnumber"),
                                new Client(
                                        rs.getInt("c.id"),
                                        rs.getString("username"),
                                        rs.getString("password")
                                ),
                                new Bank(
                                        rs.getInt("b.id")
                                ),
                                new Branch(
                                        rs.getInt("b2.bank_id")),
                                rs.getDouble("balance")
                        ),
                        YearMonth.of(rs.getInt("year"), rs.getInt("month")),
                        rs.getString("card_number")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer update(Card card) {
        String updateStmt = "UPDATE cards " +
                "SET first_pass = ? ," +
                "second_pass = ? , " +
                " card_status = ? " +
                " WHERE id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(updateStmt);
            ps.setString(1, String.valueOf(card.getFirstPass()));
            ps.setString(2, String.valueOf(card.getSecondPass()));
            ps.setString(3,card.getCardStatus().toString());
            ps.setInt(4, card.getId());
            ps.executeUpdate();
            return card.getId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(Card card) {
        String delStmt = "DELETE FROM cards WHERE id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(delStmt);
            ps.setInt(1, card.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Integer deleteByAccountAndBranch(Integer accountId,Integer branchId){
        String delStmt = "DELETE FROM cards " +
                "USING branches " +
                "WHERE account_id = ? AND branches.id = ? " +
                "RETURNING cards.id;";
        try {
            PreparedStatement ps = connection.prepareStatement(delStmt);
            ps.setInt(1,accountId);
            ps.setInt(2,branchId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer deleteByAccount(Integer accountId){
        String delStmt = "DELETE FROM cards " +
                "WHERE account_id = ? " +
                "RETURNING cards.id;";
        try {
            PreparedStatement ps = connection.prepareStatement(delStmt);
            ps.setInt(1,accountId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String deleteById(Integer cardId){
        String delStmt = "DELETE FROM cards WHERE id = ? RETURNING card_number;";
        try {
            PreparedStatement ps = connection.prepareStatement(delStmt);
            ps.setInt(1,cardId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getString("card_number");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Integer deleteByNumber(String cardNumber){
        String delStmt = "DELETE FROM cards WHERE card_number = ? RETURNING id;";
        try {
            PreparedStatement ps = connection.prepareStatement(delStmt);
            ps.setString(1,cardNumber);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
