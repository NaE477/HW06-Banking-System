package Repositories;

import Entities.Things.Bank.Bank;
import Entities.Things.Bank.Branch;
import Entities.Things.Customer.Account;
import Entities.Things.Customer.Card;
import Entities.Users.Client;
import Interfaces.ThingCRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountsRep implements ThingCRUD<Account> {
    Connection connection;
    public AccountsRep(Connection connection){
        this.connection = connection;
    }
    @Override
    public void create() {
        String crtStmt = "CREATE TABLE IF NOT EXISTS accounts(" +
                "id SERIAL PRIMARY KEY ," +
                "accountnumber char(8)," +
                "balance double precision," +
                "bank_id integer," +
                "branch_id integer," +
                "card_id integer," +
                "client_id integer" +
                ");"; // foreign keys applied

        try {
            PreparedStatement ps = connection.prepareStatement(crtStmt);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer insert(Account account) {
        String insertStmt = "INSERT INTO accounts (" +
                "accountnumber, balance, bank_id, branch_id, card_id, client_id" +
                ") " +
                "VALUES (?,?,?,?,?,?)" +
                "RETURNING id;";
        try {
            PreparedStatement ps = connection.prepareStatement(insertStmt);
            ps.setString(1,account.getAccountNumber());
            ps.setDouble(2,account.getBalance());
            ps.setInt(3,account.getBank().getId());
            ps.setInt(4,account.getBranch().getId());
            ps.setInt(5,account.getCard().getId());
            ps.setInt(6,account.getClient().getUserId());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Account read(Account account) {
        String selectStmt = "SELECT * FROM accounts " +
                " INNER JOIN clients c on c.id = accounts.client_id " +
                "INNER JOIN banks b on b.id = accounts.bank_id " +
                "INNER JOIN branches b2 on b.id = b2.bank_id " +
                "INNER JOIN cards c2 on accounts.id = c2.account_id " +
                "WHERE id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(selectStmt);
            ps.setInt(1, account.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Account(
                        rs.getInt("id"),
                        rs.getString("accountnumber"),
                        new Client(
                                rs.getInt("c.id"),
                                rs.getString("username"),
                                rs.getString("password")
                        ),
                        new Bank(
                                rs.getInt("b.id"),
                                rs.getString("bank_name")
                        ),
                        new Branch(rs.getInt("b2.id")),
                        rs.getDouble("balance"),
                        new Card(rs.getInt("c2.id"))
                );
            } else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Account read(Integer accountId) {
        String selectStmt = "SELECT * FROM accounts " +
                "WHERE id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(selectStmt);
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Account(
                        rs.getInt("id"),
                        rs.getString("accountnumber"),
                        new Client(
                                rs.getInt("c.id"),
                                rs.getString("username"),
                                rs.getString("password")
                        ),
                        new Bank(
                                rs.getInt("b.id"),
                                rs.getString("bank_name")
                        ),
                        new Branch(rs.getInt("b2.id")),
                        rs.getDouble("balance"),
                        new Card(rs.getInt("c2.id"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Account> readAll() {
        List<Account> accounts = new ArrayList<>();
        String selectStmt = "SELECT * FROM accounts";
        try {
            PreparedStatement ps = connection.prepareStatement(selectStmt);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                accounts.add(new Account(
                        rs.getInt("id"),
                        rs.getString("accountnumber"),
                        new Client(
                                rs.getInt("c.id"),
                                rs.getString("username"),
                                rs.getString("password")
                        ),
                        new Bank(
                                rs.getInt("b.id"),
                                rs.getString("bank_name")
                        ),
                        new Branch(rs.getInt("b2.id")),
                        rs.getDouble("balance"),
                        new Card(rs.getInt("c2.id"))
                        )
                );
            }
            return accounts;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer update(Account account) {
        String updateStmt = "UPDATE accounts SET balance = ?" +
                "WHERE id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(updateStmt);
            ps.setDouble(1,account.getBalance());
            ps.setInt(1,account.getId());
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(Account account) {
        String delStmt = "DELETE FROM accounts WHERE id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(delStmt);
            ps.setInt(1, account.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
