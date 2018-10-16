package com.zeiss.settings.service.impl;

import com.zeiss.settings.service.api.LocaleService;
import com.zeiss.user.service.api.User;

import java.util.Locale;

public class LocaleServiceImpl implements LocaleService {

    private Locale locale = Locale.GERMANY;

    private User user;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public User getLoggedInUser() {
        return user;
    }

    @Override
    public void setLoggedInUser(User user) {
        this.user = user;
    }


}
