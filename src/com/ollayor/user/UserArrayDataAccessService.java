package com.ollayor.user;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserArrayDataAccessService implements UserDAO{

    private static final List<User> users = new ArrayList<>();

    static {
        users.add(new User(UUID.fromString("8ca51d2b-aaaf-4bf2-834a-e02964e10fc3"), "James"));
        users.add(new User(UUID.fromString("b10d126a-3608-4980-9f9c-aa179f5cebc3"), "Jamila"));
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public User findUserById(UUID id){
      return getUsers()
              .stream()
              .filter(
                      user -> user.getId().equals(id))
              .findFirst()
              .orElseThrow(
                      () -> new IllegalArgumentException("User Not Found"));
    }
}
