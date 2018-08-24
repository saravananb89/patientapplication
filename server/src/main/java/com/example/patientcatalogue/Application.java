package com.example.patientcatalogue;

import com.example.patientcatalogue.data.document.DocumentDTORepository;
import com.example.patientcatalogue.data.document.DocumentDTORepositoryInterface;
import com.example.patientcatalogue.service.Role.RoleService;
import com.example.patientcatalogue.service.Role.RoleServiceDatabase;
import com.example.patientcatalogue.service.document.DocumentService;
import com.example.patientcatalogue.service.document.FileBasedDocumentService;
import com.example.patientcatalogue.service.patient.PatientService;
import com.example.patientcatalogue.service.patient.PatientServiceDatabase;
import com.example.patientcatalogue.service.user.UserService;
import com.example.patientcatalogue.service.user.UserServiceDatabase;
import com.example.patientcatalogue.service.visit.PatientVisitService;
import com.example.patientcatalogue.service.visit.PatientVisitServiceDatabase;
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
    PatientService patientRepository() {
        return new PatientServiceDatabase();
    }

    @Bean
    PatientVisitService patientVisitRepository() {
        return new PatientVisitServiceDatabase();
    }

    @Bean
    UserService userRepository() {
        return new UserServiceDatabase();
    }

    @Bean
    DocumentService documentRepository() {
        return new FileBasedDocumentService();
    }

    @Bean
    DocumentDTORepositoryInterface documentDTORepositoryInterface() {
        return new DocumentDTORepository();
    }

    @Bean
    RoleService roleRepository() {
        return new RoleServiceDatabase();
    }

}
