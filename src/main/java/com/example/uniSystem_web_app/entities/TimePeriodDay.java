package com.example.uniSystem_web_app.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "time_periods_days")
public class TimePeriodDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String weekDay;

    @ManyToOne
    @JoinColumn(name = "time_period_id")
    private TimePeriod timePeriod;

    public TimePeriodDay(){}

    public TimePeriodDay(String weekDay){
        this.weekDay = weekDay;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getWeekDay(){
        return weekDay;
    }

    public void setWeekDay(String weekDay){
        this.weekDay = weekDay;
    }

    public TimePeriod getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(TimePeriod timePeriod) {
        this.timePeriod = timePeriod;
    }
}
