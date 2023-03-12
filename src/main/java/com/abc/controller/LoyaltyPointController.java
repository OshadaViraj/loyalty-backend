package com.abc.controller;


import com.abc.dto.LoyaltyPointDTO;
import com.abc.service.LoyaltyPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loyalty-point")
public class LoyaltyPointController {

    private final LoyaltyPointService loyaltyPointService;

    @Autowired
    public LoyaltyPointController(LoyaltyPointService loyaltyPointService) {
        this.loyaltyPointService = loyaltyPointService;
    }

    @GetMapping("/{mobileNumber}")
    public ResponseEntity viewHistory(@PathVariable String mobileNumber) {
        return new ResponseEntity<>( this.loyaltyPointService.viewHistory(mobileNumber) , HttpStatus.CREATED );
    }

    @PostMapping("/add")
    public ResponseEntity addLoyaltyPoints(@RequestBody LoyaltyPointDTO loyaltyPointDTO) {
        return new ResponseEntity<>( this.loyaltyPointService.addLoyaltyPoints(loyaltyPointDTO) , HttpStatus.CREATED );

    }

    @PostMapping("/redeem")
    public ResponseEntity redeemLoyaltyPoints(@RequestBody LoyaltyPointDTO loyaltyPointDTO) {
        return new ResponseEntity<>( this.loyaltyPointService.redeemLoyaltyPoints(loyaltyPointDTO) , HttpStatus.CREATED );
    }

}
