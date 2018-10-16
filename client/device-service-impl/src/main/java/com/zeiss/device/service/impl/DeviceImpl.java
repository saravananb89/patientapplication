package com.zeiss.device.service.impl;

import com.zeiss.device.service.api.Device;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

public class DeviceImpl implements Device {

    private final StringProperty deviceName = new SimpleStringProperty();
    private final StringProperty host = new SimpleStringProperty();
    private final IntegerProperty port = new SimpleIntegerProperty();

    DeviceImpl(String deviceName, String host, int port) {
        this.deviceName.setValue(deviceName);
        this.host.setValue(host);
        this.port.setValue(port);
    }

    public DeviceImpl() {
    }

    public String getDeviceName() {
        return deviceName.get();
    }

    public StringProperty deviceNameProperty() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName.set(deviceName);
    }

    public String getHost() {
        return host.get();
    }

    public StringProperty hostProperty() {
        return host;
    }

    public void setHost(String host) {
        this.host.set(host);
    }

    public Integer getPort() {
        return port.get();
    }

    public IntegerProperty portProperty() {
        return port;
    }

    public void setPort(int port) {
        this.port.set(port);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceImpl device = (DeviceImpl) o;
        return Objects.equals(deviceName, device.deviceName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(deviceName);
    }

    @Override
    public String toString() {
        return "DeviceImpl{" +
                "deviceName=" + deviceName +
                ", host=" + host +
                ", port=" + port +
                '}';
    }
}
