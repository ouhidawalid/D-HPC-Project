package org.sid.billingservice.web;

import org.sid.billingservice.feign.CustomerRestClient;
import org.sid.billingservice.feign.InventoryRestClient;
import org.sid.billingservice.entities.Bill;
import org.sid.billingservice.repository.BillRepository;
import org.sid.billingservice.repository.ProductItemRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillingRestController {
    private final BillRepository billRepository;
    private final ProductItemRepository productItemRepository;
    private final CustomerRestClient customerRestClient;
    private final InventoryRestClient inventoryRestClient;

    public BillingRestController(BillRepository billRepository,
                                 ProductItemRepository productItemRepository,
                                 CustomerRestClient customerRestClient,
                                 InventoryRestClient inventoryRestClient) {
        this.billRepository = billRepository;
        this.productItemRepository = productItemRepository;
        this.customerRestClient = customerRestClient;
        this.inventoryRestClient = inventoryRestClient;
    }

    @GetMapping("/bills/full/{id}")
    public Bill getBill(@PathVariable(name = "id") Long id){
        Bill bill= billRepository.findById(id).get();
        bill.setCustomer(customerRestClient.findCustomerById(bill.getCustomerId()));
        bill.setProductItems(productItemRepository.findByBillId(id));
        bill.getProductItems().forEach(productItem -> {
            productItem.setProduct(inventoryRestClient.findProductById(productItem.getProductId()));
        });
        return bill;
    }
}
