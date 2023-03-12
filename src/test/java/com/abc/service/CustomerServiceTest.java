package com.abc.service;


import com.abc.dto.CustomerDTO;
import com.abc.model.Customer;
import com.abc.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CustomerServiceTest {

    private final CustomerService customerService;

    private final ModelMapper modelMapper;

    @MockBean
    private  CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceTest(CustomerService customerService, ModelMapper modelMapper) {
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }


    @Test
    public void createCustomer(){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setMobileNumber("0715117219");
        customerDTO.setFirstName("Udani");
        customerDTO.setLastName("Buddimalie");
        Customer customer = this.modelMapper.map(customerDTO ,Customer.class);
        when(customerRepository.save(customer)).thenReturn(customer);
        assertEquals(customerDTO , customerService.createCustomer(customerDTO));
    }

    @Test
    public void updateCustomer(){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setMobileNumber("0715117119");
        customerDTO.setFirstName("Udani");
        customerDTO.setMiddleName("De");
        customerDTO.setLastName("Buddimalie");

        Customer customer = this.modelMapper.map(customerDTO ,Customer.class);
        when(customerRepository.findByMobileNumber(customerDTO.getMobileNumber())).thenReturn(Optional.of(customer));

        customerDTO.setFirstName("Udani_U");
        customerDTO.setMiddleName("De_U");
        customerDTO.setLastName("Buddimalie_U");

        customer = this.modelMapper.map(customerDTO ,Customer.class);

        when(customerRepository.save(customer)).thenReturn(customer);
        assertEquals(customerDTO.getFirstName() , customerService.updateCustomer(customerDTO).getFirstName());
        assertEquals(customerDTO.getLastName() , customerService.updateCustomer(customerDTO).getLastName());
    }

    @Test
    public void findByMobileNumber(){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setMobileNumber("0715117019");
        customerDTO.setFirstName("Udani");
        customerDTO.setMiddleName("De");
        customerDTO.setLastName("Buddimalie");

        Customer customer = this.modelMapper.map(customerDTO ,Customer.class);
        when(customerRepository.findByMobileNumber(customerDTO.getMobileNumber())).thenReturn(Optional.of(customer));
        assertEquals(customerDTO.getMobileNumber() , customerService.findByMobileNumber(customerDTO.getMobileNumber()).getMobileNumber());

    }


}
