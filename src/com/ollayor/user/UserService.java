package com.ollayor.user;

import java.util.UUID;

public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User[] getAllUsers(){
        return userDAO.getUsers();
    }

    public User findUserById(UUID id){
        for (User user : userDAO.getUsers()) {
            if (user.getId().equals(id)){
                return user;
            }
        }
        throw new IllegalArgumentException("User Not Found");
    }
}
