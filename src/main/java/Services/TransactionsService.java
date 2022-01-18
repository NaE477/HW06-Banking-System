package Services;

import Entities.Things.Transaction;
import Interfaces.Findable;
import Interfaces.ThingCRUD;
import Repositories.TransactionsRep;

import java.sql.Connection;

public class TransactionsService implements Findable<Transaction> {
    Connection connection;
    TransactionsRep tr;

    public TransactionsService(Connection connection) {
        this.connection = connection;
        tr = new TransactionsRep(connection);
    }

    public Integer makeNew(Transaction transaction){
        return tr.insert(transaction);
    }

    public Integer getDone(Transaction transaction){
        return tr.update(transaction);
    }


    @Override
    public Transaction findById(Integer transactionId) {
        return tr.read(transactionId);
    }

    @Override
    public Transaction find(Transaction transaction) {
        return tr.read(transaction);
    }
}
