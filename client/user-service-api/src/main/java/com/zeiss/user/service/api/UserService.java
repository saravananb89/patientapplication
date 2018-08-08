package com.zeiss.user.service.api;

import java.util.List;

public interface UserService {
    List<? extends User> getUsers();

    boolean delete(User user);

    void create(User user);

    void update(User user);

    List<? extends User> getUsersByUserName(String userName);

    List<? extends User> getUsersByUserNameAndPassword(String userName, String password);

    void clear();
}
