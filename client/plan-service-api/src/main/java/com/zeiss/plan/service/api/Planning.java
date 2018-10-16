package com.zeiss.plan.service.api;

import javafx.beans.property.StringProperty;

public interface Planning {

    String getId();

    StringProperty idProperty();

    void setId(String id);

    void setPatientId(String patientId);

    void setDeviceId(String deviceId);

    void setPlanTime(String planTime);

    void setPlanDate(String planDate);

    String getPlanTime();

    StringProperty planTimeProperty();

    String getDeviceId();

    StringProperty deviceIdProperty();

    String getPatientId();

    StringProperty patientIdProperty();

    String getPlanDate();

    StringProperty planDateProperty();

}
