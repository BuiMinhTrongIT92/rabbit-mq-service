package com.trong.rabbitmqservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    private Object _id;
    private String customerNumber;
    private String checkNumber;
    private Date paymentDate;
    private Double amount;
}
