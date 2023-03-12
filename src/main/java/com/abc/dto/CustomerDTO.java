package com.abc.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CustomerDTO {

    private Long id;

    @NotNull(message = "mobile number cannot null")
    private String mobileNumber;

    private String firstName;

    private String middleName;

    private String lastName;

    private Double totalPoints;

}
