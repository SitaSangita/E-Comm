package hlc.tech.UserPortFolio.dto;

import lombok.Data;

@Data
public class StockDto {
    private String code;
    private String name;
    private double price;
    private String range;
    private double change;
}