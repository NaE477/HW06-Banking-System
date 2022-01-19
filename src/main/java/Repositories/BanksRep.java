package Repositories;

import Entities.Things.Bank.Bank;
import Interfaces.ThingCRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BanksRep implements ThingCRUD<Bank> {

    Connection connection;

    public BanksRep(Connection connection){
        this.connection = connection;
    }

    @Override
    public void create() {
        String createStmt = "CREATE TABLE IF NOT EXISTS banks (" +
                "id         SERIAL PRIMARY KEY ," +
                "bank_name       VARCHAR(50)" +
                ");";
        try {
            PreparedStatement ps = connection.prepareStatement(createStmt);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer insert(Bank bank) {
        String insertStmt = "INSERT INTO banks (" +
                "bank_name) VALUES (?) " +
                "RETURNING id;";
        try {
            PreparedStatement ps = connection.prepareStatement(insertStmt);
            ps.setString(1,bank.getName());
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
    public Bank read(Bank bank) {
        String selectStmt = "SELECT * FROM banks " +
                " WHERE id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(selectStmt);
            ps.setInt(1,bank.getId());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new Bank(
                        rs.getInt("id"),
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
    public Bank read(Integer bankId) {
        String selectStmt = "SELECT * FROM banks " +
                "WHERE id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(selectStmt);
            ps.setInt(1,bankId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new Bank(
                        rs.getInt("id"),
                        rs.getString("name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Bank> readAll() {
        List<Bank> banks = new ArrayList<>();
        String selectStmt = "SELECT * FROM banks ";
        try {
            PreparedStatement ps = connection.prepareStatement(selectStmt);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                 banks.add(new Bank(
                        rs.getInt("id"),
                        rs.getString("name"))
                );
            }
            return banks;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer update(Bank bank) {
        String updateStmt = "UPDATE banks " +
                "SET bank_name = ? WHERE id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(updateStmt);
            ps.setString(1,bank.getName());
            ps.setInt(2,bank.getId());
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
    public void delete(Bank bank) {
        String delStmt = "DELETE FROM banks WHERE id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(delStmt);
            ps.setInt(1,bank.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
