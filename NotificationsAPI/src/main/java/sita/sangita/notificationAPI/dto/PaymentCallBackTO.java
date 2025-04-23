package sita.sangita.notificationAPI.dto;


import lombok.Data;

@Data
public class PaymentCallBackTO {

	
	private String razorPayOrderId;
	
	private String razorPayPaymentId;
	
	private String razorPaySignature;
}
