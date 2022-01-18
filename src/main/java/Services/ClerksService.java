package Services;

import Entities.Users.Bank.Clerk;
import Entities.Users.User;
import Interfaces.Authentic;
import Interfaces.Findable;
import Repositories.ClerksRep;

import java.sql.Connection;

public class ClerksService implements Authentic<Clerk>, Findable<Clerk> {
    Connection connection;
    ClerksRep cr;

    public ClerksService(Connection connection){
        this.connection = connection;
        cr = new ClerksRep(connection);
    }

    @Override
    public User login(String username) {
        return cr.read(username);
    }

    @Override
    public Integer signup(Clerk clerk) {
        return cr.insert(clerk);
    }

    @Override
    public Boolean authentication(String username, String password) {
        User user = cr.read(username);
        return user != null && user.getPassword().equals(password);
    }

    @Override
    public Boolean exists(String username) {
        return cr.read(username) != null;
    }

    @Override
    public Clerk find(Clerk clerk){
        return cr.read(clerk.getUsername());
    }

    @Override
    public Clerk findById(Integer clerkId){
        return cr.read(clerkId);
    }
}
