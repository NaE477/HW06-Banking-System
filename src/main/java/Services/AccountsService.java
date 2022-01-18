package Services;

import Entities.Things.Customer.Account;
import Interfaces.Findable;
import Repositories.AccountsRep;

import java.sql.Connection;
import java.util.List;

public class AccountsService implements Findable<Account> {
    Connection connection;
    AccountsRep ar;

    public AccountsService(Connection connection){
        this.connection = connection;
        ar = new AccountsRep(this.connection);
    }

    public Integer open(Account account){
        return ar.insert(account);
    }

    @Override
    public Account find(Account account) {
        return ar.read(account);
    }

    @Override
    public Account findById(Integer id) {
        return ar.read(id);
    }

    @Override
    public List<Account> findAll() {
        return ar.readAll();
    }
}
