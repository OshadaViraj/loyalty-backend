package com.abc.service;

import com.abc.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {


     CustomerDTO createCustomer(CustomerDTO customerDTO);

     CustomerDTO updateCustomer(CustomerDTO customerDTO);

     List<CustomerDTO> findAll();

     CustomerDTO findByMobileNumber( String mobileNumber);

}
