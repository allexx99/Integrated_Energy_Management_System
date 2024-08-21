package com.example.DS2023_30244_Usuc_Alexandru_2_Backend3.entities;

import com.example.DS2023_30244_Usuc_Alexandru_2_Backend3.dtos.DeviceDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import jakarta.xml.bind.annotation.XmlTransient;


import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Device {

    @Id
    private long id;

    @NotNull
    @Column
    private String name;

    @XmlTransient
    @OneToMany(targetEntity = Measurement.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "idDevice", referencedColumnName = "id")
    @NotNull
    private List<Measurement> measurementList;

    // @NotNull
    @Column
    private long userId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Measurement> getMeasurementList() {
        return measurementList;
    }

    public void setMeasurementList(List<Measurement> measurementList) {
        this.measurementList = measurementList;
    }

    public DeviceDTO convertToDTO(Device device) {
        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setId(device.getId());
        deviceDTO.setName(device.getName());
        deviceDTO.setMeasurementList(device.getMeasurementList());
        deviceDTO.setUserId(device.getUserId());
        return deviceDTO;
    }
}
