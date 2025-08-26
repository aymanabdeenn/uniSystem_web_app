package com.example.uniSystem_web_app.repositories;

import com.example.uniSystem_web_app.entities.TimePeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface TimePeriodRepository extends JpaRepository<TimePeriod, Long> {

    List<TimePeriod> findByStartTimeAndEndTime(LocalTime startTime , LocalTime endTime);

}
