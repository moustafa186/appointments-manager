package com.outfittery.AppointmentManager.timeslots;

import com.outfittery.AppointmentManager.stylists.StylistRespository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
@NoArgsConstructor
public class TimeslotService {

    @Value("${timeslotsPerDay}")
    private int timeslotsPerDay;

    @Value("${timeslotsStartAt}")
    private String timeslotsStartAt;


    @Value("${timeslotDuration}")
    private String timeslotDuration;

    private Map<Date, Integer> timeslotStylistMap;

    StylistRespository stylistRespository;

    @Autowired
    public TimeslotService(StylistRespository stylistRespository) {
        this.stylistRespository = stylistRespository;
    }

    @PostConstruct
    private void init() {
        timeslotStylistMap = new HashMap<>();

        Date timeslot = getFirstTimeSlot(timeslotsStartAt);
        int duration = Integer.parseInt(timeslotDuration);

        for(int i = 0; i < timeslotsPerDay; i++){
            timeslotStylistMap.put(timeslot, 0);
            timeslot = getNextTimeslot(timeslot, duration);
        }

    }

    private Date getFirstTimeSlot(String timeslotsStartAt) {
        String[] split = timeslotsStartAt.split(":");
        int startHour = Integer.parseInt(split[0]);
        int startMinutes = Integer.parseInt(split[1]);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, startHour);
        cal.set(Calendar.MINUTE, startMinutes);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private Date getNextTimeslot(Date timeslot, int duration) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(timeslot);
        cal.add(Calendar.MINUTE, duration);
        return cal.getTime();
    }

    public List<Date> listAvailableTimeslots(int daysFromNow) {

        long stylistsCount = stylistRespository.count();

        List<Date> availableTimeslots = new ArrayList<>();
        for (Map.Entry<Date, Integer> t: timeslotStylistMap.entrySet()) {
            if(t.getValue() < stylistsCount) {
                availableTimeslots.add(t.getKey());
            }
        }
        return availableTimeslots;
    }

    public boolean isTimeslotAvailable(Date timeslot) {
        return timeslotStylistMap.get(timeslot) < stylistRespository.count();
    }
}
