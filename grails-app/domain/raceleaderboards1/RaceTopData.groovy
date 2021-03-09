package raceleaderboards1

import java.sql.Time

class RaceTopData {
    int race_type
    long age_category 
    int gender
	Time shortest_swim_leg_time
    Time shortest_run_leg_time
    Time shortest_cycle_leg_time
    String indiaGlobal

    static constraints = {
    	gender nullable:true
    }
}
