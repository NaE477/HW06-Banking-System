package Services;

import Entities.Things.Bank.Branch;
import Interfaces.AdminPermissionOnly;
import Interfaces.Findable;
import Repositories.BranchesRep;

import java.sql.Connection;
import java.util.List;

public class BranchesService implements Findable<Branch>, AdminPermissionOnly<Branch> {

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
    public List<Branch> findAll() {
        return br.readAll();
    }

    @Override
    public Branch find(Branch branch) {
        return br.read(branch);
    }

    @Override
    public Integer createNew(Branch branch) {
        return br.insert(branch);
    }

    @Override
    public Integer modify(Branch branch) {
        return br.update(branch);
    }
}
