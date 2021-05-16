package com.graduation.project.service;

import com.graduation.project.model.entities.Student;

import java.util.List;

public interface StudentDetailsService {
     List<Student> findAllStudents();

     Student findStudentById(Long id);

     void createStudent(Student student);

     void updateStudent(Student student);

     void deleteStudent(Long id);

}
