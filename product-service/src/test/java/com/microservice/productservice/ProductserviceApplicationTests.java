package com.microservice.productservice;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.productservice.payload.request.ProductRequest;
import com.microservice.productservice.repository.ProductRepository;

@SpringBootTest
@Testcontainers
class ProductserviceApplicationTests {

	@Container
	static MySQLContainer mySQLContainer = new MySQLContainer("mysql:latest").withDatabaseName("productdb").withPassword("").withUsername("root");
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private ProductRepository productRepository;
	
	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
		dynamicPropertyRegistry.add("spring.datasource.username", mySQLContainer::getUsername);	
		dynamicPropertyRegistry.add("spring.datasource.password", mySQLContainer::getPassword);	
	}
	
	@BeforeAll
	static void beforeAll() {
		mySQLContainer.start();
	}
	
	@AfterAll
	static void closeAll() {
		mySQLContainer.stop();
	}
	
	@Test
	void shouldCreateProduct() throws Exception {
		ProductRequest productRequest = getProductRequest();
		String productRequestString = objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/product")
						.contentType(MediaType.APPLICATION_JSON)
						.content(productRequestString))
						.andExpect(status().isCreated());
		
		Assertions.assertTrue(productRepository.findAll().size() == 1);
	}
	
	@Test
	void contextLoads() {
	}
	
	private ProductRequest getProductRequest() {
		return ProductRequest.builder()
						.name("Iphone 14")
						.price(150000)
						.quantity(10)
						.build();
	}

}
