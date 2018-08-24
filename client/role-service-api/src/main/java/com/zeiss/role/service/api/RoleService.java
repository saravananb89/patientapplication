package com.zeiss.role.service.api;

import java.util.List;

public interface RoleService {
    List<? extends Role> getRoles();

    boolean delete(Role role);

    void create(Role role);

    void update(Role role);

    Role getByRoleName(String roleName);


    void clear();
}
