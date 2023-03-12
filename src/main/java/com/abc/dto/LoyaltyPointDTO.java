package com.abc.dto;

import com.abc.model.Customer;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class LoyaltyPointDTO {

    private Long id;

    @NotNull(message = "customer mobile number cannot null")
    private String mobileNumber;

    @NotNull(message = "loyalty points cannot null")
    private Double loyaltyPoint;

    private Long productId;

    private LocalDateTime createdDate;
}
