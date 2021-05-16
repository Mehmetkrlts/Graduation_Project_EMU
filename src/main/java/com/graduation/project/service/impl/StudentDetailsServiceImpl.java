package com.graduation.project.service.impl;

import com.graduation.project.exception.NotFoundException;
import com.graduation.project.model.entities.Student;
import com.graduation.project.repository.StudentRepository;

import com.graduation.project.service.StudentDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class StudentDetailsServiceImpl implements UserDetailsService , StudentDetailsService {

    private final StudentRepository studentRepository;

    private final PasswordEncoder bcryptEncoder;

    private final JuryDetailsServiceImpl juryDetailsServiceImpl;


    public StudentDetailsServiceImpl(JuryDetailsServiceImpl juryDetailsServiceImpl, PasswordEncoder bcryptEncoder, StudentRepository studentRepository){

        this.juryDetailsServiceImpl = juryDetailsServiceImpl;
        this.bcryptEncoder = bcryptEncoder;
        this.studentRepository = studentRepository;
    };



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<SimpleGrantedAuthority> roles = null;

        Student student = studentRepository.findByUsername(username);
        if (student != null) {
            roles = Arrays.asList(new SimpleGrantedAuthority(student.getRole()));
            return new org.springframework.security.core.userdetails.User(student.getUsername(), student.getPassword(), roles);
        }

        return juryDetailsServiceImpl.loadUserByUsername(username);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Student findStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Student not found with ID %d", id)));
    }

    @Override
    public void createStudent(Student student) {
        Student newStudent = new Student();
        newStudent.setUsername(student.getUsername());
        newStudent.setPassword(bcryptEncoder.encode(student.getPassword()));
        newStudent.setFirstName(student.getFirstName());
        newStudent.setLastName(student.getLastName());
        newStudent.setStudentId(student.getStudentId());
        newStudent.setCourse(student.getCourse());
        newStudent.setRole("ROLE_STUDENT");
        studentRepository.save(newStudent);
    }

    @Override
    public void updateStudent(Student student) {
        student.setPassword(bcryptEncoder.encode(student.getPassword()));
        studentRepository.save(student);
    }


    @Override
    public void deleteStudent(Long id) {
        studentRepository.delete(findStudentById(id));
    }
}



