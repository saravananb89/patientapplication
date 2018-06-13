package com.zeiss.gui.localeservice;

import java.util.Locale;

public class LocaleService {

    private Locale locale = Locale.GERMANY;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
