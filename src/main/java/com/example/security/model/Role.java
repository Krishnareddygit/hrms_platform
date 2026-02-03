package com.example.security.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;
    // ROLE_EMPLOYEE, ROLE_HR_OPERATIONS, ROLE_TALENT_ACQUISITION, ROLE_HR_BP, ROLE_HR_PAYROLL

    private String description;



}

