package sita.sangita.productAPI.restcontroller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import sita.sangita.productAPI.dto.ProductCategoryDTO;
import sita.sangita.productAPI.dto.ProductDTO;
import sita.sangita.productAPI.response.APIResponse;
import sita.sangita.productAPI.service.ProductService;
@RestController
@CrossOrigin
public class ProductRestController {

	@Autowired
	private ProductService serviceInterf;

	@GetMapping("/categories")
	public ResponseEntity<APIResponse<List<ProductCategoryDTO>>> prodcutCategories() {

		APIResponse<List<ProductCategoryDTO>> response = new APIResponse<>();
		List<ProductCategoryDTO> allCatagories = serviceInterf.findAllCategories();
		if (!allCatagories.isEmpty()) {
			response.setStatus(200);
			response.setMessage("Fetch Catagories Successfully");
			response.setData(allCatagories);

			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		else  {
			response.setStatus(500);
			response.setMessage("Faild to Fetch Catagories");
			response.setData(null);

			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/products/{categoryId}")
	public ResponseEntity<APIResponse<List<ProductDTO>>> products(@PathVariable Long categoryId){
		APIResponse<List<ProductDTO>> response=new APIResponse<>();
		List<ProductDTO> productByCatagoryId = serviceInterf.findProductByCategoryId(categoryId);
	
		if(!productByCatagoryId.isEmpty()) {
			response.setStatus(200);
			response.setMessage("Fetch all Product by CatagoryId");
			response.setData(productByCatagoryId);
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		else {
			response.setStatus(500);
			response.setMessage("Faild Fetch all Product by CatagoryId");
			response.setData(null);
			return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	
	}
	
	@GetMapping("/productsName/{name}")
	public ResponseEntity<APIResponse<List<ProductDTO>>> products(@PathVariable String name) {
	    APIResponse<List<ProductDTO>> response = new APIResponse<>();
	    List<ProductDTO> productByCategoryId = serviceInterf.findByProductName(name);

	    if (productByCategoryId.isEmpty()) { // No need to check for null now
	        response.setStatus(404);
	        response.setMessage("No products found for the given product name");
	        response.setData(Collections.emptyList());
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    } 

	    response.setStatus(200);
	    response.setMessage("Fetched all products by product name");
	    response.setData(productByCategoryId);
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}

	
	
	@GetMapping("/productsId/{id}")
	public ResponseEntity<APIResponse<ProductDTO>> productsId(@PathVariable Long id){
		APIResponse<ProductDTO> response=new APIResponse<>();
		ProductDTO productById = serviceInterf.findByProductId(id);
	
		if(productById!=null) {
			response.setStatus(200);
			response.setMessage("Fetch all Product by CatagoryId");
			response.setData(productById);
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		else {
			response.setStatus(500);
			response.setMessage("Faild Fetch all Product by CategoryId");
			response.setData(null);
			return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	
	}
	
		
	
}
