package sita.sangita.orderAPI.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sita.sangita.orderAPI.entity.Address;



public interface AddressRepo extends JpaRepository<Address, Long>{

}
