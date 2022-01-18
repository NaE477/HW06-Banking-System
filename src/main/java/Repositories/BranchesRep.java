package Repositories;

import Entities.Things.Bank.Bank;
import Entities.Things.Bank.Branch;
import Entities.Users.President;
import Interfaces.ThingCRUD;

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
                "FOREIGN KEY (bank_id) REFERENCES branches(id)," +
                "FOREIGN KEY (president_id) REFERENCES presidents(id)" +
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
                "branch_name, bank_id, president_id" +
                ")" +
                "VALUES (?,?,?)" +
                "RETURNING id;";
        try {
            PreparedStatement ps = connection.prepareStatement(insertStmt);
            ps.setString(1, branch.getBranch_name());
            ps.setInt(2, branch.getBank().getId());
            ps.setInt(3, branch.getPresident().getUserId());
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
    public Branch read(Branch branch) {
        String selectStmt = "SELECT * FROM branches " +
                " INNER JOIN banks b on b.id = branches.bank_id" +
                " INNER JOIN presidents p on p.id = branches.president_id " +
                " WHERE id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(selectStmt);
            ps.setInt(1, branch.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Branch(
                        rs.getInt("id"),
                        new Bank(rs.getInt("b.id"), rs.getString("bank_name")),
                        new President(rs.getInt("p.id"), rs.getString("firstname"),
                                rs.getString("lastname"), null, null,
                                rs.getInt("branch_id"), rs.getDouble("salary")),
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
                " INNER JOIN presidents p on p.id = branches.president_id " +
                " WHERE id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(selectStmt);
            ps.setInt(1, targetId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Branch(
                        rs.getInt("id"),
                        new Bank(rs.getInt("b.id"), rs.getString("bank_name")),
                        new President(rs.getInt("p.id"), rs.getString("firstname"),
                                rs.getString("lastname"), null, null,
                                rs.getInt("branch_id"), rs.getDouble("salary")),
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
                " INNER JOIN presidents p on p.id = branches.president_id " +
                ";";
        try {
            PreparedStatement ps = connection.prepareStatement(selectStmt);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                branches.add(
                        new Branch(
                                rs.getInt("id"),
                                new Bank(rs.getInt("b.id"), rs.getString("bank_name")),
                                new President(
                                        rs.getInt("p.id"),
                                        rs.getString("firstname"),
                                        rs.getString("lastname"),
                                        null,
                                        null,
                                        rs.getInt("branch_id"),
                                        rs.getDouble("salary")),
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
