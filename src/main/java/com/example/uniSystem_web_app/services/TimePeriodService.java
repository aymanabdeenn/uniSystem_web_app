package com.example.uniSystem_web_app.services;

import com.example.uniSystem_web_app.entities.TimePeriod;
import com.example.uniSystem_web_app.entities.TimePeriodDay;
import com.example.uniSystem_web_app.repositories.TimePeriodDayRepository;
import com.example.uniSystem_web_app.repositories.TimePeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;

@Service
public class TimePeriodService {

    private final TimePeriodRepository timePeriodRepository;
    private final TimePeriodDayRepository timePeriodDayRepository;

    @Autowired
    public TimePeriodService(TimePeriodRepository timePeriodRepository , TimePeriodDayRepository timePeriodDayRepository){
        this.timePeriodRepository = timePeriodRepository;
        this.timePeriodDayRepository = timePeriodDayRepository;
    }

    public List<TimePeriod> getAllTimePeriods(){
        return timePeriodRepository.findAll();
    }

    public HashSet<String> fillAHashSetWithDays(List<String> days){
        HashSet<String> set = new HashSet<String>();
        for(String day : days) set.add(day);
        return set;
    }

    public boolean timePeriodExists(LocalTime startTime , LocalTime endTime , List<String> days){
        List<TimePeriod> timePeriods = timePeriodRepository.findByStartTimeAndEndTime(startTime , endTime);

        for(TimePeriod tp : timePeriods) {
            HashSet<String> set = fillAHashSetWithDays(days);
            int matches = 0;
            for(TimePeriodDay tpd : tp.getDays()){
                if(set.contains(tpd.getWeekDay())) matches++;
            }
            if(matches == tp.getDays().size()) return true;
        }

        return false;
    }

    public void createNewTimePeriod(LocalTime startTime , LocalTime endTime , List<String> days){
        TimePeriod tp = new TimePeriod(startTime , endTime);

        for(String day : days){
            TimePeriodDay tpd = new TimePeriodDay(day);
            tpd.setTimePeriod(tp);
            tp.getDays().add(tpd);
        }

        timePeriodRepository.save(tp);
    }

}
