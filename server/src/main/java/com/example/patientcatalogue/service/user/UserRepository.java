package com.example.patientcatalogue.service.user;

import com.example.patientcatalogue.service.patient.Patient;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository {


    /**
     *
     * @param userName
     * @return
     */
    Optional<User> get(String userName);

    /**
     * @return all the patients
     */
    Set<User> all();

    /**
     *
     * @param userName
     * @param password
     * @param preferredLocale
     * @param lastLogin
     * @return
     */
    String create(String userName, String password, String preferredLocale, LocalDate lastLogin);

    /**
     *
     * @param userName
     * @param password
     * @param preferredLocale
     * @param lastLogin
     * @return
     */
    boolean update(String userName, String password, String preferredLocale, LocalDate lastLogin);

    /**
     *
     * @param userName
     * @return
     */
    boolean delete(String userName);

    /**
     *
     */
    void clear();

    List<User> findByUserName(String userName);

    /**
     *
     * @param userName
     * @param password
     * @return
     */
    List<User> findByUserNameAndPassword(String userName, String password);
}
