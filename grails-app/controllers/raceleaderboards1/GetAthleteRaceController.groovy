package raceleaderboards1
import groovy.sql.Sql
import raceleaderboards1.Age_group

class GetAthleteRaceController {

    def dataSource

    def index() { 

      def firstName=params.firstName
      def lastName=params.lastName
      def country=params.country
      def athleteRaceType=params.athleteRaceType

      def db = new Sql(dataSource);

      def queryCountry="SELECT c.id from `country` c where name='"+country+"'";
      def resultCountry = db.rows(queryCountry);
      def countryId=resultCountry[0]['id']

      def query="SELECT r.id,r.race_id as race_id,r.gender as gender,r.first_name as first_name,r.family_name as family_name,r.bib_number as bib_number,c.name as name,a.age_group as age_group,r.overall_position as overall_position,r.gender_position as gender_position,r.category_position as category_position,r.swim_leg_time as swim_leg_time,r.run_leg_time as run_leg_time,r.cycle_leg_time as cycle_leg_time,r.t1_leg_time as t1_leg_time,r.t2_leg_time as t2_leg_time,r.start_time as start_time,r.finish_time as finish_time,r.result_time as result_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.age_category=a.id and r.race_type="+athleteRaceType+" and first_name='"+firstName+"' and family_name='"+lastName+"' and country="+countryId+" and result_time != '00:00:00'";
      def result = db.rows(query);

      renderJSONResponse(result);
    }

    def renderJSONResponse(result){
        respond result.collect {
            [ 
              id:it.id,
              name:it.first_name+' '+it.family_name,
              bib_number:it.bib_number,
              gender:it.gender,
              country:it.name,
              age_group:it.age_group,
              overall_position:it.overall_position,
              category_position:it.category_position,
              gender_position:it.gender_position,
              swim_time:it.swim_leg_time.toString(),
              cycle_time:it.cycle_leg_time.toString(),
              run_time:it.run_leg_time.toString(),
              t1_time:it.t1_leg_time.toString(),
              t2_time:it.t2_leg_time.toString(),
              start_time:it.start_time.toString(),
              finish_time:it.finish_time.toString(),
              result_time:it.result_time.toString()
            ]
        }, [formats: ['json']];
    }
}
