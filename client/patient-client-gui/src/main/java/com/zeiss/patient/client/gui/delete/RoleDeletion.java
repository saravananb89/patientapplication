package com.zeiss.patient.client.gui.delete;

import com.zeiss.role.service.api.Role;
import com.zeiss.role.service.api.RoleService;

public class RoleDeletion {
    public static boolean showRoleDeletionDialog(RoleService roleService, Role role, Runnable runnable) {
        boolean b = roleService.delete(role);
        runnable.run();
        return b;
    }
}
