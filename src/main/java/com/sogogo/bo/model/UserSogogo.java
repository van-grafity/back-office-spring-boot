package com.sogogo.bo.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "user_sogogo")
public class UserSogogo {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "uuid", length = 36, nullable = false)
    private UUID id;

    private String name;
    private String username;
    private String password;
    private int role;
}
