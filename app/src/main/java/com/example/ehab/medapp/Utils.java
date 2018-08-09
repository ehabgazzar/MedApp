package com.example.ehab.medapp;

import org.joda.time.LocalTime;

public class Utils {
    public static boolean isWithinInterval(LocalTime start,
                                           LocalTime end,
                                           LocalTime time) {
        if (start.isAfter(end)) {
            // Return true if the time is after (or at) start,
            // *or* it's before end
            return time.compareTo(start) >= 0 ||
                    time.compareTo(end) < 0;
        } else {
            return start.compareTo(time) <= 0 &&
                    time.compareTo(end) < 0;
        }
    }

    public static String getDayPart(LocalTime time)
    {
        LocalTime morning = new LocalTime(6, 0, 0);
        LocalTime evening = new LocalTime(18, 0, 0);
        LocalTime noon = new LocalTime(12, 0, 0);
        LocalTime midnight = new LocalTime(0, 0, 0);
        if(isWithinInterval(morning, noon, time))
            return "Morning";
        else if(isWithinInterval(noon, evening, time))
            return "AfterNoon";
        else if(isWithinInterval(evening,midnight, time))
            return "Evening";
        else if(isWithinInterval(midnight,morning, time))
            return "Night";

        return "";
    }
}
