package com.zeiss.device.service.api;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public interface Device {

    void setDeviceName(String deviceName);

    void setHost(String host);

    void setPort(int port);

    String getDeviceName();

    StringProperty deviceNameProperty();

    String getHost();

    StringProperty hostProperty();

    Integer getPort();

    IntegerProperty portProperty();

}
