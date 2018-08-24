package com.example.patientcatalogue.service.Role;

import java.util.Optional;
import java.util.Set;

public interface RoleService {

    /**
     * @param id
     * @return the patient with the id
     */
    Optional<Role> get(String id);

    /**
     * @return all the patients
     */
    Set<Role> all();

    /**
     *
     * @param roleName
     * @param patientAccess
     * @param visitAccess
     * @param userAccess
     * @param roleAccess
     * @return
     */
    String create(String roleName, Integer patientAccess, Integer visitAccess, Integer userAccess, Integer roleAccess);

    /**
     *
     * @param roleName
     * @param patientAccess
     * @param visitAccess
     * @param userAccess
     * @param roleAccess
     * @return
     */
    boolean update(String roleName, Integer patientAccess, Integer visitAccess, Integer userAccess, Integer roleAccess);

    /**
     *
     * @param roleName
     * @return
     */
    boolean delete(String roleName);

    /**
     *
     */
    void clear();

}
