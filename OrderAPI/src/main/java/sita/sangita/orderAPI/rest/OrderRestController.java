package sita.sangita.orderAPI.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sita.sangita.orderAPI.dto.OrderDTO;
import sita.sangita.orderAPI.dto.PaymentCallBackTO;
import sita.sangita.orderAPI.request.PurchesOrderRequest;
import sita.sangita.orderAPI.response.APIResponse;
import sita.sangita.orderAPI.response.PurchaseOrderResponse;
import sita.sangita.orderAPI.service.OrderService;


@RestController
public class OrderRestController {

	@Autowired
	private OrderService orderServiceIntrface;
	
	@PostMapping("/orderCreate")
	public ResponseEntity<APIResponse<PurchaseOrderResponse>> createOrder( @RequestBody  PurchesOrderRequest request) throws Exception{
		
		APIResponse<PurchaseOrderResponse> response=new APIResponse<>();
		
		PurchaseOrderResponse orderResponse=orderServiceIntrface.createOrder(request);
		
		if(orderResponse!=null) {
			response.setStatus(200);
			response.setMessage("Order Created");
			response.setData(orderResponse);
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		else {
			response.setStatus(500);
			response.setMessage("Order Creation Failed");
			response.setData(null);
			return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
				
		
	}
	
	@PutMapping("/orderUpdate")
	public ResponseEntity<APIResponse<PurchaseOrderResponse>> updateOrder( @RequestBody  PaymentCallBackTO request) throws Exception{
		
		APIResponse<PurchaseOrderResponse> response=new APIResponse<>();
		
		PurchaseOrderResponse orderResponse=orderServiceIntrface.updateOrder(request);
		
		if(orderResponse!=null) {
			response.setStatus(200);
			response.setMessage("Order Updated");
			response.setData(orderResponse);
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		else {
			response.setStatus(500);
			response.setMessage("Order Updattion Failed");
			response.setData(null);
			return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
				
	}
	@GetMapping("/orders/{email}")
	public ResponseEntity<APIResponse<List<OrderDTO>>> getMyOrder(@PathVariable String email){
		
		APIResponse<List<OrderDTO>> response=new APIResponse<>();
		
		List<OrderDTO> orderEmail=orderServiceIntrface.getOrdersByEmail(email);
		if(!orderEmail.isEmpty()) {
			response.setStatus(200);
			response.setMessage("Order Fetched");
			response.setData(orderEmail);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		else {
			response.setStatus(500);
			response.setMessage("Order Fetched");
			response.setData(orderEmail);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	
	
}
