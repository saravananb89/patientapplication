package com.example.patientcatalogue.data.visit;

import com.example.patientcatalogue.service.patient.Patient;
import com.example.patientcatalogue.service.visit.PatientVisit;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name="patientvisittable")
public class PatientVisitDTO {

    @Column(name="id") @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    @Column(name="firstName")
    private String firstName;
    @Column(name="lastName")
    private String lastName;
    @Column(name="visitDate")
    private LocalDate visitDate;

    public PatientVisitDTO() {
    }

    public PatientVisitDTO(Integer id, String firstName, String lastName, LocalDate visitDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.visitDate = visitDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    public PatientVisit getPatientVisit(){
        return new PatientVisit(String.valueOf(id),firstName,lastName,visitDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientVisitDTO that = (PatientVisitDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PatientVisitDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", visitDate='" + visitDate + '\'' +
                '}';
    }
}
