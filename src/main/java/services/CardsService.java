package services;

import entities.things.customer.Card;
import entities.things.customer.CardStatus;
import entities.users.Client;
import interfaces.Findable;
import repositories.CardsRep;

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
    public Integer modifyPassAndStatus(Card card){
        return cr.update(card);
    }


    public Boolean exists(Integer cardId){
        return cr.read(cardId) != null;
    }
    public Boolean cardNumExists(String cardNumber){
        return cr.readByNumber(cardNumber) != null;
    }

    public Boolean cardNumAvailable(String cardNumber){
        return cr.readByNumber(cardNumber) != null && cr.readByNumber(cardNumber).getCardStatus() == CardStatus.OPEN;
    }

    public Boolean cardBelongsToClient(Card card, Client client){
         List<Card>cards = cr.readAllByClient(client.getUserId());
         for(Card thisCard : cards){
             if(card.getId() == thisCard.getId()){
                 return true;
             }
         }
         return false;
    }

    public Boolean existsForClient(Integer clientId,Integer cardId){
        return findByClient(clientId,cardId) != null;
    }
    public Boolean hasCard(Integer accountId){return findByAccount(accountId) != null;}

    public List<Card> findAllByClient(Integer clientId) {
        return cr.readAllByClient(clientId);
    }

    public Card findByClient(Integer clientId,Integer cardId){
        return cr.readByClient(clientId,cardId);
    }
    public Card findByNumber(String cardNumber){
        return cr.readByNumber(cardNumber);
    }
    public Card findByAccount(Integer accountId){
        return cr.readByAccount(accountId);
    }

    public Boolean firstPassAuthenticate(Integer cardId,Integer firstPass){
        Integer correctPass = cr.read(cardId).getFirstPass();
        return correctPass.equals(firstPass);
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
    public Integer removeByAccountAndBranch(Integer accountId,Integer branchId){
        return cr.deleteByAccountAndBranch(accountId,branchId);
    }
    public Integer removeByAccountId(Integer accountId){
        return cr.deleteByAccount(accountId);
    }
    public String removeById(Integer cardId){
        return cr.deleteById(cardId);
    }
    public Integer removeByCardNumber(String cardNumber){
        return cr.deleteByNumber(cardNumber);
    }

}
