package ru.dmatveeva.to;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Map;

public class MileageReport extends Report {

    @JsonProperty
    public int vehicleId;

    public MileageReport(@JsonProperty("periodToValue") final Map<String, Integer> periodToValue,
                         @JsonProperty("type") final String type,
                         @JsonProperty("period") final String period,
                         @JsonProperty("start") final LocalDate start,
                         @JsonProperty("end") final LocalDate end,
                         @JsonProperty("vehicleId") final int vehicleId) {
        super(periodToValue, type, period, start, end);
        this.vehicleId = vehicleId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }


}
