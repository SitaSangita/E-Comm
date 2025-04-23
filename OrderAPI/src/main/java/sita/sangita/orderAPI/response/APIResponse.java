package sita.sangita.orderAPI.response;

import lombok.Data;

@Data
public class APIResponse<T> {
	
	private Integer status;
	private String message;
	private T data;

}
