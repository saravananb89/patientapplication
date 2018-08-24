package com.zeiss.role.service.api;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public interface Role {

    void setRoleName(String roleName);

    void setPatientTabAccess(Integer patientTabAccess);

    void setVisitTabAccess(Integer visitTabAccess);

    void setUserTabAccess(Integer userTabAccess);

    void setRoleTabAccess(Integer roleTabAccess);

    String getRoleName();

    StringProperty roleNameProperty();

    Integer getPatientTabAccess();

    IntegerProperty patientTabAccessProperty();

    Integer getVisitTabAccess();

    IntegerProperty visitTabAccessProperty();

    Integer getUserTabAccess();

    IntegerProperty userTabAccessProperty();

    Integer getRoleTabAccess();

    IntegerProperty roleTabAccessProperty();

}
