package com.microservice.payementservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentMode {

	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	 private long id;

	 @Column(name = "PAY_NAME")
	 private String name;
}
