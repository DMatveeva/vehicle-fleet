package ru.dmatveeva.to;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class VehicleTo {

    @JsonProperty
    private Integer id;

    @JsonProperty
    private Integer model_id;

    @JsonProperty
    private String vin;

    @JsonProperty
    private BigDecimal costUsd;

    @JsonProperty
    private String color;

    @JsonProperty
    private int mileage;

    @JsonProperty
    private int productionYear;

    @JsonProperty
    private Integer enterprise_id;

    public VehicleTo(Integer id, Integer model_id, String vin, BigDecimal costUsd, String color, int mileage, int productionYear, Integer enterprise_id) {
        this.id = id;
        this.model_id = model_id;
        this.vin = vin;
        this.costUsd = costUsd;
        this.color = color;
        this.mileage = mileage;
        this.productionYear = productionYear;
        this.enterprise_id = enterprise_id;
    }

    @Override
    public String toString() {
        return "VehicleTo{" +
                "id=" + id +
                ", model_id=" + model_id +
                ", vin='" + vin + '\'' +
                ", costUsd=" + costUsd +
                ", color='" + color + '\'' +
                ", mileage=" + mileage +
                ", productionYear=" + productionYear +
                ", enterprise_id=" + enterprise_id +
                '}';
    }
}
