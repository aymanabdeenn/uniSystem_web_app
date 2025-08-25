package com.example.uniSystem_web_app.services;

import com.example.uniSystem_web_app.entities.Course;
import com.example.uniSystem_web_app.entities.Doctor;
import com.example.uniSystem_web_app.entities.Faculty;
import com.example.uniSystem_web_app.entities.Student;
import com.example.uniSystem_web_app.exceptions.FacultyNotFoundException;
import com.example.uniSystem_web_app.repositories.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyService(FacultyRepository facultyRepository){
        this.facultyRepository = facultyRepository;
    }

    public Faculty getFacultyById(Long id){
        return facultyRepository.findById(id).orElseThrow(() -> new FacultyNotFoundException("Faculty not found."));
    }

    public List<Faculty> getAllFaculties(){
        return facultyRepository.findAll();
    }

    public List<Doctor> getAllDoctorsForFaculty(Faculty faculty){
        return faculty.getDoctors();
    }

    public List<Student> getAllStudentsForFaculty(Faculty faculty){
        return faculty.getStudents();
    }

    public List<Course> getAllCoursesForFaculty(Faculty faculty){
        return faculty.getCourses();
    }
}
