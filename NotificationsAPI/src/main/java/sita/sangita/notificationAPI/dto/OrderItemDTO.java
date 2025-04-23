package sita.sangita.notificationAPI.dto;


import lombok.Data;

@Data
public class OrderItemDTO {

	private Long id;
	private int quantity;
	private double unitPrice;
	private String imageUrl;
	
	private String productName;
}
