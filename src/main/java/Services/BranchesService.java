package Services;

import Entities.Things.Bank.Branch;
import Interfaces.Findable;
import Interfaces.ThingCRUD;
import Repositories.BranchesRep;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BranchesService implements Findable<Branch> {

    Connection connection;
    BranchesRep br;
    public BranchesService(Connection connection){
        this.connection = connection;
        br = new BranchesRep(connection);
    }

    @Override
    public Branch findById(Integer id) {
        return br.read(id);
    }

    @Override
    public Branch find(Branch branch) {
        return br.read(branch);
    }
}
