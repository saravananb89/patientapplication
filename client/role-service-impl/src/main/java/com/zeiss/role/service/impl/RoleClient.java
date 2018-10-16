package com.zeiss.role.service.impl;

import com.zeiss.role.service.api.RoleDTO;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface RoleClient {
    @RequestLine("GET /role/{roleName}")
    RoleDTO getByRoleName(@Param("roleName") String roleName);

    @RequestLine("GET /role")
    List<RoleDTO> getAll();

    @RequestLine("POST /role")
    @Headers("Content-Type: application/json")
    void create(RoleDTO role);

    @RequestLine("DELETE /role/{roleName}")
    boolean delete(@Param("roleName") String roleName);

    @RequestLine("PUT /role/{roleName}")
    @Headers("Content-Type: application/json")
    void update(@Param("roleName") String roleName, RoleDTO role);

    @RequestLine("DELETE /role")
    void clear();
}
