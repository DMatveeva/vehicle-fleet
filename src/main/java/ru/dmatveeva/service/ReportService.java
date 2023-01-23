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

    public Map<String, Integer> getReportResults(int vehicle_id, LocalDate start, LocalDate end) {
        return jdbcVehicleRepository.getReportResultsForYear(vehicle_id, start, end);
    }
}
