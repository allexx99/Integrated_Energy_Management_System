package com.example.DS2023_30244_Usuc_Alexandru_2_Backend3.dtos;

import com.example.DS2023_30244_Usuc_Alexandru_2_Backend3.entities.Measurement;

public class MeasurementDTO {

    private long id;

    private String timestamp;

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

    public Measurement convertToEntity(MeasurementDTO measurementDTO) {
        Measurement measurement = new Measurement();
        measurement.setId(measurementDTO.getId());
        measurement.setTimestamp(measurementDTO.getTimestamp());
        measurement.setMeasurement_value(measurementDTO.getMeasurement_value());
        return measurement;
    }
}
