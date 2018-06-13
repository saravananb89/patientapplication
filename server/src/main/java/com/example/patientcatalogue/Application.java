package com.example.patientcatalogue;

import com.example.patientcatalogue.service.patient.PatientRepository;
import com.example.patientcatalogue.service.patient.PatientRepositoryDatabase;
import com.example.patientcatalogue.service.visit.PatientVisitRepository;
import com.example.patientcatalogue.service.visit.PatientVisitRepositoryDatabase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    PatientRepository patientRepository() {
        return new PatientRepositoryDatabase();
    }

    @Bean
    PatientVisitRepository patientVisitRepository() {
        return new PatientVisitRepositoryDatabase();
    }
}
