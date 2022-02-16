package repositories;

import entities.things.Transaction;
import entities.things.TransactionStatus;
import interfaces.ThingCRUD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionsRep implements ThingCRUD<Transaction> {
    Connection connection;

    public TransactionsRep(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create() {
        String createStmt = "CREATE TABLE IF NOT EXISTS transactions (" +
                "id         SERIAL PRIMARY KEY ," +
                "amount     DOUBLE PRECISION," +
                "commission DOUBLE PRECISION," +
                "src_account_id INTEGER," +
                "des_account_id INTEGER," +
                "account_id INTEGER," +
                "branch_id  INTEGER," +
                "bank_id    INTEGER," +
                "status varchar," +
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
            ps.setInt(5, transaction.getBank_id());
            ps.setInt(6, transaction.getBranch_id());
            ps.setString(7, transaction.getTransactionStatus().toString());
            ps.setObject(8,transaction.getTransactionTime());
            ResultSet rs = ps.executeQuery();
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
                                TransactionStatus.valueOf(rs.getString("status")),
                                rs.getObject("transaction_time", Timestamp.class)
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
                        TransactionStatus.valueOf(rs.getString("status")),
                        rs.getObject("transaction_time", Timestamp.class)
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
                        rs.getObject("transaction_time", Timestamp.class)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Transaction> readAllByClient(Integer clientId){
        List<Transaction> transactions = new ArrayList<>();
        String selectStmt = "SELECT transactions.* FROM transactions " +
                "INNER JOIN accounts a on a.id = transactions.src_account_id or a.id = transactions.des_account_id " +
                "INNER JOIN clients c on c.id = a.client_id" +
                " WHERE client_id = ?" +
                "GROUP BY transactions.id;";
        try {
            PreparedStatement ps = connection.prepareStatement(selectStmt);
            ps.setInt(1,clientId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                transactions.add(new Transaction(
                        rs.getInt(1),
                        rs.getInt(7),
                        rs.getInt(6),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getDouble(2),
                        TransactionStatus.valueOf(rs.getString("status")),
                        rs.getTimestamp(9)
                ));
            }
            return transactions;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Transaction> readAllByAccountId(Integer accountId){
        List<Transaction> transactions = new ArrayList<>();
        String selectStmt = "SELECT * FROM transactiontocard" +
                " INNER JOIN transactions t on t.id = transactiontocard.transaction_id" +
                " WHERE account_id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(selectStmt);
            ps.setInt(1,accountId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                transactions.add(
                        new Transaction(
                                rs.getInt("id"),
                                rs.getInt("branch_id"),
                                rs.getInt("bank_id"),
                                rs.getInt("src_account_id"),
                                rs.getInt("des_account_id"),
                                rs.getDouble(6),
                                TransactionStatus.valueOf(rs.getString("status")),
                                rs.getObject("transaction_time", Timestamp.class)
                        )
                );
            }
            return transactions;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Transaction> readAllByAccountAndDate(Integer accountId, Timestamp from){
        List<Transaction> transactions = new ArrayList<>();
        String selectStmt = "SELECT * FROM transactions " +
                "INNER JOIN transactiontocard t on t.transaction_id = transactions.id" +
                " WHERE account_id = ? " +
                "AND transaction_time >= ? AND transaction_time < ?";
        try {
            PreparedStatement ps = connection.prepareStatement(selectStmt);
            ps.setInt(1,accountId);
            ps.setTimestamp(2,from);
            ps.setTimestamp(3,new Timestamp(System.currentTimeMillis()));
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                transactions.add(new Transaction(
                        rs.getInt("id"),
                        rs.getInt("branch_id"),
                        rs.getInt("bank_id"),
                        rs.getInt("src_account_id"),
                        rs.getInt("des_account_id"),
                        rs.getDouble(6),
                        TransactionStatus.valueOf(rs.getString("status")),
                        rs.getObject("transaction_time", Timestamp.class)
                ));
            }
            return transactions;
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
                                rs.getObject("transaction_time", Timestamp.class)
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
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transaction.getId();
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
