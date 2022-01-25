package services;

import entities.things.bank.Branch;
import interfaces.AdminPermissionOnly;
import interfaces.Findable;
import repositories.BranchesRep;

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

}
