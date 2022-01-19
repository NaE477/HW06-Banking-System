package Services;

import Entities.Things.Transaction;
import Interfaces.Findable;
import Repositories.TransactionsRep;

import java.sql.Connection;
import java.util.List;

public class TransactionsService implements Findable<Transaction> {
    Connection connection;
    TransactionsRep tr;

    public TransactionsService(Connection connection) {
        this.connection = connection;
        tr = new TransactionsRep(this.connection);
    }

    public Integer makeNew(Transaction transaction){
        return tr.insert(transaction);
    }

    public Integer getDone(Transaction transaction){
        return tr.update(transaction);
    }

    public List<Object> findAllByBranch(Integer branchId){
        return tr.readByBranchId(branchId);
    }

    @Override
    public Transaction findById(Integer transactionId) {
        return tr.read(transactionId);
    }

    @Override
    public List<Transaction> findAll() {
        return tr.readAll();
    }

    @Override
    public Transaction find(Transaction transaction) {
        return tr.read(transaction);
    }
}
