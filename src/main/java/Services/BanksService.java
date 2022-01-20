package Services;

import Entities.Things.Bank.Bank;
import Interfaces.AdminPermissionOnly;
import Interfaces.Findable;
import Repositories.BanksRep;

import java.sql.Connection;
import java.util.List;

public class BanksService implements Findable<Bank>, AdminPermissionOnly<Bank> {

    Connection connection;
    BanksRep br;

    public BanksService(Connection connection){
        this.connection = connection;
        br = new BanksRep(connection);
    }
    public Boolean exists(Integer bankId){
        return findById(bankId) != null;
    }

    @Override
    public Bank findById(Integer bankId) {
        return br.read(bankId);
    }

    @Override
    public List<Bank> findAll() {
        return br.readAll();
    }

    @Override
    public Bank find(Bank bank) {
        return br.read(bank);
    }

    @Override
    public Integer createNew(Bank bank) {
        return br.insert(bank);
    }

    @Override
    public Integer modify(Bank bank) {
        return br.update(bank);
    }
}
