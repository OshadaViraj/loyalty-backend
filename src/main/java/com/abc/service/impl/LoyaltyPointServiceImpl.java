package com.abc.service.impl;

import com.abc.dto.CustomerDTO;
import com.abc.dto.LoyaltyPointDTO;
import com.abc.exceptions.CustomerNotAvailableException;
import com.abc.exceptions.InsufficientBalanceException;
import com.abc.model.Customer;
import com.abc.model.LoyaltyPoint;
import com.abc.repository.CustomerRepository;
import com.abc.repository.LoyaltyPointRepository;
import com.abc.service.LoyaltyPointService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LoyaltyPointServiceImpl implements LoyaltyPointService {

    private final LoyaltyPointRepository loyaltyPointRepository;
    private final CustomerRepository customerRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public LoyaltyPointServiceImpl(LoyaltyPointRepository loyaltyPointRepository,
                                   CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.loyaltyPointRepository = loyaltyPointRepository;
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomerDTO addLoyaltyPoints(LoyaltyPointDTO loyaltyPointDTO) {
        Optional<Customer> optionalCustomer = this.customerRepository.findByMobileNumber(loyaltyPointDTO.getMobileNumber());
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            LoyaltyPoint loyaltyPoint = new LoyaltyPoint();
            loyaltyPoint.setCustomer(customer);
            loyaltyPoint.setProductId(loyaltyPointDTO.getProductId());
            loyaltyPoint.setLoyaltyPoint(loyaltyPointDTO.getLoyaltyPoint());
            this.loyaltyPointRepository.save(loyaltyPoint);
            customer.setTotalPoints(customer.getTotalPoints() + loyaltyPointDTO.getLoyaltyPoint());
            return this.modelMapper.map(this.customerRepository.save(customer), CustomerDTO.class);
        } else {
            throw new CustomerNotAvailableException();
        }


    }

    @Override
    public CustomerDTO redeemLoyaltyPoints(LoyaltyPointDTO loyaltyPointDTO) {
        Optional<Customer> optionalCustomer = this.customerRepository.findByMobileNumber(loyaltyPointDTO.getMobileNumber());
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            LoyaltyPoint loyaltyPoint = new LoyaltyPoint();
            loyaltyPoint.setCustomer(customer);
            loyaltyPoint.setProductId(loyaltyPointDTO.getProductId());
            if (customer.getTotalPoints() >= loyaltyPointDTO.getLoyaltyPoint()) {
                loyaltyPoint.setLoyaltyPoint(loyaltyPointDTO.getLoyaltyPoint() * -1);
                this.loyaltyPointRepository.save(loyaltyPoint);
                customer.setTotalPoints(customer.getTotalPoints() - loyaltyPointDTO.getLoyaltyPoint());
                return this.modelMapper.map(this.customerRepository.save(customer), CustomerDTO.class);
            } else {
                throw new InsufficientBalanceException("Insufficient Balance");
            }

        } else {
            throw new CustomerNotAvailableException("Customer not found");
        }
    }

    @Override
    public List<LoyaltyPointDTO> viewHistory(String mobileNumber) {
        List<LoyaltyPointDTO> loyaltyPointDTOList = new ArrayList<>();
        List<LoyaltyPoint> loyaltyPointList = this.loyaltyPointRepository.findByCustomerMobileNumber(mobileNumber);
        loyaltyPointList.forEach(value -> {
            LoyaltyPointDTO loyaltyPointDTO = new LoyaltyPointDTO();
            loyaltyPointDTO.setMobileNumber(value.getCustomer().getMobileNumber());
            loyaltyPointDTO.setProductId(value.getProductId());
            loyaltyPointDTO.setLoyaltyPoint(value.getLoyaltyPoint());
            loyaltyPointDTO.setCreatedDate(value.getCreatedDate());
            loyaltyPointDTO.setId(value.getId());
            loyaltyPointDTOList.add(loyaltyPointDTO);
        });
        return loyaltyPointDTOList;
    }
}
