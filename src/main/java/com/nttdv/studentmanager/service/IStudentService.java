package com.nttdv.studentmanager.service;

import com.nttdv.studentmanager.model.Student;

import java.util.List;
import java.util.Optional;

public interface IStudentService {
    List<Student> findAll();

    Optional<Student> findById(Long id);

    Student save(Student student);

    void delete(Long id);

    List<Student> findStudentByName(String name);
}
