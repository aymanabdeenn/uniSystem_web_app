package com.example.uniSystem_web_app.services;

import com.example.uniSystem_web_app.entities.Course;
import com.example.uniSystem_web_app.entities.Faculty;
import com.example.uniSystem_web_app.entities.Section;
import com.example.uniSystem_web_app.exceptions.FacultyNotFoundException;
import com.example.uniSystem_web_app.repositories.CourseRepository;
import com.example.uniSystem_web_app.repositories.FacultyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final FacultyRepository facultyRepository;

    public CourseService(CourseRepository courseRepository , FacultyRepository facultyRepository){
        this.courseRepository = courseRepository;
        this.facultyRepository = facultyRepository;
    }

    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    public Course getCourseByCourseId(String courseId){
        return courseRepository.findByCourseId(courseId);
    }

    public List<Course> getAllCoursesByCourseId(String courseId){
        return courseRepository.findAllByCourseId(courseId);
    }

    public List<Course> getCoursesByFaculty(Long facultyId){
        Faculty faculty = facultyRepository.findById(facultyId).orElseThrow(() -> new FacultyNotFoundException("Faculty not found"));
        return courseRepository.findByFaculty(faculty);
    }

    public List<Section> getAllSections(Course course){
        return course.getSections();
    }

    public Course createNewCourse(String name , String courseId , Long facultyId){
        Faculty faculty = facultyRepository.findById(facultyId).orElseThrow(() -> new FacultyNotFoundException("Faculty not found"));
        Course course = new Course(courseId , name , faculty);
        courseRepository.save(course);
        return course;
    }

}