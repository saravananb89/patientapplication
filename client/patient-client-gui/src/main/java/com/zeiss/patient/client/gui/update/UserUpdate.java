package com.zeiss.patient.client.gui.update;

import com.zeiss.patient.client.gui.create.UserCreation;
import com.zeiss.user.service.api.User;
import com.zeiss.user.service.api.UserService;

public class UserUpdate extends UserCreation {
    protected void save(UserService userService, User user) {
        userService.update(user);
    }

}
