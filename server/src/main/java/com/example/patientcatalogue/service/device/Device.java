package com.example.patientcatalogue.service.device;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class Device {

    private String deviceName;
    private String host;
    private Integer port;

}
