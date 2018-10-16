package com.zeiss.patient.client.gui.delete;

import com.zeiss.device.service.api.Device;
import com.zeiss.device.service.api.DeviceService;

public class DeviceDeletion {
    public static boolean showDeletionDeletionDialog(DeviceService deviceService,
                                                     Device device, Runnable runnable) {
        boolean b = deviceService.delete(device);
        runnable.run();
        return b;
    }
}
