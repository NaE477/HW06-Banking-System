package Services;

import Entities.Users.Clerk;
import Entities.Users.User;
import Interfaces.Authentic;
import Interfaces.Findable;
import Repositories.ClerksRep;

import java.sql.Connection;
import java.util.List;

public class ClerksService implements Authentic<Clerk>, Findable<Clerk> {
    Connection connection;
    ClerksRep cr;

    public ClerksService(Connection connection){
        this.connection = connection;
        cr = new ClerksRep(connection);
    }
    public Integer fire(Integer clerkId){
        if(exists(clerkId)) {
            cr.delete(clerkId);
            return clerkId;
        }
        else return 0;
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
    public Boolean exists(Integer id) {
        return cr.read(id) != null;
    }

    @Override
    public Clerk find(Clerk clerk){
        return cr.read(clerk.getUsername());
    }

    @Override
    public Clerk findById(Integer clerkId){
        return cr.read(clerkId);
    }

    @Override
    public List<Clerk> findAll() {
        return cr.readAll();
    }
}
