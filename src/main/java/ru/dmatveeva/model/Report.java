package ru.dmatveeva.model;

import java.time.LocalDate;
import java.util.Map;

public class Report extends AbstractBaseEntity{

    public String type;

    String period;

    private LocalDate start;

    private LocalDate end;

    private Map<String, Integer> periodToValue;

    public Report() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public Map<String, Integer> getPeriodToValue() {
        return periodToValue;
    }

    public void setPeriodToValue(Map<String, Integer> periodToValue) {
        this.periodToValue = periodToValue;
    }
}
