package com.sogogo.bo.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "user_sogogo")
public class UserSogogo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String username;
    private String password;
    @NotNull(message = "Role is required")
    private Integer role = 0;
    @Column(columnDefinition="Decimal(10,0) default '0'")
    private BigDecimal balance = BigDecimal.ZERO;
    @Column(name = "enabled", columnDefinition = "INT(1)")
    private int enabled;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAdd;
    private String ip;
    private Long parent;
    private Long referral;
}
