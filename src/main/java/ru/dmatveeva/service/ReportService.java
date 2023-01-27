package ru.dmatveeva.service;

import org.springframework.stereotype.Service;
import ru.dmatveeva.repository.jdbc.JdbcVehicleRepository;

import java.time.LocalDate;
import java.util.Map;

@Service
public class ReportService {

    JdbcVehicleRepository jdbcVehicleRepository;

    public ReportService(JdbcVehicleRepository jdbcVehicleRepository) {
        this.jdbcVehicleRepository = jdbcVehicleRepository;
    }

    public Map<String, Integer> getReportResultsForYear(int vehicle_id, LocalDate start, LocalDate end) {
        return jdbcVehicleRepository.getReportResultsForYear(vehicle_id, start, end);
    }

    public Map<String, Integer> getReportResultsForMonth(int vehicle_id, LocalDate start, LocalDate end) {
        return jdbcVehicleRepository.getReportResultsForMonth(vehicle_id, start, end);
    }

    public Map<String, Integer> getReportResultsForDay(int vehicle_id, LocalDate start, LocalDate end) {
        return jdbcVehicleRepository.getReportResultsForDay(vehicle_id, start, end);
    }
}
