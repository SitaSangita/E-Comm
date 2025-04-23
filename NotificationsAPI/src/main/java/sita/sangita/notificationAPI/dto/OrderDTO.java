package sita.sangita.notificationAPI.dto;


import java.time.LocalDate;

import lombok.Data;

@Data
public class OrderDTO {

	private Integer orderId;
	private String orderTrackingNum;
	private String email;
	private String razorPayOrderId;
	private String orderStatus;
	private Double totalPrice;
	private Integer totalQuantity;
	private String razorPayPaymentId;
	private LocalDate deliveryDate;

	private String invoiceUrl;

}
