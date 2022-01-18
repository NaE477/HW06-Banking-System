package Repositories;

import App.HandyClasses.ConClass;
import Entities.Users.Bank.Clerk;
import Interfaces.UserCRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
                "FOREIGN KEY (branch_id) REFERENCES bank.public.branches (id)," +
                "FOREIGN KEY (president_id) REFERENCES bank.public.presidents (id)" +
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
        String insertStmt = "INSERT INTO bank.public.clerks (firstname, lastname, username, password, salary, branch_id, president_id) " +
                "VALUES (?,?,?,?,?,?,?) RETURNING id;";
        try {
            PreparedStatement ps = connection.prepareStatement(insertStmt);
            ps.setString(1, clerk.getFirstname());
            ps.setString(2, clerk.getLastname());
            ps.setString(3, clerk.getUsername());
            ps.setString(4, clerk.getPassword());
            ps.setDouble(5, clerk.getSalary());
            ps.setInt(6, clerk.getBranch_id());
            ps.setInt(7, clerk.getPresident_id());
            ps.executeUpdate();
            ResultSet generatedId = ps.getGeneratedKeys();
            if(generatedId.next()) {
                return generatedId.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Clerk read(String username) {
        String readQuery = "SELECT * FROM bank.public.clerks WHERE username = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(readQuery);
            ps.setString(1,username);
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
        String readQuery = "SELECT * FROM bank.public.clerks WHERE id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(readQuery);
            ps.setInt(1,clerkId);
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
    public Integer update(Clerk clerk) {
        String updateStmt = "UPDATE bank.public.clerks " +
                "SET password = ?, firstname = ?, lastname = ?, salary = ?" +
                " WHERE username = ? RETURNING id;" ;
        try {
            PreparedStatement ps = connection.prepareStatement(updateStmt);
            ps.setString(1, clerk.getPassword());
            ps.setString(2, clerk.getFirstname());
            ps.setString(3,clerk.getLastname());
            ps.setDouble(4,clerk.getSalary());
            ps.setString(5,clerk.getUsername());
            ps.executeUpdate();
            return ps.getResultSet().getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer update(String username,String password) {
        String passChangeStmt = "UPDATE bank.public.clerks SET password = ? WHERE username = ? RETURNING id;";
        {
            try {
                PreparedStatement ps = connection.prepareStatement(passChangeStmt);
                ps.setString(1,password);
                ps.setString(2,username);
                ps.executeUpdate();
                return ps.getResultSet().getInt(1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    public void delete(Integer clerkId) {
        String delStmt = "DELETE FROM bank.public.clerks WHERE id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(delStmt);
            ps.setInt(1,clerkId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
