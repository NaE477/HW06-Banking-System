package Repositories;

import Entities.Things.Transaction;
import Interfaces.CRUD;

import java.sql.Connection;

public class TransactionsRep implements CRUD<Transaction> {
    Connection connection;

    public TransactionsRep(Connection connection){
        this.connection = connection;
    }

    @Override
    public void create() {

    }

    @Override
    public void read(Transaction transaction) {

    }

    @Override
    public void update(Transaction transaction) {

    }

    @Override
    public void delete(Transaction transaction) {

    }
}
