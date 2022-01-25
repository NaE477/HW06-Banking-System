package interfaces;

import entities.users.User;

public interface Authentic<T> {
    User login(String username);
    Integer signup(T t);
    Boolean authentication(String username, String password);
    Boolean exists(String username);
    Boolean exists(Integer id);
}
