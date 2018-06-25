package com.zeiss.patient.client.app;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.zeiss.patient.client.gui.GuiStarter;
import com.zeiss.patient.service.impl.DefaultPatientService;
import com.zeiss.patient.service.api.PatientService;
import com.zeiss.patient.client.gui.localeservice.LocaleService;

public class InjectionModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PatientService.class).to(DefaultPatientService.class).in(Singleton.class);
        bind(LocaleService.class).in(Singleton.class);
        bind(GuiStarter.class).in(Singleton.class);
    }
}
