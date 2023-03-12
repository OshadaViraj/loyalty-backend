package com.abc.controller;

import com.abc.dto.CustomerDTO;
import com.abc.dto.LoyaltyPointDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class LoyaltyPointControllerTest {

    private final CustomerController customerController;

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    private final String LOYALTY_URL = "/api/loyalty-point";

    private final String CUSTOMER_URL = "/api/customer";

    @Autowired
    public LoyaltyPointControllerTest(CustomerController customerController,
                                      MockMvc mockMvc, ObjectMapper objectMapper) {
        this.customerController = customerController;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void addLoyaltyPoint() throws Exception {

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setMobileNumber("0715117419");
        customerDTO.setFirstName("Udani");
        customerDTO.setLastName("Buddimalie");

        ObjectWriter ow = this.objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(customerDTO);
        mockMvc.perform(MockMvcRequestBuilders.post(CUSTOMER_URL)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        LoyaltyPointDTO loyaltyPointDTO = new LoyaltyPointDTO();
        loyaltyPointDTO.setLoyaltyPoint(100D);
        loyaltyPointDTO.setMobileNumber(customerDTO.getMobileNumber());


        requestJson = ow.writeValueAsString(loyaltyPointDTO);
        mockMvc.perform(MockMvcRequestBuilders.post(LOYALTY_URL + "/add")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void redeemLoyaltyPoint() throws Exception {

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setMobileNumber("0715117319");
        customerDTO.setFirstName("Udani");
        customerDTO.setLastName("Buddimalie");

        ObjectWriter ow = this.objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(customerDTO);
        mockMvc.perform(MockMvcRequestBuilders.post(CUSTOMER_URL)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        LoyaltyPointDTO loyaltyPointDTO = new LoyaltyPointDTO();
        loyaltyPointDTO.setLoyaltyPoint(100D);
        loyaltyPointDTO.setMobileNumber(customerDTO.getMobileNumber());

        requestJson = ow.writeValueAsString(loyaltyPointDTO);
        mockMvc.perform(MockMvcRequestBuilders.post(LOYALTY_URL + "/add")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        loyaltyPointDTO.setLoyaltyPoint(50D);
        requestJson = ow.writeValueAsString(loyaltyPointDTO);
        mockMvc.perform(MockMvcRequestBuilders.post(LOYALTY_URL + "/redeem")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.post(LOYALTY_URL + "/redeem")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    public void InsufficientLoyalPointBalance() throws Exception {

    }


}
