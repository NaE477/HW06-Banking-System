package Entities.Users;

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
    public Clerk(String firstname,String lastname,String username,String password,int branch_id, int president_id,Double salary){
        super(firstname,lastname,username,password);
        this.salary = salary;
        this.branch_id = branch_id; this.president_id = president_id;
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

    @Override
    public String toString() {
        return "Clerk ID: " + super.getUserId() +
                " First Name: " + super.getFirstname() +
                " Last Name: " + super.getLastname() +
                " Branch ID: " + branch_id +
                " President ID: " + president_id +
                " Salary=" + salary;
    }
}
