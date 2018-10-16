package com.example.patientcatalogue.service.device;

import java.util.Optional;
import java.util.Set;

public interface DeviceService {

    /**
     *
     * @param id
     * @return
     */
    Optional<Device> get(String id);

    /**
     *
     * @return
     */
    Set<Device> all();

    /**
     *
     * @param deviceName
     * @param host
     * @param port
     * @return
     */
    String create(String deviceName, String host, Integer port);

    /**
     *
     * @param deviceName
     * @param host
     * @param port
     * @return
     */
    boolean update(String deviceName, String host, Integer port);

    /**
     *
     * @param deviceName
     * @return
     */
    boolean delete(String deviceName);

    /**
     *
     */
    void clear();

}
