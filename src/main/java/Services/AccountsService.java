package Services;

import Entities.Things.Customer.Account;
import Interfaces.Findable;
import Repositories.AccountsRep;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;

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

    public Boolean exists(Integer accountId){
        return ar.read(accountId) != null;
    }

    public Boolean existsForClient(Integer clientId){
        List<Account> accounts = findAllByClient(clientId);
        for(Account account : accounts){
            if(account.getClient().getUserId() == clientId){
                return true;
            }
        }
        return false;
    }
    public Boolean existsForClient(Integer clientId,Integer accountId){
        List<Account> accounts = findAllByClient(clientId);
        for(Account account : accounts){
            if(account.getId() == accountId){
                return true;
            }
        }
        return false;
    }

    public Boolean existsInBranch(Integer branchId,Integer accountId){
        List<Account> accounts = ar.readAllByBranch(branchId);
        for(Account account : accounts){
            if(account.getId() == accountId) return true;
        }
        return false;
    }

    public void closeAccount(Account account){
        ar.delete(account);
    }

    public List<Account> findAllByBranch(Integer branchId) {
        return ar.readAllByBranch(branchId);
    }



    public List<Account> findAllByClient(Integer clientId){
        return ar.readAllByClient(clientId);
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

    public Integer doTransaction(Account account){
        return ar.update(account);
    }

}
