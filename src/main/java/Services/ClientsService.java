package Services;

import Entities.Users.Client.Client;
import Entities.Users.User;
import Interfaces.Authentic;
import Interfaces.Findable;
import Repositories.ClientsRep;

import java.sql.Connection;

public class ClientsService implements Authentic<Client>, Findable<Client> {
    Connection connection;
    ClientsRep cr;

    public ClientsService(Connection connection){
        this.connection = connection;
        cr = new ClientsRep(connection);
    }

    @Override
    public User login(String username) {
        return cr.read(username);
    }

    @Override
    public Integer signup(Client client) {
        return cr.insert(client);
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
    public Client find(Client client) {
        return cr.read(client.getUsername());
    }

    @Override
    public Client findById(Integer id) {
        return cr.read(id);
    }
}
