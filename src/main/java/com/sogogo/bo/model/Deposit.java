package com.sogogo.bo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data
@Table(name = "deposit")
public class Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private UserSogogo user;
    private Long amount;
    private int status;
    @ManyToOne
    private PaymentMethod paymentMethod;
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAdd;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateApprove;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateReject;
    @ManyToOne
    private UserSogogo updateBy;
}
