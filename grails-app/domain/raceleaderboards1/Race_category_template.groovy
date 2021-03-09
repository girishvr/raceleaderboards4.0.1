package raceleaderboards1

import java.sql.Time

class Race_category_template {
    int id
    String description
    Time swim_cut_off_time
    Time run_cut_off_time
    Time cycle_cut_off_time
    long swim_distance
    long run_distance
    long cycle_distance

    static constraints = {
    }
}
