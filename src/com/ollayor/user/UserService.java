package com.ollayor.user;


import java.util.UUID;

public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User[] getAllUsers() {
        return userDAO.getUsers();
    }

    public User findUserById(UUID id){
        User user = userDAO.findUserById(id);

        if (user == null){
            throw new IllegalArgumentException("User Not Found");
        }
        return user;
    }

}
