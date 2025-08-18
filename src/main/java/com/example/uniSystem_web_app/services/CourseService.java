package com.example.uniSystem_web_app.services;

import com.example.uniSystem_web_app.entities.Course;
import com.example.uniSystem_web_app.entities.Section;
import com.example.uniSystem_web_app.entities.Student;
import com.example.uniSystem_web_app.repositories.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    public Course getCourseByCourseId(String courseId){
        return courseRepository.findByCourseId(courseId);
    }

    public List<Section> getAllSections(Course course){
        return course.getSections();
    }

}