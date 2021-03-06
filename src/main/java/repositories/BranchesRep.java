package repositories;

import entities.things.bank.Bank;
import entities.things.bank.Branch;
import interfaces.ThingCRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BranchesRep implements ThingCRUD<Branch> {
    Connection connection;

    public BranchesRep(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create() {
        String createStmt = "CREATE TABLE IF NOT EXISTS branches (" +
                "id         SERIAL PRIMARY KEY ," +
                "branch_name    VARCHAR(50)," +
                "bank_id        INTEGER," +
                "president_id   INTEGER," +
                "FOREIGN KEY (bank_id) REFERENCES branches(id)" +
                ");";
        try {
            PreparedStatement ps = connection.prepareStatement(createStmt);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer insert(Branch branch) {
        String insertStmt = "INSERT INTO branches (" +
                "branch_name, bank_id" +
                ")" +
                "VALUES (?,?)" +
                "RETURNING id;";
        try {
            PreparedStatement ps = connection.prepareStatement(insertStmt);
            ps.setString(1, branch.getBranch_name());
            ps.setInt(2, branch.getBank().getId());
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
    public Branch read(Branch branch) {
        String selectStmt = "SELECT * FROM branches " +
                " INNER JOIN banks b on b.id = branches.bank_id" +
                " WHERE b.id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(selectStmt);
            ps.setInt(1, branch.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Branch(
                        rs.getInt("id"),
                        new Bank(rs.getInt(4), rs.getString("bank_name")),
                        rs.getString("branch_name")
                );
            } else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Branch read(Integer targetId) {
        String selectStmt = "SELECT * FROM branches " +
                " INNER JOIN banks b on b.id = branches.bank_id" +
                " WHERE branches.id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(selectStmt);
            ps.setInt(1, targetId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Branch(
                        rs.getInt("id"),
                        new Bank(rs.getInt(1), rs.getString("bank_name")),
                        rs.getString("bank_name")
                );
            } else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Branch> readAll() {
        List<Branch> branches = new ArrayList<>();
        String selectStmt = "SELECT * FROM branches " +
                " INNER JOIN banks b on b.id = branches.bank_id" +
                ";";
        try {
            PreparedStatement ps = connection.prepareStatement(selectStmt);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                branches.add(
                        new Branch(
                                rs.getInt("id"),
                                new Bank(rs.getInt(4), rs.getString("bank_name")),
                                rs.getString("bank_name")
                        )
                );
            }
            return branches;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer update(Branch branch) {
        String updateStmt = "UPDATE branches " +
                "SET branch_name = ? WHERE id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(updateStmt);
            ps.setString(1, branch.getBranch_name());
            ps.setInt(2, branch.getId());
            return branch.getId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(Branch branch) {
        String delStmt = "DELETE FROM branches WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(delStmt);
            ps.setInt(1, branch.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
