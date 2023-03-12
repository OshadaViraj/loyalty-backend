package com.abc.controller;


import com.abc.dto.CustomerDTO;
import com.abc.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("{mobileNumber}")
    public ResponseEntity findByMobileNumber(@PathVariable String mobileNumber){
        return new ResponseEntity<>(this.customerService.findByMobileNumber(mobileNumber) , HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity findAll() {
        List<CustomerDTO> customerDTOList = this.customerService.findAll();
        return new ResponseEntity<>(customerDTOList , HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createCustomer(@RequestBody @Valid CustomerDTO customerDTO) {
        return new ResponseEntity<>(this.customerService.createCustomer(customerDTO) ,HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity updateCustomer(@RequestBody @Valid CustomerDTO customerDTO) {
        return new ResponseEntity<>(this.customerService.updateCustomer(customerDTO) , HttpStatus.OK);
    }


}
