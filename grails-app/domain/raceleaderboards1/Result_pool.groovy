package raceleaderboards1

import java.sql.Time

class Result_pool {
    long race_id
    String first_name
    String family_name
    String email
    long bib_number
    int gender
    long country

    long running_category
    long age_category
    long overall_position
    long gender_position
    long category_position
    Time swim_leg_time
    Time run_leg_time
    Time cycle_leg_time
    Time t1_leg_time
    Time t2_leg_time
    Time result_time
    Time start_time
    Time finish_time
    static constraints = {
    }
}
