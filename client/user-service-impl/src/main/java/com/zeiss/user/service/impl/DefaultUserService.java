package com.zeiss.user.service.impl;

import com.google.inject.Inject;
import com.zeiss.role.service.api.Role;
import com.zeiss.role.service.api.RoleService;
import com.zeiss.user.service.api.User;
import com.zeiss.user.service.api.UserService;
import feign.Feign;
import feign.httpclient.ApacheHttpClient;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

import java.util.List;

public class DefaultUserService implements UserService {

    private UserClient userClient;

    private static final User NOUSER = new NoUser();

    @Inject
    private RoleService roleService;

    public DefaultUserService() {
        userClient = Feign.builder().client(new ApacheHttpClient())
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(UserClient.class, "http://localhost:1234");

    }


    public List<? extends User> getUsers() {

        return userClient.getAll();
    }

    @Override
    public User getNoUser() {
        return NOUSER;
    }

    public boolean delete(User user) {
        System.out.println("User wurde gelöscht " + user);
        return userClient.delete(user.getUserName());
    }

    public void create(User user) {
        System.out.println("user is created " + user);
        userClient.create(user);
    }

    public void update(User user) {
        System.out.println("user is updated " + user);
        userClient.update(user.getUserName(), user);
    }

    public List<? extends User> getUsersByUserName(String userName) {

        List<UserImpl> usersByUserName = userClient.getUsersByUserName(userName);

        usersByUserName.forEach(user -> {
            Role role = roleService.getByRoleName(user.getRole());
            user.setRoleType(role);
        });

        return usersByUserName;
    }

    public List<? extends User> getUsersByUserNameAndPassword(String userName, String password) {

        List<UserImpl> usersByUserNameAndPassword = userClient.getUsersByUserNameAndPassword(userName, password);

        usersByUserNameAndPassword.forEach(user -> {
            Role role = roleService.getByRoleName(user.getRole());
            user.setRoleType(role);
        });

        return usersByUserNameAndPassword;
    }


    @Override
    public void clear() {
        userClient.clear();
    }

}
