package com.zeiss.device.service.api;

import java.util.List;

public interface DeviceService {
    List<? extends Device> getDevices();

    boolean delete(Device device);

    void create(Device device);

    void update(Device device);

    Device getByDeviceName(String deviceName);

    void clear();
}
