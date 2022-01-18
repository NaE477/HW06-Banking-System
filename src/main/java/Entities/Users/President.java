package Entities.Users;

import Entities.Things.Bank.Branch;
import Entities.Users.User;

import java.util.List;

public class President extends User {
    private Integer branch_id;
    private List<Integer> clerk_ids;
    private Double salary;

    public President(int userId, String firstname, String lastname,
                     String username, String password,
                     Integer branch_id, List<Integer> clerk_ids,Double salary) {

        super(userId, firstname, lastname, username, password);
        this.branch_id = branch_id;
        this.clerk_ids = clerk_ids;
        this.salary = salary;
    }

    public President(int userId, String firstname, String lastname,
                     String username, String password,
                     Integer branch_id,Double salary){
        super(userId, firstname, lastname, username, password);
        this.branch_id = branch_id;
        this.salary = salary;
    }
    public President(int userId,String username,String password){
        super(userId,username,password);
    }

    public Integer getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(Integer branch_id) {
        this.branch_id = branch_id;
    }

    public List<Integer> getClerk_ids() {
        return clerk_ids;
    }

    public void setClerk_ids(List<Integer> clerk_ids) {
        this.clerk_ids = clerk_ids;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "President ID: " + super.getUserId() +
                " Full Name: " + super.getFirstname()  + " " + super.getLastname() +
                " Username: " + super.getPassword() +
                " Branch ID: " + branch_id +
                " Salary: " + salary.intValue();
    }
}
