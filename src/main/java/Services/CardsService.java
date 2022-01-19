package Services;

import Entities.Things.Customer.Card;
import Interfaces.Findable;
import Repositories.CardsRep;

import java.sql.Connection;
import java.util.List;

public class CardsService implements Findable<Card> {
    Connection connection;
    CardsRep cr;

    public CardsService(Connection connection){
        this.connection = connection;
        cr = new CardsRep(connection);
    }

    //only done when making a new account by clerk
    public Integer createNew(Card card){
        return cr.insert(card);
    }

    //only done by client
    public Integer modifyPasswords(Card card){
        return cr.update(card);
    }

    public Integer removeByAccountAndBranch(Integer accountId,Integer branchId){
        return cr.deleteByAccountAndBranch(accountId,branchId);
    }
    public Integer removeByAccount(Integer accountId){
        return cr.deleteByAccount(accountId);
    }
    public String removeById(Integer cardId){
        return cr.deleteById(cardId);
    }
    public Integer removeByCardNumber(String cardNumber){
        return cr.deleteByNumber(cardNumber);
    }

    public Boolean existsForClient(Integer clientId,Integer cardId){
        return findByClient(clientId,cardId) != null;
    }

    public List<Card> findAllByClient(Integer clientId) {
        return cr.readAllByClient(clientId);
    }

    public Card findByClient(Integer clientId,Integer cardId){
        return cr.readByClient(clientId,cardId);
    }
    public Card findByNumber(String cardNumber){
        return cr.readByNumber(cardNumber);
    }

    @Override
    public Card findById(Integer cardId) {
        return cr.read(cardId);
    }

    @Override
    public List<Card> findAll() {
        return cr.readAll();
    }

    @Override
    public Card find(Card card) {
        return cr.read(card);
    }
}
