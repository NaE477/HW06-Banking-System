package Services;

import Entities.Users.Bank.President;
import Entities.Users.User;
import Interfaces.Authentic;
import Interfaces.Findable;
import Repositories.PresidentsRep;

import java.sql.Connection;

public class PresidentsService implements Authentic<President>, Findable<President> {
    Connection connection;
    PresidentsRep pr;

    public PresidentsService(Connection connection){
        this.connection = connection;
        pr = new PresidentsRep(connection);
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
    public President find(President president) {
        return pr.read(president.getUsername());
    }

    @Override
    public President findById(Integer id) {
        return pr.read(id);
    }
}
