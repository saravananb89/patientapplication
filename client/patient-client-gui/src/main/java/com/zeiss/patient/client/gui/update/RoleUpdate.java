package com.zeiss.patient.client.gui.update;

import com.zeiss.patient.client.gui.create.RoleCreation;
import com.zeiss.role.service.api.Role;
import com.zeiss.role.service.api.RoleService;

public class RoleUpdate extends RoleCreation {
    protected void save(RoleService roleService, Role role) {
        roleService.update(role);
    }

}
