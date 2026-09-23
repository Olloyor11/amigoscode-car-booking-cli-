package com.ollayor.user;

import java.util.UUID;
//d3a7e5f8-1c55-4b9d-93e8-8c2f6a4b1010
public class UserDAO {

    private static final User[] users;

    static {
        users = new User[]{
                new User(UUID.fromString("8ca51d2b-aaaf-4bf2-834a-e02964e10fc3"), "James"),
                new User(UUID.fromString("b10d126a-3608-4980-9f9c-aa179f5cebc3"), "Jamila")
        };
    }

    public User[] getUsers() {
        return users;
    }

}
