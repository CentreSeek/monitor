package com.yjjk.monitor.entity;

import java.io.Serializable;
import java.util.Date;

public class ZsEcgInfo implements Serializable {
    private Integer temperatureId;

    private String machineId;

    private Date createTime;

    private Long timestamp;

    private String ecg;

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

    public String getEcg() {
        return ecg;
    }

    public void setEcg(String ecg) {
        this.ecg = ecg == null ? null : ecg.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", temperatureId=").append(temperatureId);
        sb.append(", machineId=").append(machineId);
        sb.append(", createTime=").append(createTime);
        sb.append(", timestamp=").append(timestamp);
        sb.append(", ecg=").append(ecg);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}