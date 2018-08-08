package com.zeiss.patient.client.gui.delete;

import com.zeiss.user.service.api.User;
import com.zeiss.user.service.api.UserService;

public class UserDeletion {
    public static boolean showUserDeletionDialog(UserService userService, User user, Runnable runnable){
        boolean b = userService.delete(user);
        runnable.run();
        return b;
    }
}
