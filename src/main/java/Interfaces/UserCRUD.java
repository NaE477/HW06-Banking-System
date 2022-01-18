package Interfaces;

public interface UserCRUD<O> {
    public void insert();
    public Integer insert(O o);
    public O read(String target);
    public O read(Integer targetId);
    public Integer update(O obj);
    public Integer update(String username,String password);
    public void delete(Integer id);
}
