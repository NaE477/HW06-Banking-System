package Repositories;

import Entities.Things.Customer.Card;
import Interfaces.ThingCRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CardsRep implements ThingCRUD<Card> {

    Connection connection;

    public CardsRep(Connection connection){
        this.connection = connection;
    }

    @Override
    public void create() {
        String createStmt = "CREATE TABLE IF NOT EXISTS cards (" +
                "id         SERIAL PRIMARY KEY ," +
                "card_number     CHAR(12)," +
                "first_pass      CHAR(4)," +
                "second_pass     CHAR(6)," +
                "bank_id        INTEGER," +
                "exp_date       DATE," +
                "account_id     INTEGER," +
                "FOREIGN KEY (bank_id) REFERENCES bank.public.banks(id)," +
                "FOREIGN KEY (account_id) REFERENCES bank.public.accounts(id)" +
                ")";
        try {
            PreparedStatement ps = connection.prepareStatement(createStmt);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer insert(Card o) {
        return null;
    }

    @Override
    public Card read(Card o) {
        return null;
    }

    @Override
    public Card read(Integer targetId) {
        return null;
    }

    @Override
    public Integer update(Card obj) {
        return null;
    }

    @Override
    public void delete(Card obj) {

    }
}
