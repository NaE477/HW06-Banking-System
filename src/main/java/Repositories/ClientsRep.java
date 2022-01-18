package Repositories;

import Entities.Users.Client;
import Interfaces.UserCRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientsRep implements UserCRUD<Client> {
    Connection connection;

    public ClientsRep(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert() {
        String createStmt = "CREATE TABLE IF NOT EXISTS clients (" +
                "id         SERIAL PRIMARY KEY ," +
                "username   VARCHAR(50)," +
                "password   VARCHAR(50)," +
                "firstname  VARCHAR(50)," +
                "lastname   VARCHAR(50)" +
                ");";
        try {
            PreparedStatement ps = connection.prepareStatement(createStmt);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer insert(Client client) {
        String insertStmt = "INSERT INTO clerks (firstname, lastname, username, password) " +
                "VALUES (?,?,?,?) RETURNING id;";
        try {
            PreparedStatement ps = connection.prepareStatement(insertStmt);
            ps.setString(1, client.getFirstname());
            ps.setString(2, client.getLastname());
            ps.setString(3, client.getUsername());
            ps.setString(4, client.getPassword());
            ps.executeUpdate();
            ResultSet generatedId = ps.getGeneratedKeys();
            if (generatedId.next()) {
                return generatedId.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Client read(String username) {
        String readQuery = "SELECT * FROM clients WHERE username = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(readQuery);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Client(
                        rs.getInt("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            } else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Client read(Integer clientId) {
        String readQuery = "SELECT * FROM clients WHERE id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(readQuery);
            ps.setInt(1, clientId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Client(
                        rs.getInt("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Client> readAll() {
        List<Client> clients = new ArrayList<>();
        String readQuery = "SELECT * FROM clients;";
        try {
            PreparedStatement ps = connection.prepareStatement(readQuery);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                clients.add(new Client(
                                rs.getInt("id"),
                                rs.getString("firstname"),
                                rs.getString("lastname"),
                                rs.getString("username"),
                                rs.getString("password")
                        )
                );
            }
            return clients;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer update(Client client) {
        String updateStmt = "UPDATE clients " +
                "SET password = ?, firstname = ?, lastname = ? " +
                " WHERE username = ? RETURNING id;";
        try {
            PreparedStatement ps = connection.prepareStatement(updateStmt);
            ps.setString(1, client.getPassword());
            ps.setString(2, client.getFirstname());
            ps.setString(3, client.getLastname());
            ps.setString(4, client.getUsername());
            ps.executeUpdate();
            return ps.getResultSet().getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer update(String username, String password) {
        String passChangeStmt = "UPDATE clients SET password = ? WHERE username = ? RETURNING id;";
        {
            try {
                PreparedStatement ps = connection.prepareStatement(passChangeStmt);
                ps.setString(1, password);
                ps.setString(2, username);
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
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
    public void delete(Integer clientId) {
        String delStmt = "DELETE FROM clients WHERE id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(delStmt);
            ps.setInt(1, clientId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
