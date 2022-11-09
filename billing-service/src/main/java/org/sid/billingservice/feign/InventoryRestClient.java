package org.sid.billingservice.feign;

import org.sid.billingservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PRODUCT-SERVICE")
public interface InventoryRestClient {

    @GetMapping("/products/{id}?projection=fullProduct")
    Product findProductById(@PathVariable("id") Long id);

    @GetMapping("/products?projection=fullProduct")
    PagedModel<Product> findAll();

    @GetMapping(path = "/products")
    PagedModel<Product> pageProducts(@RequestParam(value = "page") int page,
                                     @RequestParam(value = "size") int size);
    @GetMapping(path = "/products")
    PagedModel<Product> pageProducts();

    @GetMapping("/products/{id}")
    Product getProductById(@PathVariable("id") Long id);
}