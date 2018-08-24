package com.zeiss.role.service.impl;

import com.zeiss.role.service.api.Role;
import com.zeiss.role.service.api.RoleService;
import feign.Feign;
import feign.httpclient.ApacheHttpClient;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

import java.util.List;

public class DefaultRoleService implements RoleService {

    private RoleClient roleClient;

    public DefaultRoleService() {
        roleClient = Feign.builder().client(new ApacheHttpClient())
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(RoleClient.class, "http://localhost:1234");

    }


    public List<? extends Role> getRoles() {

        return roleClient.getAll();
    }

    public boolean delete(Role role) {
        System.out.println("Role wurde gelöscht " + role);
        return roleClient.delete(role.getRoleName());
    }

    public void create(Role role) {
        System.out.println("role is created " + role);
        roleClient.create(role);
    }

    public void update(Role role) {
        System.out.println("role is updated " + role);
        roleClient.update(role.getRoleName(), role);
    }

    public Role getByRoleName(String roleName) {
        return roleClient.getByRoleName(roleName);
    }

    @Override
    public void clear() {
        roleClient.clear();
    }

}
