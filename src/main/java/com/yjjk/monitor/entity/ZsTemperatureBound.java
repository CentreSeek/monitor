package com.yjjk.monitor.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ZsTemperatureBound {
    private Integer departmentId;

    private Double lowTemperature;

    private Double normalTemperature;

    private Double highTemperature;

    private Integer temperatureAlert;

    private Double lowAlert;

    private Double highAlert;

    private Integer managerId;

    private String changeTime;

    private Integer status;

}