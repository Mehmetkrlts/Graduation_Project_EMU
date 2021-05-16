package com.graduation.project.controller;

import com.graduation.project.model.entities.Student;
import com.graduation.project.service.impl.StudentDetailsServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    private final StudentDetailsServiceImpl studentDetailsService;

    public StudentController(StudentDetailsServiceImpl studentDetailsService) {
        this.studentDetailsService = studentDetailsService;
    }


    @RequestMapping(value = "/createstudent",method = RequestMethod.POST)
    public ResponseEntity<String> saveStudent(@RequestBody Student student) throws Exception {
        studentDetailsService.createStudent(student);
        return ResponseEntity.ok("Student registered");
    }

    @RequestMapping("/students")
    public ResponseEntity<?> findAllStudents() {
        final List<Student> students = studentDetailsService.findAllStudents();
        return ResponseEntity.ok(students);
    }

    @RequestMapping("/student/{id}")
    public ResponseEntity<Student> findStudent(@PathVariable("id") Long id){
        final Student student = studentDetailsService.findStudentById(id);
        return ResponseEntity.ok(student);
    }

    @RequestMapping("/updatestudent/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable("id") Long id, @RequestBody Student student){
        studentDetailsService.findStudentById(id);
        student.setId(id);
        studentDetailsService.updateStudent(student);
        return ResponseEntity.ok("Student is updated");
    }

    @RequestMapping("/deletestudent/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable("id") Long id){
        studentDetailsService.deleteStudent(id);
        return ResponseEntity.ok("Student with "+id+" is deleted");
    }
}
