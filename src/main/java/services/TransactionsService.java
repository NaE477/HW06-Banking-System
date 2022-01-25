package services;

import entities.things.Transaction;
import entities.things.TransactionStatus;
import entities.things.TransactionType;
import interfaces.Findable;
import repositories.TransactionToCardRep;
import repositories.TransactionsRep;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

public class TransactionsService implements Findable<Transaction> {
    Connection connection;
    TransactionsRep tr;
    TransactionToCardRep ttcr;

    public TransactionsService(Connection connection) {
        this.connection = connection;
        tr = new TransactionsRep(this.connection);
        ttcr = new TransactionToCardRep(this.connection);
    }

    public Integer makeNew(Transaction transaction){
        return tr.insert(transaction);
    }

    public Integer getDone(Transaction transaction){
        Integer updatedTransaction = tr.update(transaction);
        if(transaction.getTransactionStatus() == TransactionStatus.DONE) {
            ttcr.insertDes(transaction, TransactionType.WITHDRAW);
            ttcr.insertSrc(transaction,TransactionType.DEPOSIT);
        }
        return updatedTransaction;
    }

    public List<Object> findAllByBranch(Integer branchId){
        return tr.readByBranchId(branchId);
    }

    public List<Transaction> findAllByAccount(Integer accountId){
        return tr.readAllByAccountId(accountId);
    }

    public List<Transaction> findAllByCardAndDate(Integer cardId, Timestamp from){
        return tr.readAllByAccountAndDate(cardId,from);
    }

    public List<Transaction> findAllByClient(Integer clientId){
        return tr.readAllByClient(clientId);
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
