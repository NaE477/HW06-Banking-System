package Entities.Users.Bank;

import Entities.Things.Bank.Branch;
import Entities.Users.User;

public class Clerk extends User {
    private int branch_id,president_id;
    private Double salary;

    public Clerk(int userId, String firstname, String lastname,
                 String username, String password,
                 int branch_id, int president_id,Double salary) {
        super(userId, firstname, lastname, username, password);
        this.branch_id = branch_id; this.president_id = president_id;
        this.salary = salary;
    }
    public Clerk(int userId,String firstname,String lastname,String username,String password,Double salary){
        super(userId,firstname,lastname,username,password);
        this.salary = salary;
    }

    public int getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(int branch_id) {
        this.branch_id = branch_id;
    }

    public int getPresident_id() {
        return president_id;
    }

    public void setPresident_id(int president_id) {
        this.president_id = president_id;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
