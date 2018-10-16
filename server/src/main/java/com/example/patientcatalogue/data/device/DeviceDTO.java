package com.example.patientcatalogue.data.device;

import com.example.patientcatalogue.service.device.Device;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "devicetable")
public class DeviceDTO {
    @Column(name = "deviceName", length = 20)
    @Id
    private String deviceName;
    @Column(name = "host")
    private String host;
    @Column(name = "port")
    private Integer port;

    public DeviceDTO(String deviceName, String host, Integer port) {
        this.deviceName = deviceName;
        this.host = host;
        this.port = port;
    }

    public DeviceDTO() {
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Device getDevice() {
        return new Device(deviceName, host, port);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceDTO deviceDTO = (DeviceDTO) o;
        return Objects.equals(deviceName, deviceDTO.deviceName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(deviceName);
    }

    @Override
    public String toString() {
        return "PlaningUnitDTO{" +
                "deviceName='" + deviceName + '\'' +
                ", host='" + host + '\'' +
                ", port='" + port + '\'' +
                '}';
    }
}
