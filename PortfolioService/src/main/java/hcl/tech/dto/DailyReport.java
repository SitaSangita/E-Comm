package hcl.tech.dto;

import java.time.LocalDate;

import lombok.Data;
@Data
public class DailyReport {
    private double totalInvestment;
    private int totalStocks;
    private LocalDate date;
   
}
