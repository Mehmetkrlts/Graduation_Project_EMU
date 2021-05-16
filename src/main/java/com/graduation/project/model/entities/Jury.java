package com.graduation.project.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "Jury")
@Table(name = "jury")
@Getter
@Setter
public class Jury {
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
