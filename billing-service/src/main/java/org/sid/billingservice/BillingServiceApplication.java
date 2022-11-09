package org.sid.billingservice;

import org.sid.billingservice.feign.CustomerRestClient;
import org.sid.billingservice.feign.InventoryRestClient;
import org.sid.billingservice.entities.Bill;
import org.sid.billingservice.model.Customer;
import org.sid.billingservice.entities.ProductItem;
import org.sid.billingservice.model.Product;
import org.sid.billingservice.repository.BillRepository;
import org.sid.billingservice.repository.ProductItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;

import java.util.Date;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillingServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(BillRepository billRepository,
							ProductItemRepository productItemRepository,
							InventoryRestClient inventoryRestClient,
							CustomerRestClient customerRestClient){
		/*return args -> {
			Bill bill = new Bill();
			bill.setBillingDate(new Date());
			Customer customer = customerServiceClient.findCustomerById(1L);
			bill.setCustomerId(customer.getId());
			billRepository.save(bill);
			inventoryServiceClient.findAll().getContent().forEach(product -> {
				productItemRepository.save(new ProductItem(null, null,product.getId(), product.getPrice(), (int)(1+Math.random()*1000), bill));
			});
		};*/

		return args -> {
			Customer customer = customerRestClient.findCustomerById(1L);
			System.out.println(customer);
			Bill bill1 = billRepository.save(new Bill(null, new Date(), null, null, customer.getId()));
			PagedModel<Product> productPagedModel = inventoryRestClient.pageProducts();
			productPagedModel.forEach(product -> {
				ProductItem productItem = new ProductItem();
				productItem.setPrice(product.getPrice());
				productItem.setQuantity(1 + new Random().nextInt(100));
				productItem.setBill(bill1);
				productItem.setProductId(product.getId());
				productItemRepository.save(productItem);
			});
		};
	}

}
