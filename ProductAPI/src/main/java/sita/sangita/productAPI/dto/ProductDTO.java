package sita.sangita.productAPI.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductDTO {
    private Long productId;
    private String name;
    private String description;
    private BigDecimal unitPrice;
    private Integer unitsStack;
    private String category;
    private String imageUrl;
    private String title;
    private Boolean active;
    private Integer categoryId;
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;

}