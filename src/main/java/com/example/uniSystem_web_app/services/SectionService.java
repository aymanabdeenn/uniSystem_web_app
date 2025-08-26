package com.example.uniSystem_web_app.services;

import com.example.uniSystem_web_app.entities.Course;
import com.example.uniSystem_web_app.entities.Section;
import com.example.uniSystem_web_app.entities.Student;
import com.example.uniSystem_web_app.entities.TimePeriod;
import com.example.uniSystem_web_app.exceptions.SectionNotFoundException;
import com.example.uniSystem_web_app.exceptions.TimePeriodNotFoundException;
import com.example.uniSystem_web_app.repositories.CourseRepository;
import com.example.uniSystem_web_app.repositories.SectionRepository;
import com.example.uniSystem_web_app.repositories.TimePeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;
    private final CourseRepository courseRepository;
    private final TimePeriodRepository timePeriodRepository;

    @Autowired
    public SectionService(SectionRepository sectionRepository , CourseRepository courseRepository , TimePeriodRepository timePeriodRepository){
        this.sectionRepository = sectionRepository;
        this.courseRepository = courseRepository;
        this.timePeriodRepository = timePeriodRepository;
    }

    public Section getSectionBySectionId(Long sectionId){
        return sectionRepository.findById(sectionId).orElseThrow(() -> new SectionNotFoundException("Section does not exist"));
    }

    public List<Student> getSectionStudents(Section section){
        return section.getStudents();
    }

    public Section getCertainSectionFromACourse(Course course , Long sectionId){
        for(Section section : course.getSections()){
            if(section.getId() == sectionId) return section;
        }
        return null;
    }

    public Section getCertainSectionFromACourseWithNumber(Course course , int sectionNumber){
        for(Section section : course.getSections()){
            if(section.getSectionNumber() == sectionNumber) return section;
        }
        return null;
    }

    public Section createNewSection(Course course , Long timePeriodId , int capacity){
        int sectionNumber = course.getSections().size() + 1;
        TimePeriod timePeriod = timePeriodRepository.findById(timePeriodId).orElseThrow(() -> new TimePeriodNotFoundException("Time period not found."));
        Section section = new Section(course , sectionNumber , timePeriod , capacity);
        course.getSections().add(section);
        courseRepository.save(course);
        return section;
    }

}
