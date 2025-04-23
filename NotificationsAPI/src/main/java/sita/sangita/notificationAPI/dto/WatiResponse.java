package sita.sangita.notificationAPI.dto;


import java.util.List;

import lombok.Data;

@Data
public class WatiResponse {
	private String result;
	private Long phone_number;
	
	private List<WatiParameters> parameters;
	private boolean validWhatsappNumber;
	private String name;
	private String orderNumber;
}
