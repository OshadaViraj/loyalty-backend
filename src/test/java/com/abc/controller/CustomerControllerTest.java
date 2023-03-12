package com.abc.controller;


import com.abc.dto.CustomerDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    private final CustomerController customerController;
    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    private final String URL = "/api/customer";

    @Autowired
    public CustomerControllerTest(CustomerController customerController, MockMvc mockMvc,
                                  ObjectMapper objectMapper) {
        this.customerController = customerController;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void createCustomer() throws Exception {


        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setMobileNumber("0715117519");
        customerDTO.setFirstName("Udani");
        customerDTO.setLastName("Buddimalie");

        ObjectWriter ow = this.objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(customerDTO);
        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void createUserWIthDuplicateMobileNumber() throws Exception {

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setMobileNumber("0715117518");
        customerDTO.setFirstName("Udani");
        customerDTO.setLastName("Buddimalie");

        ObjectWriter ow = this.objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(customerDTO);
        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());


        CustomerDTO secondCustomerDTO = new CustomerDTO();
        secondCustomerDTO.setMobileNumber("0715117518");
        secondCustomerDTO.setFirstName("Udani");
        secondCustomerDTO.setLastName("Buddimalie");

        requestJson = ow.writeValueAsString(secondCustomerDTO);
        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

    }

    @Test
    public void findCustomer() throws Exception {

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setMobileNumber("0715117517");
        customerDTO.setFirstName("Oshada");
        customerDTO.setMiddleName("Viraj");
        customerDTO.setLastName("Rodrigo");

        ObjectWriter ow = this.objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(customerDTO);
        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        MvcResult rt = mockMvc.perform(MockMvcRequestBuilders
                        .get(URL + "/{mobileNumber}", customerDTO.getMobileNumber()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String json = rt.getResponse().getContentAsString();
        CustomerDTO response = new ObjectMapper().readValue(json, CustomerDTO.class);
        assertEquals(customerDTO.getFirstName(), response.getFirstName());
        assertEquals(customerDTO.getFirstName(), response.getFirstName());

    }




}
