package com.graduation.project.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "student")
@Getter
@Setter
@NoArgsConstructor
public class Student {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column(name = "firstName", length = 100, nullable = false)
    private String firstName;

    @Column(name = "lastName", length = 100, nullable = false)
    private String lastName;

    @Column(name = "studentId", length = 15, nullable = false)
    private String studentId;

    @Column(name = "course", length = 250, nullable = false)
    private String course;

    @Column
    private String role;
}
