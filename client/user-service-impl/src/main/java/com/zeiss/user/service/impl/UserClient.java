package com.zeiss.user.service.impl;

import com.zeiss.user.service.api.User;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface UserClient {
    @RequestLine("GET /user/{userName}")
    UserImpl getByUserName(@Param("userName") String userName);

    @RequestLine("GET /user")
    List<UserImpl> getAll();

    @RequestLine("POST /user")
    @Headers("Content-Type: application/json")
    void create(User user);

    @RequestLine("DELETE /user/{userName}")
    boolean delete(@Param("userName") String userName);

    @RequestLine("PUT /user/{userName}")
    @Headers("Content-Type: application/json")
    void update(@Param("userName") String userName, User user);

    @RequestLine("GET /user/search/{userName}")
    List<UserImpl> getUsersByUserName(@Param("userName") String userName);

    @RequestLine("GET /user/search/{userName}/{password}")
    List<UserImpl> getUsersByUserNameAndPassword(@Param("userName") String userName, @Param("password") String password);

    @RequestLine("DELETE /user")
    void clear();
}
