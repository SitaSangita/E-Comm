package sita.sangita.orderAPI.service;

import com.razorpay.Order;

public interface RazorPayService {
	public Order createPaymentOrder(double amount)throws Exception;
}
