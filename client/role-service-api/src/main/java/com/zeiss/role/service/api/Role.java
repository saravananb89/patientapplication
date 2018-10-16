package com.zeiss.role.service.api;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

public interface Role {

    void setRoleName(String roleName);

    void setPatientAccess(Access patientAccess);

    void setVisitAccess(Access visitAccess);

    void setUserAccess(Access userAccess);

    void setRoleAccess(Access roleAccess);

    void setDeviceAccess(Access deviceAccess);

    String getRoleName();

    StringProperty roleNameProperty();

    Access getPatientAccess();

    ObjectProperty<Access> patientAccessProperty();

    Access getVisitAccess();

    ObjectProperty<Access>  visitAccessProperty();

    Access getUserAccess();

    ObjectProperty<Access>  userAccessProperty();

    Access getRoleAccess();

    ObjectProperty<Access>  roleAccessProperty();

    Access getDeviceAccess();

    ObjectProperty<Access>  deviceAccessProperty();

}
