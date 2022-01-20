package Repositories;

import Entities.Users.Clerk;
import Entities.Users.President;
import Interfaces.UserCRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PresidentsRep implements UserCRUD<President> {
    Connection connection;

    public PresidentsRep(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert() {
        String createStmt = "CREATE TABLE IF NOT EXISTS clerks (" +
                "id         SERIAL PRIMARY KEY ," +
                "firstname   VARCHAR(50)," +
                "lastname   VARCHAR(50)," +
                "username  VARCHAR(50)," +
                "password   VARCHAR(50)," +
                "salary     DOUBLE PRECISION," +
                "branch_id  INTEGER," +
                "FOREIGN KEY (branch_id) REFERENCES branches (id)" +
                ");";
        try {
            PreparedStatement ps = connection.prepareStatement(createStmt);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer insert(President president) {
        String insertStmt = "INSERT INTO presidents (firstname, lastname, username, password, salary, branch_id) " +
                "VALUES (?,?,?,?,?,?) RETURNING id;";
        try {
            PreparedStatement ps = connection.prepareStatement(insertStmt);
            ps.setString(1, president.getFirstname());
            ps.setString(2, president.getLastname());
            ps.setString(3, president.getUsername());
            ps.setString(4, president.getPassword());
            ps.setDouble(5, president.getSalary());
            ps.setInt(6, president.getBranch_id());
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
    public President read(String username) {
        String readQuery = "SELECT * FROM presidents WHERE username = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(readQuery);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new President(
                        rs.getInt("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("branch_id"),
                        rs.getDouble("salary")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public President read(Integer presidentId) {
        String readQuery = "SELECT * FROM presidents WHERE id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(readQuery);
            ps.setInt(1, presidentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new President(
                        rs.getInt("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("branch_id"),
                        rs.getDouble("salary")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Clerk> readClerks(President president){
        List<Clerk> clerks = new ArrayList<>();
        String readClerksStmt = "SELECT * FROM clerks WHERE branch_id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(readClerksStmt);
            ps.setInt(1,president.getBranch_id());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                clerks.add(new Clerk(
                        rs.getInt("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("branch_id"),
                        rs.getInt("president_id"),
                        rs.getDouble("salary")
                ));
            }
            return clerks;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<President> readAll() {
        List<President> presidents = new ArrayList<>();
        String readQuery = "SELECT * FROM presidents WHERE id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(readQuery);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                presidents.add(new President(
                                rs.getInt("id"),
                                rs.getString("firstname"),
                                rs.getString("lastname"),
                                rs.getString("username"),
                                rs.getString("password"),
                                rs.getInt("branch_id"),
                                rs.getDouble("salary")
                        )
                );
            }
            return presidents;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer update(President president) {
        String updateStmt = "UPDATE presidents " +
                "SET password = ?, firstname = ?, lastname = ?, salary = ?" +
                " WHERE username = ? RETURNING id;";
        try {
            PreparedStatement ps = connection.prepareStatement(updateStmt);
            ps.setString(1, president.getPassword());
            ps.setString(2, president.getFirstname());
            ps.setString(3, president.getLastname());
            ps.setDouble(4, president.getSalary());
            ps.setString(5, president.getUsername());
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
    public Integer update(String username, String password) {
        String passChangeStmt = "UPDATE presidents SET password = ? WHERE username = ? RETURNING id;";
        {
            try {
                PreparedStatement ps = connection.prepareStatement(passChangeStmt);
                ps.setString(1, password);
                ps.setString(2, username);
                ResultSet rs = ps.executeQuery();
                if(rs.next()) {
                    return rs.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void delete(Integer presidentId) {
        String delStmt = "DELETE FROM presidents WHERE id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(delStmt);
            ps.setInt(1, presidentId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
