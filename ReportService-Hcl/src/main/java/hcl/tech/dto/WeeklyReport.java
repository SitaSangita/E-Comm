package hcl.tech.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;
@Data
public class WeeklyReport {
    private List<DailyReport> dailyReports;
    private LocalDate startDate;
    private LocalDate endDate;
   }
