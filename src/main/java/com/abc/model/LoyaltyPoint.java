package com.abc.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class LoyaltyPoint extends BaseEntity{

    @ManyToOne
    private Customer customer;

    private Double loyaltyPoint;

    private Long productId;
}
