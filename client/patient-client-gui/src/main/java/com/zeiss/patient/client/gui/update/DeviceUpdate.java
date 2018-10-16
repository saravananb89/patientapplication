package com.zeiss.patient.client.gui.update;

import com.zeiss.device.service.api.Device;
import com.zeiss.device.service.api.DeviceService;
import com.zeiss.patient.client.gui.create.DeviceCreation;

public class DeviceUpdate extends DeviceCreation {
    protected void save(DeviceService deviceService, Device device) {
        deviceService.update(device);
    }
}
