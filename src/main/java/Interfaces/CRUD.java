package Interfaces;

public interface CRUD<R> {
    public void create();
    public void read(R r);
    public void update(R r);
    public void delete(R r);
}
