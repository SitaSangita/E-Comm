package sita.sangita.customerAPI.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class CustomerDTO {
	
	private Long id;
	private String name;
	private String email;
	private String pwd;
	private String updatePwd;
	private Long phno;
	
	private Date createdDate;
	
	private Date updateDate;



}
