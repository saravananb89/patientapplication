package com.zeiss.device.service.impl;

import com.zeiss.device.service.api.Device;
import com.zeiss.device.service.api.DeviceService;
import feign.Feign;
import feign.httpclient.ApacheHttpClient;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

import java.util.List;

public class DefaultDeviceService implements DeviceService {

    private DeviceClient deviceClient;

    public DefaultDeviceService() {
        deviceClient = Feign.builder().client(new ApacheHttpClient())
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(DeviceClient.class, "http://localhost:1234");

    }


    public List<? extends Device> getDevices() {

        return deviceClient.getAll();
    }

    public boolean delete(Device device) {
        System.out.println("Device wurde gelöscht " + device);
        return deviceClient.delete(device.getDeviceName());
    }

    public void create(Device device) {
        System.out.println("device is created " + device);
        deviceClient.create(device);
    }

    public void update(Device device) {
        System.out.println("device is updated " + device);
        deviceClient.update(device.getDeviceName(), device);
    }

    public Device getByDeviceName(String deviceName) {
        return deviceClient.getByDeviceName(deviceName);
    }

    @Override
    public void clear() {
        deviceClient.clear();
    }

}
