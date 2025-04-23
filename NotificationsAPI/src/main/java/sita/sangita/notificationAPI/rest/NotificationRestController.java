package sita.sangita.notificationAPI.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import sita.sangita.notificationAPI.service.NotificationServiceInterf;

@RestController
public class NotificationRestController {

	@Autowired
	private NotificationServiceInterf notificationServiceInterf;
	
	
	@GetMapping("/demo")
	public String demo() {
		notificationServiceInterf.sendDeliverNotification();
		return "success";
	}
}
