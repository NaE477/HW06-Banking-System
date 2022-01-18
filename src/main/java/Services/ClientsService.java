package Services;

import Entities.Users.Client;
import Entities.Users.User;
import Interfaces.Authentic;
import Interfaces.Findable;
import Repositories.ClientsRep;

import java.sql.Connection;
import java.util.List;

public class ClientsService implements Authentic<Client>, Findable<Client> {
    Connection connection;
    ClientsRep cr;

    public ClientsService(Connection connection){
        this.connection = connection;
        cr = new ClientsRep(connection);
    }

    public Integer remove(Integer clientId){
        if(exists(clientId)){
            cr.delete(clientId);
            return clientId;
        }
        else return 0;
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
    public Boolean exists(Integer id) {
        return cr.read(id) != null;
    }

    @Override
    public Client find(Client client) {
        return cr.read(client.getUsername());
    }

    @Override
    public Client findById(Integer id) {
        return cr.read(id);
    }

    @Override
    public List<Client> findAll() {
        return cr.readAll();
    }
}
