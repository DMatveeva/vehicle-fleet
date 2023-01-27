package ru.dmatveeva.web.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dmatveeva.to.MileageReport;
import ru.dmatveeva.to.Report;
import ru.dmatveeva.service.ReportService;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping(value = RestReportController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestReportController {

    static final String REST_URL = "/rest/reports";

    ReportService reportService;

    public RestReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/generate")
    public Report generate(@RequestBody MileageReport requestReport) {
        String period = requestReport.getPeriod();
        LocalDate start = requestReport.getStart();
        LocalDate end = requestReport.getEnd();
        LocalDate newStart;
        LocalDate newEnd;
        Map<String, Integer> reportResults;
        if (period.equals("year")) {
            newStart = LocalDate.of(start.getYear(), 1, 1);
            newEnd = LocalDate.of(end.getYear(), 12, 31);
            reportResults = reportService.getReportResultsForYear(requestReport.getVehicleId(), newStart, newEnd);
        } else if (period.equals("month")) {
            newStart = LocalDate.of(start.getYear(), start.getMonth(), 1);
            int lastDayOfMonth = end.getMonth().length(end.isLeapYear());
            newEnd = LocalDate.of(end.getYear(), end.getMonth(), lastDayOfMonth);
            reportResults = reportService.getReportResultsForMonth(requestReport.getVehicleId(), newStart, newEnd);
        } else if (period.equals("day")) {
            newStart = start;
            newEnd = end;
            reportResults = reportService.getReportResultsForDay(requestReport.getVehicleId(), newStart, newEnd);

        } else {
            throw new IllegalArgumentException("specify period : year, month, day)");
        }
        Report report = new MileageReport(reportResults, requestReport.getType(), requestReport.getPeriod(),
                newStart, newEnd, requestReport.getVehicleId());
        report.setPeriodToValueKm(reportResults);
        return report;
    }
}
