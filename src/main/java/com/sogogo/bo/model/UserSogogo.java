package com.sogogo.bo.model;

import lombok.Data;

import javax.persistence.*;

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
    private int role;
}
