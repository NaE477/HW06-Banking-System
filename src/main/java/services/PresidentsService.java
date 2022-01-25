package services;

import entities.users.Clerk;
import entities.users.President;
import entities.users.User;
import interfaces.Authentic;
import interfaces.Findable;
import repositories.PresidentsRep;

import java.sql.Connection;
import java.util.List;

public class PresidentsService implements Authentic<President>, Findable<President> {
    Connection connection;
    PresidentsRep pr;

    public PresidentsService(Connection connection){
        this.connection = connection;
        pr = new PresidentsRep(connection);
    }

    public List<Clerk> findClerks(President president){
        return pr.readClerks(president);
    }

    @Override
    public User login(String username) {
        return pr.read(username);
    }

    @Override
    public Integer signup(President president) {
        return pr.insert(president);
    }

    @Override
    public Boolean authentication(String username, String password) {
        User user = pr.read(username);
        return user != null && user.getPassword().equals(password);
    }


    @Override
    public Boolean exists(String username) {
        return pr.read(username) != null;
    }

    @Override
    public Boolean exists(Integer id) {
        return pr.read(id) != null;
    }

    @Override
    public President find(President president) {
        return pr.read(president.getUsername());
    }

    @Override
    public President findById(Integer id) {
        return pr.read(id);
    }

    @Override
    public List<President> findAll() {
        return pr.readAll();
    }
}
