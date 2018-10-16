package com.zeiss.device.service.impl;

import com.zeiss.device.service.api.Device;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface DeviceClient {
    @RequestLine("GET /device/{deviceName}")
    DeviceImpl getByDeviceName(@Param("deviceName") String deviceName);

    @RequestLine("GET /device")
    List<DeviceImpl> getAll();

    @RequestLine("POST /device")
    @Headers("Content-Type: application/json")
    void create(Device device);

    @RequestLine("DELETE /device/{deviceName}")
    boolean delete(@Param("deviceName") String deviceName);

    @RequestLine("PUT /device/{deviceName}")
    @Headers("Content-Type: application/json")
    void update(@Param("deviceName") String deviceName, Device device);

    @RequestLine("DELETE /device")
    void clear();
}
