package com.abc.service.impl;

import com.abc.dto.CustomerDTO;
import com.abc.exceptions.CustomerAlreadyExistException;
import com.abc.exceptions.CustomerNotAvailableException;
import com.abc.model.Customer;
import com.abc.repository.CustomerRepository;
import com.abc.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Optional<Customer> optionalCustomer = this.customerRepository.findByMobileNumber(customerDTO.getMobileNumber());
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistException();
        }
        Customer customer = this.modelMapper.map(customerDTO, Customer.class);
        customer.setTotalPoints(0D);
        return this.modelMapper.map(this.customerRepository.save(customer) , CustomerDTO.class);
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        Optional<Customer> optionalCustomer = this.customerRepository.findByMobileNumber(customerDTO.getMobileNumber());
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setFirstName(customerDTO.getFirstName());
            customer.setLastName(customerDTO.getLastName());
            customer.setMiddleName(customerDTO.getMiddleName());
            return this.modelMapper.map(this.customerRepository.save(customer) , CustomerDTO.class);
        } else throw new CustomerNotAvailableException();
    }

    @Override
    public List<CustomerDTO> findAll() {
        return this.customerRepository.findAll().stream().map(customer -> this.modelMapper.map(customer, CustomerDTO.class)).collect(Collectors.toList());

    }

    @Override
    public CustomerDTO findByMobileNumber(String mobileNumber) {
        Optional<Customer> optionalCustomer = this.customerRepository.findByMobileNumber(mobileNumber);
        if (optionalCustomer.isPresent()) {
            return this.modelMapper.map(optionalCustomer.get(), CustomerDTO.class);
        }
        throw new CustomerNotAvailableException();
    }


}
