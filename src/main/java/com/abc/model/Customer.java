package com.abc.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Customer extends BaseEntity {

    private String mobileNumber;

    private String firstName;

    private String middleName;

    private String lastName;

    private Double totalPoints;

}
