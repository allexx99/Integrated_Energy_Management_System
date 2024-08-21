package com.example.DS2023_30244_Usuc_Alexandru_2_Backend3.entities;

import com.example.DS2023_30244_Usuc_Alexandru_2_Backend3.dtos.MeasurementDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column
    private String timestamp;

    @NotNull
    @Column
    private double measurement_value;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public double getMeasurement_value() {
        return measurement_value;
    }

    public void setMeasurement_value(double measurement_value) {
        this.measurement_value = measurement_value;
    }

    public MeasurementDTO convertToDTO(Measurement measurement) {
        MeasurementDTO measurementDTO = new MeasurementDTO();
        measurementDTO.setId(measurement.getId());
        measurementDTO.setTimestamp(measurement.getTimestamp());
        measurementDTO.setMeasurement_value(measurement.getMeasurement_value());
        return measurementDTO;
    }
}
