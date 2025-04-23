package sita.sangita.notificationAPI.dto;

import lombok.Data;

@Data
public class AddressDTO {
	
	private Long id;
	private String houseNum;
	private String street;
	private String city;
	private String state;
	private String zipCode;

}
