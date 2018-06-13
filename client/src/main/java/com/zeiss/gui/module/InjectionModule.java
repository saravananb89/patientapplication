package com.zeiss.gui.module;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.zeiss.gui.data.DefaultPatientService;
import com.zeiss.gui.data.PatientService;
import com.zeiss.gui.localeservice.LocaleService;

public class InjectionModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PatientService.class).to(DefaultPatientService.class).in(Singleton.class);
        bind(LocaleService.class).in(Singleton.class);
    }
}
