package com.yjjk.monitor.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ZsHealthInfo implements Serializable {
    private Integer temperatureId;

    private String machineId;

    private BigDecimal heartRate;

    private BigDecimal respiratoryRate;

    private Date createTime;

    private Long timestamp;

    private static final long serialVersionUID = 1L;

    public Integer getTemperatureId() {
        return temperatureId;
    }

    public void setTemperatureId(Integer temperatureId) {
        this.temperatureId = temperatureId;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId == null ? null : machineId.trim();
    }

    public BigDecimal getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(BigDecimal heartRate) {
        this.heartRate = heartRate;
    }

    public BigDecimal getRespiratoryRate() {
        return respiratoryRate;
    }

    public void setRespiratoryRate(BigDecimal respiratoryRate) {
        this.respiratoryRate = respiratoryRate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", temperatureId=").append(temperatureId);
        sb.append(", machineId=").append(machineId);
        sb.append(", heartRate=").append(heartRate);
        sb.append(", respiratoryRate=").append(respiratoryRate);
        sb.append(", createTime=").append(createTime);
        sb.append(", timestamp=").append(timestamp);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}