package raceleaderboards1
import groovy.sql.Sql
import raceleaderboards1.Age_group
import grails.converters.JSON

class YoskaTriDashboardResultController {

    def dataSource

    def index() { 

      println "========================params"
      println params

      def raceType=params.raceType
      def indiaGlobal=params.indiaGlobal
      def filterOnGender=params.filterOnGender
      def athleteGender=params.gender
      def athleteAgeCategory=params.athleteAgeCategory

      def gender
      if(athleteGender=='male'){
        gender="gender=0"
      }else if(athleteGender=='female'){
        gender="gender=1"
      }else{
        gender="(gender=0 or gender=1)"
      }

      /*println athleteGender
      println athleteAgeCategory*/

      def ageCategory='0'
      Age_group[] ageGroup=Age_group.findAll()
      for(def ag=0;ag<ageGroup.length;ag++){
        if(ageGroup[ag]['age_group']==athleteAgeCategory){
          /*if(filterOnGender=='true'){
            if(athleteGender==ageGroup[ag]['gender']){
              ageCategory=ageCategory+","+ageGroup[ag]['id']
            }
          }else{*/
            ageCategory=ageCategory+","+ageGroup[ag]['id']
          /*}*/
        }
      }

      println "ageCategory"
      println ageCategory

      def swim_leg_time
      def run_leg_time
      def cycle_leg_time
      def overall_time

      def db = new Sql(dataSource);

      if(params.raceSelected != null){
        def query_race_data= "SELECT swim_leg_time,run_leg_time,cycle_leg_time,result_time FROM `result_pool` where id="+params.raceSelected;
        def result_race_data = db.rows(query_race_data);

        swim_leg_time=result_race_data[0]['swim_leg_time']
        run_leg_time=result_race_data[0]['run_leg_time']
        cycle_leg_time=result_race_data[0]['cycle_leg_time']

        overall_time=convertStringTimeToSec((result_race_data[0]['swim_leg_time']).toString())+convertStringTimeToSec((result_race_data[0]['run_leg_time']).toString())+convertStringTimeToSec((result_race_data[0]['cycle_leg_time']).toString())
        overall_time=convertSecToStringTime(overall_time)
      }else{

        /*println "athleteRaceType"
        println params.athleteRaceType*/

        def query_race_type= "SELECT swim_distance,run_distance,cycle_distance FROM `race_category_template` where id="+params.athleteRaceType;
        def result_race_type = db.rows(query_race_type);

        /*println "result_race_type"
        println result_race_type*/

        /*println convertStringTimeToSec(params.swimDuration)
        println result_race_type[0]['swim_distance']
        println params.swimDistanceInMeters*/

        // example for 100 meters (swim duration) ,swim time is x, then for half marathon ditance what is the swim time
        swim_leg_time=(convertStringTimeToSec(params.swimDuration) * result_race_type[0]['swim_distance'])/Float.parseFloat(params.swimDistanceInMeters)
        /*println "swim_leg_time "+ swim_leg_time*/

        run_leg_time=(convertStringTimeToSec(params.runDuration) * result_race_type[0]['run_distance'])/Float.parseFloat(params.runDistanceInMeters)
        /*println "run_leg_time "+ run_leg_time*/


        cycle_leg_time=(convertStringTimeToSec(params.bikeDuration) * result_race_type[0]['cycle_distance'])/Float.parseFloat(params.bikeDistanceInMeters)
        /*println "cycle_leg_time "+ cycle_leg_time*/


        overall_time=swim_leg_time+run_leg_time+cycle_leg_time

        swim_leg_time=convertSecToStringTime(swim_leg_time)
        run_leg_time=convertSecToStringTime(run_leg_time)
        cycle_leg_time=convertSecToStringTime(cycle_leg_time)
        overall_time=convertSecToStringTime(overall_time)
      }

      /*println "final timing"
      println swim_leg_time
      println run_leg_time
      println cycle_leg_time
      println overall_time*/

      def race_cut_off_timing= "SELECT swim_cut_off_time,run_cut_off_time,cycle_cut_off_time FROM `race_category_template` where id="+params.athleteRaceType;
      def result_cut_off_timing = db.rows(race_cut_off_timing);
      
      def swim_cut_off_time=result_cut_off_timing[0]['swim_cut_off_time']
      def run_cut_off_time=result_cut_off_timing[0]['run_cut_off_time']
      def cycle_cut_off_time=result_cut_off_timing[0]['cycle_cut_off_time']

      def best_swim_time="00:00:00"
      def best_run_time="00:00:00"
      def best_cycle_time="00:00:00"

      def query_best_swim_time
      if(indiaGlobal=='India'){
        query_best_swim_time= "SELECT shortest_swim_leg_time FROM `race_top_data` where "+gender+" and age_category in ("+ageCategory+") and race_type="+params.athleteRaceType+" and india_global='India'";
      }else{
        query_best_swim_time= "SELECT shortest_swim_leg_time FROM `race_top_data` where "+gender+" and age_category in ("+ageCategory+") and race_type="+params.athleteRaceType+" and india_global='Global'";
      }
      def result_best_swim_time = db.rows(query_best_swim_time);
      best_swim_time= result_best_swim_time[0]['shortest_swim_leg_time']

      def query_best_run_time
      if(indiaGlobal=='India'){
        query_best_run_time= "SELECT shortest_run_leg_time FROM `race_top_data` where "+gender+" and age_category in ("+ageCategory+") and race_type="+params.athleteRaceType+" and india_global='India'";
      }else{
        query_best_run_time= "SELECT shortest_run_leg_time FROM `race_top_data` where "+gender+" and age_category in ("+ageCategory+") and race_type="+params.athleteRaceType+" and india_global='Global'";
      }
      def result_best_run_time = db.rows(query_best_run_time);
      best_run_time= result_best_run_time[0]['shortest_run_leg_time']

      def query_best_cycle_time
      if(indiaGlobal=='India'){
        query_best_cycle_time= "SELECT shortest_cycle_leg_time FROM `race_top_data` where "+gender+" and age_category in ("+ageCategory+") and race_type="+params.athleteRaceType+" and india_global='India'";
      }else{
        query_best_cycle_time= "SELECT shortest_cycle_leg_time FROM `race_top_data` where "+gender+" and age_category in ("+ageCategory+") and race_type="+params.athleteRaceType+" and india_global='Global'"
      }
      def result_best_cycle_time = db.rows(query_best_cycle_time);
      best_cycle_time= result_best_cycle_time[0]['shortest_cycle_leg_time']



      //Positions
      def query_result_time_age_group
      def query_result_time_overall
      if(indiaGlobal=='India'){
        query_result_time_age_group= "SELECT result_time FROM `result_pool` where "+gender+" and age_category in ("+ageCategory+") and race_type="+params.athleteRaceType+" and country=99 order by result_time ASC";
        query_result_time_overall= "SELECT result_time FROM `result_pool` where "+gender+" and race_type="+params.athleteRaceType+"  and country=99 order by result_time ASC";
      }else{
        query_result_time_age_group= "SELECT result_time FROM `result_pool` where "+gender+" and age_category in ("+ageCategory+") and race_type="+params.athleteRaceType+" order by result_time ASC";;
        query_result_time_overall= "SELECT result_time FROM `result_pool` where "+gender+" and race_type="+params.athleteRaceType+"  order by result_time ASC";
      }
      def result_time_age_group = db.rows(query_result_time_age_group);
      def result_time_overall = db.rows(query_result_time_overall);

      def ranking_age_group=1
      def total_age_group=result_time_age_group.size()
      for(def r1=0;r1<result_time_age_group.size()-1;r1++){

          /*println "two values"
          println convertStringTimeToSec((result_time_age_group[r1]['result_time']).toString())
          println convertStringTimeToSec((overall_time).toString())
          println "==============="*/

          def value1=convertStringTimeToSec((result_time_age_group[r1]['result_time']).toString())
          def value2=convertStringTimeToSec((overall_time).toString())
        
          if(value1 > value2){
            break;
          }

          if(r1==0){
            ranking_age_group=1
          }else if(convertStringTimeToSec((result_time_age_group[r1]['result_time']).toString()) < convertStringTimeToSec((result_time_age_group[r1+1]['result_time']).toString())){
            ranking_age_group=ranking_age_group+1;
          }
      }

      /*println "ranking_age_group"
      println ranking_age_group*/

      def ranking_overall=1
      def total_overall=result_time_overall.size()
      for(def r1=0;r1<result_time_overall.size()-1;r1++){

          /*println "two values"
          println convertStringTimeToSec((result_time_overall[r1]['result_time']).toString())
          println convertStringTimeToSec((overall_time).toString())
          println "==============="*/

          def value1=convertStringTimeToSec((result_time_overall[r1]['result_time']).toString())
          def value2=convertStringTimeToSec((overall_time).toString())
        
          if(value1 > value2){
            break;
          }

          if(r1==0){
            ranking_overall=1
          }else if(convertStringTimeToSec((result_time_overall[r1]['result_time']).toString()) < convertStringTimeToSec((result_time_overall[r1+1]['result_time']).toString())){
            ranking_overall=ranking_overall+1;
          }
      }

      /*println "ranking_overall"
      println ranking_overall*/

      //To find positions on the line graph

      /*whta percentage is 4 between 3 and 5
      x=4, a=3, b=5
      (x-a/b-a)*100
      (4-3/5-3)*100=50%*/

      //position for line graph
      def swim_portion=(convertStringTimeToSec((swim_leg_time).toString())-convertStringTimeToSec((best_swim_time).toString()))/(convertStringTimeToSec((swim_cut_off_time).toString())-convertStringTimeToSec((best_swim_time).toString()))
      def swim_percent=100-(swim_portion*100)

      def run_portion=(convertStringTimeToSec((run_leg_time).toString())-convertStringTimeToSec((best_run_time).toString()))/(convertStringTimeToSec((run_cut_off_time).toString())-convertStringTimeToSec((best_run_time).toString()))
      def run_percent=100-(run_portion*100)

      def cycle_portion=(convertStringTimeToSec((cycle_leg_time).toString())-convertStringTimeToSec((best_cycle_time).toString()))/(convertStringTimeToSec((cycle_cut_off_time).toString())-convertStringTimeToSec((best_cycle_time).toString()))
      def cycle_percent=100-(cycle_portion*100)

      //position for round graph
      def ranking_age_group_percent=(ranking_age_group/total_age_group)*100
      def ranking_overall_percent=(ranking_overall/total_overall)*100

      if(ranking_age_group_percent<=20){
        ranking_age_group_percent="-80"
      }else if(ranking_age_group_percent>20 && ranking_age_group_percent<=40){
        ranking_age_group_percent="-60"
      }else if(ranking_age_group_percent>40 && ranking_age_group_percent<=60){
        ranking_age_group_percent="0"
      }else if(ranking_age_group_percent>60 && ranking_age_group_percent<=80){
        ranking_age_group_percent="60"
      }else if(ranking_age_group_percent>80 && ranking_age_group_percent<100){
        ranking_age_group_percent="80"
      }else if(ranking_age_group_percent==100){
        ranking_age_group_percent="90"
      }

      if(ranking_overall_percent<=20){
        ranking_overall_percent="-80"
      }else if(ranking_overall_percent>20 && ranking_overall_percent<=40){
        ranking_overall_percent="-60"
      }else if(ranking_overall_percent>40 && ranking_overall_percent<=60){
        ranking_overall_percent="0"
      }else if(ranking_overall_percent>60 && ranking_overall_percent<=80){
        ranking_overall_percent="60"
      }else if(ranking_overall_percent>80 && ranking_overall_percent<100){
        ranking_overall_percent="80"
      }else if(ranking_overall_percent==100){
        ranking_overall_percent="90"
      }

      def result=[
          swim_leg_time:swim_leg_time,
          run_leg_time:run_leg_time,
          cycle_leg_time:cycle_leg_time,
          overall_time:overall_time,
          best_cycle_time:best_cycle_time,
          best_run_time:best_run_time,
          best_swim_time:best_swim_time,
          swim_cut_off_time:swim_cut_off_time,
          run_cut_off_time:run_cut_off_time,
          cycle_cut_off_time:cycle_cut_off_time,
          swim_percent:swim_percent,
          run_percent:run_percent,
          cycle_percent:cycle_percent,
          ranking_age_group:ranking_age_group,
          ranking_overall:ranking_overall,
          total_age_group:total_age_group,
          total_overall:total_overall,
          ranking_age_group_percent:ranking_age_group_percent,
          ranking_overall_percent:ranking_overall_percent
      ]

      println result['ranking_age_group_percent']
      println result['ranking_overall_percent']

      render text:(result as JSON),contentType:'application/json';
    }

