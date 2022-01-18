package Repositories;

import Entities.Things.Bank.Branch;
import Entities.Things.Transaction;
import Interfaces.ThingCRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BranchesRep implements ThingCRUD<Branch> {
    Connection connection;

    public BranchesRep(Connection connection){
        this.connection = connection;
    }

    @Override
    public void create() {
        String createStmt = "CREATE TABLE IF NOT EXISTS branches (" +
                "id         SERIAL PRIMARY KEY ," +
                "branch_name    VARCHAR(50)," +
                "bank_id        INTEGER," +
                "president_id   INTEGER," +
                "FOREIGN KEY (bank_id) REFERENCES bank.public.branches(id)," +
                "FOREIGN KEY (president_id) REFERENCES bank.public.presidents(id)" +
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
        String insertStmt = "INSERT INTO bank.public.branches (" +
                "branch_name, bank_id, president_id" +
                ")" +
                "VALUES (?,?,?)" +
                "RETURNING id;";
        try {
            PreparedStatement ps = connection.prepareStatement(insertStmt);
            ps.setString(1,branch.getBranch_name());
            ps.setInt(2,branch.getBank_id());
            ps.setInt(3,branch.getPresident_id());
            ps.executeUpdate();
            return ps.getResultSet().getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Branch read(Branch branch) {
        String selectStmt = "SELECT * FROM bank.public.branches " +
                " WHERE id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(selectStmt);
            ps.setInt(1,branch.getId());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new Branch(
                        rs.getInt("id"),
                        rs.getInt("bank_id"),
                        rs.getInt("president_id"),
                        rs.getString("bank_name")
                );
            }
            else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Branch read(Integer targetId) {
        String selectStmt = "SELECT * FROM bank.public.branches " +
                " WHERE id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(selectStmt);
            ps.setInt(1,targetId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new Branch(
                        rs.getInt("id"),
                        rs.getInt("bank_id"),
                        rs.getInt("president_id"),
                        rs.getString("bank_name")
                );
            }
            else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer update(Branch branch) {
        String updateStmt = "UPDATE bank.public.branches " +
                "SET branch_name = ? WHERE id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(updateStmt);
            ps.setString(1,branch.getBranch_name());
            ps.setInt(2,branch.getId());
            return ps.getResultSet().getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(Branch obj) {

    }
}
