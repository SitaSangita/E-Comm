package sita.sangita.notificationAPI.service;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import sita.sangita.notificationAPI.dto.WatiParameters;
import sita.sangita.notificationAPI.dto.WatiRequest;
import sita.sangita.notificationAPI.dto.WatiResponse;
import sita.sangita.notificationAPI.entity.Customer;
import sita.sangita.notificationAPI.entity.Order;
import sita.sangita.notificationAPI.repo.OrderRepo;


@Service
public class NotificationServiceimpl  implements NotificationServiceInterf{

	@Autowired
	private OrderRepo orderRepo;
	
	@Autowired
	private EmailService emailService;
	
	@Value("${wati.token}")
	private String watiToken;
	@Value("${wati.template.name}")
	private String templateName;
	@Value("${wati.endpoint.url}")
	private String watiEndPontUrl;
	
	@Override
	public Integer sendDeliverNotification() {
		List<Order> orders = orderRepo.findByDeliveryDate(LocalDate.now());
		
		
		for(Order order:orders) {
			Customer customer=order.getCustomer();
			sendEmailNotification(customer.getEmail(),order.getOrderTrackingNum());
			WatiResponse watiResponse = sendWatiNotification(customer, order.getOrderTrackingNum());
			System.out.println(watiResponse);
		}
		return orders.size();
	}

	
	
	public boolean sendEmailNotification(String to, String orderTrackingNum) {
		
		String subject="Your Order Out Of Delivery";
		String body="Your Order"+orderTrackingNum+"Will deliver Today";
		return emailService.sendEmail(to, subject, body);
		
		
	}
	private WatiResponse sendWatiNotification( Customer customer, String ordertrackingNum) {
		
		RestTemplate rt=new RestTemplate();
		String apiUrl=watiEndPontUrl+"?WhatsAppNumber="+customer.getPhno();
		WatiParameters nameParam=new WatiParameters();
		nameParam.setName("name");
		nameParam.setValue(customer.getName());
		
		WatiParameters trackingParam=new WatiParameters();
		trackingParam.setName("order_tracking_number");
		trackingParam.setValue(ordertrackingNum);
		
		
		WatiRequest request=new WatiRequest();
		request.setTemplate_name(templateName);
		request.setBroadcast_name(templateName+"BD");
		request.setParameters(Arrays.asList(nameParam,trackingParam));
		
		
		HttpHeaders headers=new HttpHeaders();
		headers.add("Authorization",watiToken);
		
		HttpEntity<WatiRequest> reqEntity=new HttpEntity<WatiRequest>(request,headers);
		
		
		ResponseEntity<WatiResponse> postForEntity = rt.postForEntity(apiUrl, request,WatiResponse.class);
		
		return postForEntity.getBody();
		
	
	}
	
	@Override
	public Integer sendNotificationToPendingOrders() {

		
		
		return null;
	}
	

}
