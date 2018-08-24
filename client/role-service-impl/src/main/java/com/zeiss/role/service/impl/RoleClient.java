package com.zeiss.role.service.impl;

import com.zeiss.role.service.api.Role;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface RoleClient {
    @RequestLine("GET /role/{roleName}")
    RoleImpl getByRoleName(@Param("roleName") String roleName);

    @RequestLine("GET /role")
    List<RoleImpl> getAll();

    @RequestLine("POST /role")
    @Headers("Content-Type: application/json")
    void create(Role role);

    @RequestLine("DELETE /role/{roleName}")
    boolean delete(@Param("roleName") String roleName);

    @RequestLine("PUT /role/{roleName}")
    @Headers("Content-Type: application/json")
    void update(@Param("roleName") String roleName, Role role);

    @RequestLine("DELETE /role")
    void clear();
}
