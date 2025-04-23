package sita.sangita.orderAPI.request;

import java.util.List;

import lombok.Data;
import sita.sangita.orderAPI.dto.AddressDTO;
import sita.sangita.orderAPI.dto.CustomerDTO;
import sita.sangita.orderAPI.dto.OrderDTO;
import sita.sangita.orderAPI.dto.OrderItemDTO;

import sita.sangita.orderAPI.dto.CustomerDTO;
import sita.sangita.orderAPI.dto.AddressDTO;
import sita.sangita.orderAPI.dto.OrderDTO;
import sita.sangita.orderAPI.dto.OrderItemDTO;

import java.util.List;
@Data
public class PurchesOrderRequest {
    private CustomerDTO customerDto;
    private AddressDTO addressDTO;
    private OrderDTO orderDTO;
    private List<OrderItemDTO> orderItemDTOs;
}