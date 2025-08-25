package com.example.uniSystem_web_app.repositories;

import com.example.uniSystem_web_app.entities.Course;
import com.example.uniSystem_web_app.entities.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>{
    Course findByCourseId(String courseNumber);
    List<Course> findByFaculty(Faculty faculty);
    List<Course> findAllByCourseId(String courseId);
}
