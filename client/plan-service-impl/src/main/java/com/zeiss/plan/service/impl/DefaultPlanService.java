package com.zeiss.plan.service.impl;

import com.google.inject.Inject;
import com.zeiss.device.service.api.Device;
import com.zeiss.device.service.api.DeviceService;
import com.zeiss.patient.service.api.Patient;
import com.zeiss.patient.service.api.PatientService;
import com.zeiss.plan.service.api.PlanService;
import com.zeiss.plan.service.api.Planning;
import com.zeiss.plan.service.api.PlanningUnit;
import feign.Feign;
import feign.httpclient.ApacheHttpClient;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DefaultPlanService implements PlanService {

    private PlanClient planClient;
    @Inject
    private PatientService patientService;
    @Inject
    private DeviceService deviceService;

    public DefaultPlanService() {

        planClient = Feign.builder().client(new ApacheHttpClient())
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(PlanClient.class, "http://localhost:1234");

    }


    public void create(PlanningUnit planningUnit) {
        System.out.println("device is created " + planningUnit);
        PlanningImpl planningImpl = new PlanningImpl(planningUnit.getId(), planningUnit.getPatient().getId(),
                planningUnit.getDevice().getDeviceName(),
                planningUnit.getPlanTime(), planningUnit.getPlanDate().toString());
        planClient.create(planningImpl);
    }

    @Override
    public List<? extends PlanningUnit> getPatientPlanningUnitsByDeviceAndDate(String deviceId, LocalDate date) {

        final List<? extends Planning> planningUnits = planClient.getPatientPlanningUnitsByDeviceAndDate(deviceId, date.toString());

        List<PlanningUnit> planningUnitList = new ArrayList<>();

        planningUnits.forEach(planningDTO -> {
            PlanningUnit planUnit = new PlanningUnitImpl();
            planUnit.setPlanTime(planningDTO.getPlanTime());
            planUnit.setPlanDate(LocalDate.parse(planningDTO.getPlanDate()));
            Patient patient = patientService.getPatientsById(planningDTO.getPatientId());
            planUnit.setFirstName(patient.getFirstName());
            planUnit.setLastName(patient.getLastName());
            planUnit.setPatient(patient);
            planUnit.setId(planningDTO.getId());
            Device device = deviceService.getByDeviceName(planningDTO.getDeviceId());
            planUnit.setDevice(device);
            planningUnitList.add(planUnit);
        });

        return planningUnitList;
    }

    public boolean delete(PlanningUnit planningUnit) {
        System.out.println("device is created " + planningUnit);
        return planClient.deletePlan(planningUnit.getId());
    }

    public void update(PlanningUnit planningUnit) {
        System.out.println("device is updated " + planningUnit);
        PlanningImpl planningImpl = new PlanningImpl(planningUnit.getId(), planningUnit.getPatient().getId(),
                planningUnit.getDevice().getDeviceName(),
                planningUnit.getPlanTime(), planningUnit.getPlanDate().toString());
        planClient.update(planningImpl.getId(), planningImpl);
    }


    @Override
    public void clear() {
        planClient.clear();
    }

}
