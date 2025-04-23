package sita.sangita.notificationAPI.entity;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Long productId;

	private String name;

	private String description;

	private BigDecimal unitPrice;

	private Integer unitsStack;

	
	private String imageUrl;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	private String title;

	private Boolean active;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdDate;

	@UpdateTimestamp
	@Column(insertable = false)
	private LocalDateTime updatedDate;
	

	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private ProductCategory category;

}
