package com.example.uniSystem_web_app.entities;

import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "time_periods")
public class TimePeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalTime startTime;
    private LocalTime endTime;

    @OneToMany(
            mappedBy = "timePeriod"
            , cascade = CascadeType.ALL
            , orphanRemoval = true
    )
    private List<TimePeriodDay> days = new ArrayList<TimePeriodDay>();

    public TimePeriod(){}

    public TimePeriod(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public List<TimePeriodDay> getDays() {
        return days;
    }

    public void setDays(List<TimePeriodDay> days) {
        this.days = days;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i < days.size() ; i++) {
            sb.append(days.get(i).getWeekDay());
            if(i != days.size() - 1) sb.append("-");
            else sb.append(" ");
        }
        sb.append(startTime);
        sb.append("--");
        sb.append(endTime);
        return sb.toString();
    }
}
