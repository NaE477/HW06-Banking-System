package interfaces;

import java.util.List;

public interface ThingCRUD<O> {

    public void create();
    public Integer insert(O o);
    public O read(O o);
    public O read(Integer targetId);
    public List<O> readAll();
    public Integer update(O obj);
    public void delete(O obj);
}
