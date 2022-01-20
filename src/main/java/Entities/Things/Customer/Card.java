package Entities.Things.Customer;

import java.sql.Date;
import java.time.YearMonth;

public class Card {
    private int id,cvv2,firstPass,secondPass;
    private Account account;
    private YearMonth expDate;
    private String number;
    private CardStatus cardStatus;

    public Card(int id, int cvv2, int firstPass, int secondPass, Account account, YearMonth expDate, String number) {
        this.id = id;
        this.cvv2 = cvv2;
        this.firstPass = firstPass;
        this.secondPass = secondPass;
        this.account = account;
        this.expDate = expDate;
        this.number = number;
        this.cardStatus = CardStatus.BLOCKED;
    }

    public Card(int id, int cvv2, int firstPass, int secondPass, Account account, YearMonth expDate, String number, CardStatus cardStatus) {
        this.id = id;
        this.cvv2 = cvv2;
        this.firstPass = firstPass;
        this.secondPass = secondPass;
        this.account = account;
        this.expDate = expDate;
        this.number = number;
        this.cardStatus = cardStatus;
    }

    public Card(int cardId) {
        this.id = cardId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCvv2() {
        return cvv2;
    }

    public void setCvv2(int cvv2) {
        this.cvv2 = cvv2;
    }

    public int getFirstPass() {
        return firstPass;
    }

    public void setFirstPass(int firstPass) {
        this.firstPass = firstPass;
    }

    public int getSecondPass() {
        return secondPass;
    }

    public void setSecondPass(int secondPass) {
        this.secondPass = secondPass;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public YearMonth getExpDate() {
        YearMonth expDate = YearMonth.of(this.expDate.getYear(),this.expDate.getMonthValue());
        return expDate;
    }

    public void setExpDate(YearMonth expDate) {
        this.expDate = expDate;
    }

    public CardStatus getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(CardStatus cardStatus) {
        this.cardStatus = cardStatus;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                " \n-CVV2: " + cvv2 +
                " \n-First Pass: " + firstPass +
                " \n-Second Pass:" + secondPass +
                " \n-EXP. Date: " + expDate +
                " \n-CardNumber: " + number +
                " \n-Card Status: " + cardStatus;
    }
}
