package services;

import entities.things.bank.Bank;
import interfaces.AdminPermissionOnly;
import interfaces.Findable;
import repositories.BanksRep;

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

}
