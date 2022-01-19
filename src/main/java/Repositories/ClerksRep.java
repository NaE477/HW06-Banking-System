package Repositories;

import Entities.Users.Clerk;
import Interfaces.UserCRUD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClerksRep implements UserCRUD<Clerk> {
    Connection connection;

    public ClerksRep(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert() {
        String createStmt = "CREATE TABLE IF NOT EXISTS clerks (" +
                "id         SERIAL PRIMARY KEY ," +
                "firstname  VARCHAR(50)," +
                "lastname   VARCHAR(50)," +
                "username   VARCHAR(50)," +
                "password   VARCHAR(50)," +
                "salary     DOUBLE PRECISION," +
                "branch_id  INTEGER," +
                "president_id   INTEGER," +
                "FOREIGN KEY (branch_id) REFERENCES branches (id)," +
                "FOREIGN KEY (president_id) REFERENCES presidents (id)" +
                ");";
        try {
            PreparedStatement ps = connection.prepareStatement(createStmt);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer insert(Clerk clerk) {
        String insertStmt = "INSERT INTO clerks (firstname, lastname, username, password, salary, branch_id, president_id) " +
                "VALUES (?,?,?,?,?,?,?) RETURNING id;";
        try {
            PreparedStatement ps = connection.prepareStatement(insertStmt, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, clerk.getFirstname());
            ps.setString(2, clerk.getLastname());
            ps.setString(3, clerk.getUsername());
            ps.setString(4, clerk.getPassword());
            ps.setDouble(5, clerk.getSalary());
            ps.setInt(6, clerk.getBranch_id());
            ps.setInt(7, clerk.getPresident_id());
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
    public Clerk read(String username) {
        String readQuery = "SELECT * FROM clerks WHERE username = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(readQuery);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Clerk(
                        rs.getInt("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("branch_id"),
                        rs.getInt("president_id"),
                        rs.getDouble("salary")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Clerk read(Integer clerkId) {
        String readQuery = "SELECT * FROM clerks WHERE id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(readQuery);
            ps.setInt(1, clerkId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Clerk(
                        rs.getInt("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("branch_id"),
                        rs.getInt("president_id"),
                        rs.getDouble("salary")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Clerk> readAll() {
        List<Clerk> clerks = new ArrayList<>();
        String readQuery = "SELECT * FROM clerks;";
        try {
            PreparedStatement ps = connection.prepareStatement(readQuery);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                clerks.add(new Clerk(
                                rs.getInt("id"),
                                rs.getString("firstname"),
                                rs.getString("lastname"),
                                rs.getString("username"),
                                rs.getString("password"),
                                rs.getInt("branch_id"),
                                rs.getInt("president_id"),
                                rs.getDouble("salary")
                        )
                );
            }
            return clerks;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Clerk> readAllByBranch(Integer branchId){
        List<Clerk> clerks = new ArrayList<>();
        String readQuery = "SELECT * FROM clerks WHERE branch_id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(readQuery);
            ps.setInt(1,branchId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                clerks.add(new Clerk(
                                rs.getInt("id"),
                                rs.getString("firstname"),
                                rs.getString("lastname"),
                                rs.getString("username"),
                                rs.getString("password"),
                                rs.getInt("branch_id"),
                                rs.getInt("president_id"),
                                rs.getDouble("salary")
                        )
                );
            }
            return clerks;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer update(Clerk clerk) {
        String updateStmt = "UPDATE clerks " +
                "SET password = ?, firstname = ?, lastname = ?, salary = ?" +
                " WHERE username = ? RETURNING id;";
        try {
            PreparedStatement ps = connection.prepareStatement(updateStmt);
            ps.setString(1, clerk.getPassword());
            ps.setString(2, clerk.getFirstname());
            ps.setString(3, clerk.getLastname());
            ps.setDouble(4, clerk.getSalary());
            ps.setString(5, clerk.getUsername());
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
    public Integer update(String username, String password) {
        String passChangeStmt = "UPDATE clerks SET password = ? WHERE username = ? RETURNING id;";
        {
            try {
                PreparedStatement ps = connection.prepareStatement(passChangeStmt);
                ps.setString(1, password);
                ps.setString(2, username);
                int id = ps.executeUpdate();
                    return id;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    public void delete(Integer clerkId) {
        String delStmt = "DELETE FROM clerks WHERE id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(delStmt);
            ps.setInt(1, clerkId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
