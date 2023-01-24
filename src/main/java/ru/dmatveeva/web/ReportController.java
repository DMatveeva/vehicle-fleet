package ru.dmatveeva.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.dmatveeva.model.vehicle.Vehicle;
import ru.dmatveeva.service.ReportService;
import ru.dmatveeva.to.MileageReport;
import ru.dmatveeva.to.Report;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Controller
@RequestMapping("/reports")

public class ReportController {

    ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/generate")
    public String generate(@RequestParam("type") String type,
                           @RequestParam("period") String period,
                           @RequestParam("start") String start,
                           @RequestParam("end") String end,
                           @RequestParam("vehicleId") String vehicleIdS,
                           Model model) {
        LocalDate startDate = getLocalDateFromString(start);
        LocalDate endDate = getLocalDateFromString(end);
        int vehicleId = Integer.parseInt(vehicleIdS);

        LocalDate newStart;
        LocalDate newEnd;
        if (period.equals("year")) {
            newStart = LocalDate.of(startDate.getYear(), 1, 1);
            newEnd = LocalDate.of(endDate.getYear(), 12, 31);
        } else if (period.equals("month")) {
            newStart = LocalDate.of(startDate.getYear(), startDate.getMonth(), 1);
            int lastDayOfMonth = endDate.getMonth().length(endDate.isLeapYear());
            newEnd = LocalDate.of(endDate.getYear(), endDate.getMonth(), lastDayOfMonth);
        } else if (period.equals("day")) {
            newStart = startDate;
            newEnd = endDate;
        } else {
            throw new IllegalArgumentException("specify period : year, month, day)");
        }

        Map<String, Integer> reportResults = reportService.getReportResults(vehicleId, newStart, newEnd);
        model.addAttribute("reportResults", reportResults);

        return "report.html";
    }

    // mm-dd-yyyy
    private LocalDate getLocalDateFromString(String s) {
        String[] arrStart = s.split("/");
        int month = Integer.parseInt(arrStart[0]);
        int day = Integer.parseInt(arrStart[1]);
        int year = Integer.parseInt(arrStart[2]);
        return LocalDate.of(year, month, day);
    }

}
