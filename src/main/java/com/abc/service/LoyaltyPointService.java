package com.abc.service;

import com.abc.dto.CustomerDTO;
import com.abc.dto.LoyaltyPointDTO;

import java.util.List;

public interface LoyaltyPointService {

    CustomerDTO addLoyaltyPoints(LoyaltyPointDTO loyaltyPointDTO);

    CustomerDTO redeemLoyaltyPoints(LoyaltyPointDTO loyaltyPointDTO);

    List<LoyaltyPointDTO> viewHistory( String mobileNumber);
}
