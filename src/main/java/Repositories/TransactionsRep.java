package Repositories;

import Entities.Things.Transaction;
import Entities.Things.TransactionStatus;
import Interfaces.ThingCRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
                "bank_id    INTEGER," +
                "transaction_time   timestamp" +
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
        String insertStmt = "INSERT INTO transactions (" +
                "amount, commission, src_account_id" +
                ", des_account_id, bank_id, branch_id," +
                "status,transaction_time" +
                ") " +
                "VALUES (?,?,?,?,?,?,?,?)" +
                "RETURNING id;";
        try {
            PreparedStatement ps = connection.prepareStatement(insertStmt);
            ps.setDouble(1, transaction.getAmount());
            ps.setDouble(2, transaction.getCommission());
            ps.setInt(3, transaction.getSrc_account_id());
            ps.setInt(4, transaction.getDes_account_id());
            ps.setInt(5, transaction.getBranch_id());
            ps.setInt(6, transaction.getBank_id());
            ps.setString(7, transaction.getTransactionStatus().toString());
            ps.setObject(8,transaction.getTransactionTime());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Object> readByBranchId(Integer branchId) {
        List<Object> transactions = new ArrayList<>();
        String selectStmt = "SELECT * FROM transactions " +
                " WHERE branch_id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(selectStmt);
            ps.setInt(1, branchId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                transactions.add(new Transaction(
                                rs.getInt("id"),
                                rs.getInt("branch_id"),
                                rs.getInt("bank_id"),
                                rs.getInt("src_account_id"),
                                rs.getInt("des_account_id"),
                                rs.getDouble("amount"),
                                rs.getObject("transaction_time", LocalDate.class)
                        )
                );
            } return transactions;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public Transaction read(Transaction transaction) {
        String selectStmt = "SELECT * FROM transactions " +
                " WHERE id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(selectStmt);
            ps.setInt(1, transaction.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Transaction(
                        rs.getInt("id"),
                        rs.getInt("branch_id"),
                        rs.getInt("bank_id"),
                        rs.getInt("src_account_id"),
                        rs.getInt("des_account_id"),
                        rs.getDouble("amount"),
                        rs.getObject("transaction_time", LocalDate.class)
                );
            } else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Transaction read(Integer transactionId) {
        String selectStmt = "SELECT * FROM transactions " +
                "WHERE id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(selectStmt);
            ps.setInt(1, transactionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Transaction(
                        rs.getInt("id"),
                        rs.getInt("branch_id"),
                        rs.getInt("bank_id"),
                        rs.getInt("srs_account_id"),
                        rs.getInt("des_account_id"),
                        rs.getDouble("amount"),
                        TransactionStatus.valueOf(rs.getString("status")),
                        rs.getObject("transaction_time", LocalDate.class)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Transaction> readAll() {
        List<Transaction> transactions = new ArrayList<>();
        String selectStmt = "SELECT * FROM transactions ;";
        try {
            PreparedStatement ps = connection.prepareStatement(selectStmt);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                transactions.add(new Transaction(
                                rs.getInt("id"),
                                rs.getInt("branch_id"),
                                rs.getInt("bank_id"),
                                rs.getInt("srs_account_id"),
                                rs.getInt("des_account_id"),
                                rs.getDouble("amount"),
                                TransactionStatus.valueOf(rs.getString("status")),
                                rs.getObject("transaction_time", LocalDate.class)
                        )
                );
            }
            return transactions;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer update(Transaction transaction) {
        String updateStmt = "UPDATE transactions " +
                "SET status = ? WHERE id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(updateStmt);
            ps.setString(1, transaction.getTransactionStatus().toString());
            ps.setInt(2, transaction.getId());
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(Transaction transaction) {
        String delStmt = "DELETE FROM transactions WHERE id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(delStmt);
            ps.setInt(1, transaction.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
