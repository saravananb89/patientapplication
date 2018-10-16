package com.example.patientcatalogue.service.device;

import com.example.patientcatalogue.data.device.DeviceDTO;
import com.example.patientcatalogue.data.device.DeviceDTORepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class DeviceServiceDatabase implements DeviceService {

    @Autowired
    private DeviceDTORepository deviceDTORepository;

    @Override
    public Optional<Device> get(String deviceName) {
        return deviceDTORepository.findById(deviceName).map(DeviceDTO::getDevice);
    }

    @Override
    public Set<Device> all() {
        Set<Device> set = new HashSet<>();
        deviceDTORepository.findAll().forEach(deviceDTO -> set.add(deviceDTO.getDevice()));
        return set;
    }

    @Override
    public String create(String deviceName, String host, Integer port) {
        DeviceDTO deviceDTO = new DeviceDTO(deviceName, host, port);
        return String.valueOf(deviceDTORepository.save(deviceDTO).getDeviceName());
    }

    @Override
    public boolean update(String deviceName, String host, Integer port) {
        DeviceDTO deviceDTO = new DeviceDTO(deviceName, host, port);
        deviceDTORepository.save(deviceDTO);
        return true;
    }

    @Override
    public boolean delete(String deviceName) {
        deviceDTORepository.deleteById(deviceName);
        return true;
    }

    @Override
    public void clear() {
        deviceDTORepository.deleteAll();
    }

}

