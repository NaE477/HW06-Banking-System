package Repositories;

import Entities.Things.Status;
import Entities.Things.Transaction;
import Interfaces.ThingCRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionsRep implements ThingCRUD<Transaction> {
    Connection connection;

    public TransactionsRep(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create() {
        String createStmt = "CREATE TABLE IF NOT EXISTS clients (" +
                "id         SERIAL PRIMARY KEY ," +
                "amount     DOUBLE PRECISION," +
                "commission DOUBLE PRECISION," +
                "src_account_id INTEGER," +
                "des_account_id INTEGER," +
                "account_id INTEGER," +
                "branch_id  INTEGER," +
                "bank_id    INTEGER" +
                ");";
        try {
            PreparedStatement ps = connection.prepareStatement(createStmt);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer insert(Transaction transaction) {
        String insertStmt = "INSERT INTO bank.public.transactions (" +
                "amount, commission, src_account_id" +
                ", des_account_id, bank_id, branch_id," +
                "status" +
                ") " +
                "VALUES (?,?,?,?,?,?,?::status)" +
                "RETURNING id;";
        try {
            PreparedStatement ps = connection.prepareStatement(insertStmt);
            ps.setDouble(1,transaction.getAmount());
            ps.setDouble(2,transaction.getCommission());
            ps.setInt(3,transaction.getSrc_account_id());
            ps.setInt(4,transaction.getDes_account_id());
            ps.setInt(5,transaction.getBranch_id());
            ps.setInt(6,transaction.getBank_id());
            ps.setString(7,transaction.getStatus().toString());
            ps.executeUpdate();
            return ps.getResultSet().getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Transaction read(Transaction transaction) {
        String selectStmt = "SELECT * FROM bank.public.transactions " +
                " WHERE id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(selectStmt);
            ps.setInt(1,transaction.getId());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new Transaction(
                        rs.getInt("id"),
                        rs.getInt("branch_id"),
                        rs.getInt("bank_id"),
                        rs.getInt("src_account_id"),
                        rs.getInt("des_account_id"),
                        rs.getDouble("amount")
                );
            }
            else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Transaction read(Integer transactionId) {
        String selectStmt = "SELECT * FROM bank.public.transactions " +
                "WHERE id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(selectStmt);
            ps.setInt(1,transactionId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new Transaction(
                        rs.getInt("id"),
                        rs.getInt("branch_id"),
                        rs.getInt("bank_id"),
                        rs.getInt("srs_account_id"),
                        rs.getInt("des_account_id"),
                        rs.getDouble("amount"),
                        Status.valueOf(rs.getString("status"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer update(Transaction transaction) {
        String updateStmt = "UPDATE bank.public.transactions " +
                "SET status = ? WHERE id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(updateStmt);
            ps.setString(1,transaction.getStatus().toString());
            ps.setInt(2,transaction.getId());
            return ps.getResultSet().getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(Transaction transaction) {
        String delStmt = "DELETE FROM bank.public.transactions WHERE id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(delStmt);
            ps.setInt(1,transaction.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
