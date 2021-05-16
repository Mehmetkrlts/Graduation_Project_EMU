package com.graduation.project.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "Coordinator")
@Table(name = "coordinator")
@Getter
@Setter
public class Coordinator {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String role;

}