package services;

import entities.users.Client;
import entities.users.User;
import interfaces.Authentic;
import interfaces.Findable;
import repositories.ClientsRep;

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

    public List<Client> findAllByBranch(Integer branchId){
        return cr.readAllByBranch(branchId);
    }

    public Boolean existsInBranch(Integer branchId,Integer clientId){
        List<Client> clients = cr.readAllByBranch(branchId);
        for(Client client : clients){
            if(client.getUserId() == clientId){
                return true;
            }
        }
        return false;
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
