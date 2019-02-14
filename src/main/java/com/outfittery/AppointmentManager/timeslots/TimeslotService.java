package com.outfittery.AppointmentManager.timeslots;

import com.outfittery.AppointmentManager.stylists.StylistRespository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
public class TimeslotService {

    @Value("${timeslotsPerDay}")
    private int timeslotsPerDay;

    @Value("${timeslotsStartAt}")
    private String timeslotsStartAt;


    @Value("${timeslotDuration}")
    private int timeslotDuration;

    @Value("${timeslotAvailabilityWindow}")
    private int timeslotAvailabilityWindow;

    private Map<Date, Integer> timeslotStylistMap;

    private StylistRespository stylistRespository;

    @Autowired
    public TimeslotService(StylistRespository stylistRespository) {
        this.stylistRespository = stylistRespository;
    }

    @PostConstruct
    private void init() {
        timeslotStylistMap = new HashMap<>();

        Date timeslot = getFirstTimeSlot(timeslotsStartAt);

        int duration = timeslotDuration;
        int window = timeslotAvailabilityWindow;

        for(int i = 0; i < timeslotsPerDay * window; i++){
            timeslotStylistMap.put(timeslot, 0);
            timeslot = getNextTimeslot(timeslot, duration);
        }

    }

    private Date getFirstTimeSlot(String timeslotsStartAt) {
        String[] split = timeslotsStartAt.split(":");
        int startHour = Integer.parseInt(split[0]);
        int startMinutes = Integer.parseInt(split[1]);

        Calendar cal = Calendar.getInstance();

        int nowMinutes = cal.get(Calendar.MINUTE);
        int nowHour = cal.get(Calendar.HOUR_OF_DAY);
        int nowDay = cal.get(Calendar.DAY_OF_MONTH);

        int endHour = startHour + (timeslotsPerDay * timeslotDuration) / 60;

        if(nowHour >= endHour) {
            nowDay += 1;
        }

        cal.set(Calendar.DAY_OF_MONTH, nowDay);
        cal.set(Calendar.HOUR_OF_DAY, startHour);
        cal.set(Calendar.MINUTE, startMinutes);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private Date getNextTimeslot(Date timeslot, int duration) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(timeslot);

        // last slot in day
        if(cal.get(Calendar.HOUR_OF_DAY) == 16 && cal.get(Calendar.MINUTE) == 30) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
            cal.set(Calendar.HOUR_OF_DAY, 9);
            cal.set(Calendar.MINUTE, 0);
        } else {
            cal.add(Calendar.MINUTE, duration);
        }
        return cal.getTime();
    }

    public List<Date> listAvailableTimeslots(int daysFromNow) {
        long stylistsCount = stylistRespository.count();
        List<Date> availableTimeslots = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, daysFromNow);
        cal.set(Calendar.HOUR_OF_DAY, Math.min(Math.max(9, cal.get(Calendar.HOUR_OF_DAY)), 16));
        cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) < 30 ? 30 : 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date upperBound = cal.getTime();

        availableTimeslots.addAll(
            timeslotStylistMap.entrySet()
                .stream()
                .filter(t -> (t.getKey().compareTo(upperBound) < 0)
                        && (t.getValue() < stylistsCount))
                .map(t -> {
                    cal.setTime(t.getKey());
                    return cal.getTime();
                })
                .collect(Collectors.toList())
        );

        return availableTimeslots;
    }

    public boolean isTimeslotAvailable(Date timeslot) throws InvalidTimeslotException {
        if(timeslotStylistMap.containsKey(timeslot)) {
            return timeslotStylistMap.get(timeslot) <= stylistRespository.count();
        } else {
            throw new InvalidTimeslotException("Timeslot not found!");
        }
    }

    public void bookTimeslot(Date timeslot) {
        // increment stylist count for given timeslot
        Integer slotStylistCount = timeslotStylistMap.get(timeslot);
        timeslotStylistMap.put(timeslot, slotStylistCount + 1);
    }
}
