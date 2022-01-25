package interfaces;

import java.util.List;

public interface Findable<O> {
    public O find(O o);
    public O findById(Integer id);
    public List<O> findAll();
}
