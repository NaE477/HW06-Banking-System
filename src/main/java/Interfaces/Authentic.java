package Interfaces;

import Entities.Users.User;

public interface Authentic {
    User authentication(String username, String password);
}
