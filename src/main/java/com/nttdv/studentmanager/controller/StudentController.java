package com.nttdv.studentmanager.controller;

import com.nttdv.studentmanager.model.Student;
import com.nttdv.studentmanager.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private IStudentService studentService;

    @GetMapping("/list")
    public ResponseEntity<List<Student>> getAllStudent() {
        return new ResponseEntity<>(studentService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable("id") Long id) {
        Optional<Student> student = studentService.findById(id);
//        if (student.isPresent()) {
//            return new ResponseEntity<>(student.get(), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return student.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/add")
    public ResponseEntity<Student> addStudent(@Valid @RequestBody Student student) {
        Student newStudent = studentService.save(student);
        return new ResponseEntity<>(newStudent, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Student> editStudent(@PathVariable("id") Long id, @Valid @RequestBody Student student) {
        Optional<Student> s = studentService.findById(id);
        if (!s.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
            student.setId(s.get().getId());
        return new ResponseEntity<>(studentService.save(student), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable("id") Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        studentService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> findStudentByName(@RequestBody Student student) {
        List<Object> list = new ArrayList<>();
        List<Student> studentList = studentService.findStudentByName(student.getName());
        studentList.add(student);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
