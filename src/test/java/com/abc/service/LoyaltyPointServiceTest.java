package com.abc.service;

import com.abc.dto.CustomerDTO;
import com.abc.dto.LoyaltyPointDTO;
import com.abc.exceptions.CustomerNotAvailableException;
import com.abc.exceptions.InsufficientBalanceException;
import com.abc.model.Customer;
import com.abc.model.LoyaltyPoint;
import com.abc.repository.CustomerRepository;
import com.abc.repository.LoyaltyPointRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class LoyaltyPointServiceTest {


    private final CustomerService customerService;

    private final LoyaltyPointService loyaltyPointService;

    private final ModelMapper modelMapper;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private LoyaltyPointRepository loyaltyPointRepository;

    @Autowired
    public LoyaltyPointServiceTest(CustomerService customerService,
                                   LoyaltyPointService loyaltyPointService, ModelMapper modelMapper) {
        this.customerService = customerService;
        this.loyaltyPointService = loyaltyPointService;
        this.modelMapper = modelMapper;
    }

    @Test
    public void addLoyaltyPoints() {
        LoyaltyPointDTO loyaltyPointDTO = new LoyaltyPointDTO();
        loyaltyPointDTO.setLoyaltyPoint(100D);
        loyaltyPointDTO.setMobileNumber("0775117517");
        loyaltyPointDTO.setProductId(100L);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setMobileNumber("0775117517");
        customerDTO.setFirstName("Udani");
        customerDTO.setMiddleName("De");
        customerDTO.setLastName("Buddimalie");

        Customer customer = this.modelMapper.map(customerDTO, Customer.class);
        customer.setTotalPoints(0D);
        when(customerRepository.findByMobileNumber(customerDTO.getMobileNumber())).thenReturn(Optional.of(customer));

        LoyaltyPoint loyaltyPoint = this.modelMapper.map(loyaltyPointDTO, LoyaltyPoint.class);
        when(loyaltyPointRepository.save(loyaltyPoint)).thenReturn(loyaltyPoint);

        when(customerRepository.save(customer)).thenReturn(customer);

        assertEquals(100D, loyaltyPointService.addLoyaltyPoints(loyaltyPointDTO).getTotalPoints());

    }

    @Test
    public void redeemLoyaltyPoints() {
        LoyaltyPointDTO loyaltyPointDTO = new LoyaltyPointDTO();
        loyaltyPointDTO.setLoyaltyPoint(10D);
        loyaltyPointDTO.setMobileNumber("0775117518");
        loyaltyPointDTO.setProductId(100L);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setMobileNumber("0775117518");
        customerDTO.setFirstName("Udani");
        customerDTO.setMiddleName("De");
        customerDTO.setLastName("Buddimalie");

        Customer customer = this.modelMapper.map(customerDTO, Customer.class);
        customer.setTotalPoints(100D);
        when(customerRepository.findByMobileNumber(customerDTO.getMobileNumber())).thenReturn(Optional.of(customer));

        LoyaltyPoint loyaltyPoint = this.modelMapper.map(loyaltyPointDTO, LoyaltyPoint.class);
        when(loyaltyPointRepository.save(loyaltyPoint)).thenReturn(loyaltyPoint);

        when(customerRepository.save(customer)).thenReturn(customer);

        assertEquals(90D, loyaltyPointService.redeemLoyaltyPoints(loyaltyPointDTO).getTotalPoints());

    }

    @Test
    public void redeemInsufficientLoyaltyPoints() {
        final String ERROR_MEG = "Insufficient Balance";

        LoyaltyPointDTO loyaltyPointDTO = new LoyaltyPointDTO();
        loyaltyPointDTO.setLoyaltyPoint(12D);
        loyaltyPointDTO.setMobileNumber("0775117518");
        loyaltyPointDTO.setProductId(100L);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setMobileNumber("0775117518");
        customerDTO.setFirstName("Udani");
        customerDTO.setMiddleName("De");
        customerDTO.setLastName("Buddimalie");

        Customer customer = this.modelMapper.map(customerDTO, Customer.class);
        customer.setTotalPoints(10D);
        when(customerRepository.findByMobileNumber(customerDTO.getMobileNumber())).thenReturn(Optional.of(customer));

        LoyaltyPoint loyaltyPoint = this.modelMapper.map(loyaltyPointDTO, LoyaltyPoint.class);
        when(loyaltyPointRepository.save(loyaltyPoint)).thenReturn(loyaltyPoint);

        when(customerRepository.save(customer)).thenReturn(customer);

        Exception exception = assertThrows(InsufficientBalanceException.class, () -> {
            loyaltyPointService.redeemLoyaltyPoints(loyaltyPointDTO);
        });
        ;
        assertEquals(ERROR_MEG, exception.getMessage());
    }

    @Test
    public void userNotFound(){
        final String ERROR_MEG = "Customer not found";

        LoyaltyPointDTO loyaltyPointDTO = new LoyaltyPointDTO();
        loyaltyPointDTO.setLoyaltyPoint(12D);
        loyaltyPointDTO.setMobileNumber("0775117517");
        loyaltyPointDTO.setProductId(100L);
        when(customerRepository.findByMobileNumber("0775117517")).thenReturn(Optional.empty());
        Exception exception = assertThrows(CustomerNotAvailableException.class, () -> {
            loyaltyPointService.redeemLoyaltyPoints(loyaltyPointDTO);
        });
        assertEquals(ERROR_MEG, exception.getMessage());



    }
}
