package com.zeiss.role.service.impl;

import com.zeiss.role.service.api.Access;
import com.zeiss.role.service.api.Role;
import com.zeiss.role.service.api.RoleDTO;
import com.zeiss.role.service.api.RoleService;
import feign.Feign;
import feign.httpclient.ApacheHttpClient;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

import java.util.ArrayList;
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

        List<RoleDTO> roleDTOS = roleClient.getAll();

        List<Role> roles = new ArrayList<>();

        roleDTOS.forEach(roleDTO -> {
            Role role = new RoleImpl(roleDTO.getRoleName(), Access.getAccess(roleDTO.getPatientAccess()), Access.getAccess(roleDTO.getVisitAccess())
                    , Access.getAccess(roleDTO.getUserAccess()), Access.getAccess(roleDTO.getRoleAccess()), Access.getAccess(roleDTO.getDeviceAccess()));
            roles.add(role);
        });
        return roles;
    }

    public boolean delete(Role role) {
        System.out.println("Role wurde gelöscht " + role);
        return roleClient.delete(role.getRoleName());
    }

    public void create(Role role) {
        System.out.println("role is created " + role);
        RoleDTO roleDTO = new RoleDTO(role.getRoleName(), role.getPatientAccess().getRepresentation(), role.getVisitAccess().getRepresentation(),
                role.getUserAccess().getRepresentation(), role.getRoleAccess().getRepresentation(), role.getDeviceAccess().getRepresentation());
        roleClient.create(roleDTO);
    }

    public void update(Role role) {
        System.out.println("role is updated " + role);
        RoleDTO roleDTO = new RoleDTO(role.getRoleName(), role.getPatientAccess().getRepresentation(), role.getVisitAccess().getRepresentation(),
                role.getUserAccess().getRepresentation(), role.getRoleAccess().getRepresentation(), role.getDeviceAccess().getRepresentation());
        roleClient.update(role.getRoleName(), roleDTO);
    }

    public Role getByRoleName(String roleName) {
        RoleDTO roleDTO = roleClient.getByRoleName(roleName);
        Role role = new RoleImpl(roleDTO.getRoleName(), Access.getAccess(roleDTO.getPatientAccess()), Access.getAccess(roleDTO.getVisitAccess())
                , Access.getAccess(roleDTO.getUserAccess()), Access.getAccess(roleDTO.getRoleAccess()), Access.getAccess(roleDTO.getDeviceAccess()));
        return role;
    }

    @Override
    public void clear() {
        roleClient.clear();
    }

}
