package com.example.uniSystem_web_app.services;

import com.example.uniSystem_web_app.entities.Course;
import com.example.uniSystem_web_app.entities.Section;
import com.example.uniSystem_web_app.entities.Student;
import com.example.uniSystem_web_app.exceptions.SectionNotFoundException;
import com.example.uniSystem_web_app.repositories.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;

    @Autowired
    public SectionService(SectionRepository sectionRepository){
        this.sectionRepository = sectionRepository;
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

}
