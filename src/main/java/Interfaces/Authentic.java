package Interfaces;

import Entities.Users.User;

public interface Authentic<T> {
    User login(String username);
    Integer signup(T t);
    Boolean authentication(String username, String password);
    Boolean exists(String username);
}
