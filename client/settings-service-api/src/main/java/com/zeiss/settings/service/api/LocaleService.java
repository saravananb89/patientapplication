package com.zeiss.settings.service.api;

import com.zeiss.user.service.api.User;

import java.util.Locale;

public interface LocaleService {

    Locale getLocale();

    void setLocale(Locale locale);

    User getLoggedInUser();

    void setLoggedInUser(User user);
}
