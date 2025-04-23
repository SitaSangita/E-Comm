package hcl.tech.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import hcl.tech.dto.DailyReport;
import hcl.tech.dto.WeeklyReport;
import hcl.tech.entity.StockEntry;
import hcl.tech.repo.StockEntryRepository;
import hcl.tech.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/portfolio/report")
public class ReportController {

    @Autowired private StockEntryRepository repo;
    @Autowired 
    private JwtUtil jwtUtil;

    @GetMapping("/welcome")
   	public String getWelcome() {
   		String msg="Welcome to UserAPI......";
   		return msg;
   	}
    private String getUserEmail(HttpServletRequest req) {
        String token = req.getHeader("Authorization").substring(7);
        return jwtUtil.extractEmail(token);
    }

    @GetMapping("/daily")
    public DailyReport getTodayReport(HttpServletRequest req) {
        String email = getUserEmail(req);
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.plusDays(1).atStartOfDay();

        List<StockEntry> stocks = repo.findByUserEmailAndAddedAtBetween(email, start, end);

        double total = 0;
        for (StockEntry s : stocks) total += s.getPurchasePrice() * s.getQuantity();

        DailyReport report = new DailyReport();
        report.setDate(today);
        report.setTotalInvestment(total);
        report.setTotalStocks(stocks.size());

        return report;
    }

    @GetMapping("/weekly")
    public WeeklyReport getWeeklyReport(HttpServletRequest req) {
        String email = getUserEmail(req);
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(6);
        List<DailyReport> list = new ArrayList<>();

        for (int i = 0; i <= 6; i++) {
            LocalDate date = startDate.plusDays(i);
            LocalDateTime start = date.atStartOfDay();
            LocalDateTime end = date.plusDays(1).atStartOfDay();
            List<StockEntry> stocks = repo.findByUserEmailAndAddedAtBetween(email, start, end);

            double total = 0;
            for (StockEntry s : stocks) total += s.getPurchasePrice() * s.getQuantity();

            DailyReport r = new DailyReport();
            r.setDate(date);
            r.setTotalStocks(stocks.size());
            r.setTotalInvestment(total);

            list.add(r);
        }

        WeeklyReport report = new WeeklyReport();
        report.setDailyReports(list);
        report.setStartDate(startDate);
        report.setEndDate(endDate);

        return report;
    }
    @GetMapping(value = "/daily/csv", produces = "text/csv")
    public void downloadDailyCSV(HttpServletResponse response, HttpServletRequest req) throws IOException {
        String email = getUserEmail(req);
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.plusDays(1).atStartOfDay();

        List<StockEntry> stocks = repo.findByUserEmailAndAddedAtBetween(email, start, end);

        response.setHeader("Content-Disposition", "attachment; filename=daily_report.csv");

        PrintWriter writer = response.getWriter();
        CSVPrinter csv = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("Symbol", "Quantity", "Price", "Total"));

        for (StockEntry s : stocks) {
            double total = s.getQuantity() * s.getPurchasePrice();
            csv.printRecord(s.getSymbol(), s.getQuantity(), s.getPurchasePrice(), total);
        }

        csv.flush();
        csv.close();
    }

    @GetMapping(value = "/weekly/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public void downloadWeeklyPDF(HttpServletResponse response, HttpServletRequest req) throws Exception {
        String email = getUserEmail(req);
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(6);

        List<DailyReport> reports = new ArrayList<>();
        for (int i = 0; i <= 6; i++) {
            LocalDate date = startDate.plusDays(i);
            LocalDateTime start = date.atStartOfDay();
            LocalDateTime end = date.plusDays(1).atStartOfDay();
            List<StockEntry> stocks = repo.findByUserEmailAndAddedAtBetween(email, start, end);
            double total = 0;
            for (StockEntry s : stocks) total += s.getQuantity() * s.getPurchasePrice();

            DailyReport r = new DailyReport();
            r.setDate(date);
            r.setTotalInvestment(total);
            r.setTotalStocks(stocks.size());
            reports.add(r);
        }

        response.setHeader("Content-Disposition", "attachment; filename=weekly_report.pdf");

        Document doc = new Document();
        PdfWriter.getInstance(doc, response.getOutputStream());
        doc.open();
        doc.add(new Paragraph("Weekly Report (" + startDate + " to " + endDate + ")"));
        doc.add(new Paragraph(" "));

        for (DailyReport r : reports) {
            doc.add(new Paragraph(
                    r.getDate() + " - Stocks: " + r.getTotalStocks() + ", Investment: ₹" + r.getTotalInvestment()));
        }

        doc.close();
    }

}
