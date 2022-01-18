package Interfaces;

public interface Findable<O> {
    public O findById(Integer id);
    public O find(O o);
}
