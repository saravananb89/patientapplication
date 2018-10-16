package com.zeiss.patient.client.app;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.zeiss.device.service.api.Device;
import com.zeiss.device.service.api.DeviceService;
import com.zeiss.device.service.impl.DefaultDeviceService;
import com.zeiss.device.service.impl.DeviceImpl;
import com.zeiss.document.service.api.Document;
import com.zeiss.document.service.api.DocumentService;
import com.zeiss.document.service.impl.DefaultDocumentService;
import com.zeiss.document.service.impl.DocumentImpl;
import com.zeiss.patient.client.gui.GuiStarter;
import com.zeiss.patient.service.api.Patient;
import com.zeiss.patient.service.api.PatientService;
import com.zeiss.patient.service.api.PatientVisit;
import com.zeiss.patient.service.impl.DefaultPatientService;
import com.zeiss.patient.service.impl.PatientImpl;
import com.zeiss.patient.service.impl.PatientVisitImpl;
import com.zeiss.plan.service.api.PlanService;
import com.zeiss.plan.service.api.PlanningUnit;
import com.zeiss.plan.service.impl.DefaultPlanService;
import com.zeiss.plan.service.impl.PlanningUnitImpl;
import com.zeiss.role.service.api.Role;
import com.zeiss.role.service.api.RoleService;
import com.zeiss.role.service.impl.DefaultRoleService;
import com.zeiss.role.service.impl.RoleImpl;
import com.zeiss.settings.service.api.LocaleService;
import com.zeiss.settings.service.impl.LocaleServiceImpl;
import com.zeiss.user.service.api.User;
import com.zeiss.user.service.api.UserService;
import com.zeiss.user.service.impl.DefaultUserService;
import com.zeiss.user.service.impl.UserImpl;

public class InjectionModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PatientService.class).to(DefaultPatientService.class).in(Singleton.class);
        bind(LocaleService.class).to(LocaleServiceImpl.class).in(Singleton.class);
        bind(GuiStarter.class).in(Singleton.class);
        bind(Patient.class).to(PatientImpl.class);
        bind(PatientVisit.class).to(PatientVisitImpl.class);
        bind(UserService.class).to(DefaultUserService.class).in(Singleton.class);
        bind(User.class).to(UserImpl.class);
        bind(RoleService.class).to(DefaultRoleService.class).in(Singleton.class);
        bind(Role.class).to(RoleImpl.class);
        bind(DocumentService.class).to(DefaultDocumentService.class).in(Singleton.class);
        bind(Document.class).to(DocumentImpl.class);
        bind(DeviceService.class).to(DefaultDeviceService.class).in(Singleton.class);
        bind(Device.class).to(DeviceImpl.class);
        bind(PlanService.class).to(DefaultPlanService.class).in(Singleton.class);
        bind(PlanningUnit.class).to(PlanningUnitImpl.class);

    }
}
