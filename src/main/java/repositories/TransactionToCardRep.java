package repositories;

import entities.things.Transaction;
import entities.things.TransactionStatus;
import entities.things.TransactionType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionToCardRep {
    Connection connection;
    public TransactionToCardRep(Connection connection){
        this.connection = connection;
    }

    public void create() {

    }

    public void insertSrc(Transaction transaction, TransactionType transactionType) {
        String insStmt = "INSERT INTO transactiontocard (transaction_id, account_id, amount, type) " +
                "VALUES (?,?,?,?) ;";
        try {
            PreparedStatement ps = connection.prepareStatement(insStmt);
            ps.setInt(1,transaction.getId());
            ps.setInt(2,transaction.getSrc_account_id());
            ps.setDouble(3,transaction.getAmount());
            ps.setString(4,transactionType.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void insertDes(Transaction transaction, TransactionType transactionType) {
        String insStmt = "INSERT INTO transactiontocard (transaction_id, account_id, amount, type) " +
                "VALUES (?,?,?,?) ";
        try {
            PreparedStatement ps = connection.prepareStatement(insStmt);
            ps.setInt(1,transaction.getId());
            ps.setInt(2,transaction.getDes_account_id());
            ps.setDouble(3,transaction.getAmount());
            ps.setString(4,transactionType.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Transaction read(Transaction transaction) {
        String selectStmt = "SELECT * FROM transactiontocard" +
                " INNER JOIN transactions t on t.id = transactiontocard.transaction_id" +
                " WHERE account_id = ? OR ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(selectStmt);
            ps.setInt(1,transaction.getSrc_account_id());
            ps.setInt(2,transaction.getDes_account_id());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new Transaction(
                        rs.getInt(1),
                        rs.getInt("srs_account_id"),
                        rs.getInt("srs_account_id"),
                        rs.getDouble("amount"),
                        TransactionStatus.valueOf(rs.getString("status")),
                        rs.getTimestamp("transaction_time")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Transaction read(Integer transactionId) {
        String selectStmt = "SELECT * FROM transactiontocard" +
                " INNER JOIN transactions t on t.id = transactiontocard.transaction_id" +
                " WHERE transaction_id = ? ;";
        try {
            PreparedStatement ps = connection.prepareStatement(selectStmt);
            ps.setInt(1,transactionId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new Transaction(
                        rs.getInt(1),
                        rs.getInt("srs_account_id"),
                        rs.getInt("srs_account_id"),
                        rs.getDouble("amount"),
                        TransactionStatus.valueOf(rs.getString("status")),
                        rs.getTimestamp("transaction_time")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(Transaction obj) {

    }
}
