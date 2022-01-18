package Services;

import Entities.Things.Bank.Bank;
import Interfaces.Findable;
import Repositories.BanksRep;

import java.sql.Connection;

public class BanksService implements Findable<Bank> {

    Connection connection;
    BanksRep br;

    public BanksService(Connection connection){
        this.connection = connection;
        br = new BanksRep(connection);
    }

    @Override
    public Bank findById(Integer bankId) {
        return br.read(bankId);
    }

    @Override
    public Bank find(Bank bank) {
        return br.read(bank);
    }
}
