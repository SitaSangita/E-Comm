package sita.sangita.orderAPI.service;

import java.util.List;

import sita.sangita.orderAPI.dto.OrderDTO;
import sita.sangita.orderAPI.dto.PaymentCallBackTO;
import sita.sangita.orderAPI.request.PurchesOrderRequest;
import sita.sangita.orderAPI.response.PurchaseOrderResponse;

public interface OrderService {

public PurchaseOrderResponse createOrder(PurchesOrderRequest orderRequest) throws Exception;
	
	public List<OrderDTO> getOrdersByEmail(String email);
	
	public PurchaseOrderResponse updateOrder(PaymentCallBackTO paymentCallBackTO);
	
}