    def convertStringTimeToSec(timeString){
        if(timeString!=null && !(timeString.equals(":"))) {
            def arrTime = timeString.split(":");
            if (arrTime.size() > 0) {
                def hr = Float.parseFloat(arrTime[0])
                def min = Float.parseFloat(arrTime[1]);
                def sec = Float.parseFloat(arrTime[2]);
                def totalSec = (hr * 3600) + (min * 60) + sec;
                // min = min+sec;
                return totalSec;
            }
            else{
                return 0;
            }
        }
        else
            return 0;
    }

    def convertSecToStringTime(seconds) {
        if (seconds != null) {
            def sec = seconds % 60;
          def min
          def hours
          if(seconds < 60){
            min=0
            hours=0
          }else{
            min=Math.floor(seconds /60);
            if(min < 60){
              hours=0
            }else{
              hours = Math.floor(min/60);
              min=min-(hours*60)
            }
          }


          sec =String.valueOf(sec)
          min =String.valueOf(min)
          hours =String.valueOf(hours)

          if (sec.contains(".") == true) {
            sec=sec.substring(0, sec.indexOf("."));
          }
          if (min.contains(".") == true) {
            min=min.substring(0, min.indexOf("."));
          }
          if (hours.contains(".") == true) {
            hours=hours.substring(0, hours.indexOf("."));
          }


          int lengthSec = sec.length();
          int lengthMin = min.length();
          int lengthHour = hours.length();

          if(lengthSec == 1){
            sec="0"+sec
          }
          if(lengthMin == 1){
            min="0"+min
          }
          if(lengthHour == 1){
            hours="0"+hours
          }

          System.out.println(hours + ":" + min + ":" + sec);

          def stringTime=hours + ":" + min + ":" + sec
          return stringTime
        } else
            return 0;
    }
}
