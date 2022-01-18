package Interfaces;

public interface AdminPermissionOnly<O> {
    public Integer createNew(O o);
    public Integer modify(O o);
}
