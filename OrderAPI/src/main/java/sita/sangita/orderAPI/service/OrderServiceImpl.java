package sita.sangita.orderAPI.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sita.sangita.orderAPI.dto.AddressDTO;
import sita.sangita.orderAPI.dto.CustomerDTO;
import sita.sangita.orderAPI.dto.OrderDTO;
import sita.sangita.orderAPI.dto.OrderItemDTO;
import sita.sangita.orderAPI.dto.PaymentCallBackTO;
import sita.sangita.orderAPI.entity.Address;
import sita.sangita.orderAPI.entity.Customer;
import sita.sangita.orderAPI.entity.Order;
import sita.sangita.orderAPI.entity.OrderItem;
import sita.sangita.orderAPI.repo.AddressRepo;
import sita.sangita.orderAPI.repo.CustomerRepo;
import sita.sangita.orderAPI.repo.OrderItemRepo;
import sita.sangita.orderAPI.repo.OrderRepo;
import sita.sangita.orderAPI.request.PurchesOrderRequest;
import sita.sangita.orderAPI.response.PurchaseOrderResponse;
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderItemRepo orderItemRepo;
	@Autowired
	private OrderRepo orderRepo;
	@Autowired
	private AddressRepo addressRepo;
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private RazorPayService razorPayServiceInterfacce;
	
	@Override
	public PurchaseOrderResponse createOrder(PurchesOrderRequest orderRequest) throws Exception {

	    if (orderRequest == null) {
	        throw new IllegalArgumentException("Order request is null");
	    }

	    CustomerDTO customerDTO = orderRequest.getCustomerDto();
	    
	    if (customerDTO == null) {
	        throw new IllegalArgumentException("CustomerDTO is null in order request");
	    }
	    
	    if (customerDTO.getEmail() == null || customerDTO.getEmail().isEmpty()) {
	        throw new IllegalArgumentException("Customer email is missing in request");
	    }
	    Customer c = customerRepo.findByEmail(customerDTO.getEmail());

	    if (c == null) {
	        c = new Customer();
	        c.setName(customerDTO.getName());
	        c.setEmail(customerDTO.getEmail());
	        c.setPhno(customerDTO.getPhno());
	        customerRepo.save(c);
	    }

	    AddressDTO addressDTO = orderRequest.getAddressDTO();
	    OrderDTO orderDTO = orderRequest.getOrderDTO();
	    List<OrderItemDTO> orderItemDTOLists = orderRequest.getOrderItemDTOs();

	    
		//save address details
		Address address=new Address();
		address.setHouseNum(addressDTO.getHouseNum());
		address.setStreet(addressDTO.getStreet());
		address.setCity(addressDTO.getCity());
		address.setState(addressDTO.getState());
		address.setCustomer(c);
		address.setZipCode(addressDTO.getZipCode());
		addressRepo.save(address);
		
		
		
		//save Order
		Order newOrder=new Order();
		String trackingNum=generateOrdertackingNumber();
		newOrder.setOrderTrackingNum(trackingNum);
		com.razorpay.Order paymentOrder=razorPayServiceInterfacce.createPaymentOrder(orderDTO.getTotalPrice());
		newOrder.setRazorPayOrderId(paymentOrder.get("id"));
		newOrder.setOrderStatus(paymentOrder.get("status"));
		newOrder.setTotalPrice(orderDTO.getTotalPrice());
		newOrder.setTotalQuantity(orderDTO.getTotalQuantity());
		newOrder.setEmail(orderDTO.getEmail());
		newOrder.setCustomer(c);
		newOrder.setAddress(address);
		
		orderRepo.save(newOrder);
		
		
		
		//save orderItems
		for(OrderItemDTO itemDTO:orderItemDTOLists) {
			OrderItem item=new OrderItem();
			BeanUtils.copyProperties(itemDTO, item);
			
			item.setOrder(newOrder);
			orderItemRepo.save(item);
			
		}
		
		//prepare and return Response
		/*PurchaseOrderResponse response=new PurchaseOrderResponse();
		response.setRazorPayOrderId(orderTrackNum);
		response.setOrderStatus(orderTrackNum);
		response.setOrderTrackingNum(orderTrackNum);
		*/		
		
		PurchaseOrderResponse response=
				PurchaseOrderResponse.builder()
									 .razorPayOrderId(paymentOrder.get("id"))
									 .orderStatus(paymentOrder.get("status"))
									 .orderTrackingNum(trackingNum)
									 .build();
					
		return response;
	}

	@Override
	public List<OrderDTO> getOrdersByEmail(String email) {

		List<OrderDTO> dtoList=new ArrayList<>();
		List<Order> orderList = orderRepo.findByEmail(email);
		
		for(Order order:orderList) {
			OrderDTO dto=new OrderDTO();
			BeanUtils.copyProperties(order, dto);
			dtoList.add(dto);
		}
		
		return dtoList;
	}

	@Override
	public PurchaseOrderResponse updateOrder(PaymentCallBackTO paymentCallBackTO) {

		Order order=orderRepo.findByRazorPayOrderId(paymentCallBackTO.getRazorPayOrderId());
		
		
		if(order!=null) {
			order.setOrderStatus("Confirmed");
			order.setDeliveryDate(LocalDate.now().plusDays(3));
			order.setRazorPayPaymentId(paymentCallBackTO.getRazorPayPaymentId());
			orderRepo.save(order);
			
			String subject="Your Order Is Confirmed";
			
			String body="Thank You you will recive Your oder on given date"+order.getDeliveryDate();
			
			emailService.sendEmail(order.getEmail(), subject, body);
			
		}
		
		//Prepare and return  response
				
		
		return PurchaseOrderResponse.builder()
									.razorPayOrderId(paymentCallBackTO.getRazorPayOrderId())
									.orderStatus(order.getOrderStatus())
									.orderTrackingNum(order.getOrderTrackingNum())
									.build();
	}
	
	private String generateOrdertackingNumber() {
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		String timeStamp=sdf.format(new Date());
		String randomUuid=UUID.randomUUID().toString().substring(0,5).toUpperCase();
		
		//combine timestamp and uuid from order tracking
		return "OD"+timeStamp+randomUuid;
		
		
	}

}
