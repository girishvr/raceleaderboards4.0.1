package raceleaderboards1
import groovy.sql.Sql

class Result_categoryBasedController {

	def dataSource

    def index() { 

		println params.raceId
    	println params.ageCategory
    	println params.gender

    	def raceId=params.raceId
    	def ageCategory=params.ageCategory
    	def gender=params.gender

    	def db = new Sql(dataSource);
    	def query

    	if(ageCategory=='all'){
    		query= "SELECT r.race_id as id,r.gender as gender,r.first_name as first_name,r.family_name as family_name,r.bib_number as bib_number,c.name as name,a.age_group as age_group,r.overall_position as overall_position,r.gender_position as gender_position,r.category_position as category_position,r.swim_leg_time as swim_leg_time,r.run_leg_time as run_leg_time,r.cycle_leg_time as cycle_leg_time,r.t1_leg_time as t1_leg_time,r.t2_leg_time as t2_leg_time,r.start_time as start_time,r.finish_time as finish_time,r.result_time as result_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.age_category=a.id and r.race_id="+raceId+" and r.gender="+gender;
        }else{
        	query= "SELECT r.race_id as id,r.gender as gender,r.first_name as first_name,r.family_name as family_name,r.bib_number as bib_number,c.name as name,a.age_group as age_group,r.overall_position as overall_position,r.gender_position as gender_position,r.category_position as category_position,r.swim_leg_time as swim_leg_time,r.run_leg_time as run_leg_time,r.cycle_leg_time as cycle_leg_time,r.t1_leg_time as t1_leg_time,r.t2_leg_time as t2_leg_time,r.start_time as start_time,r.finish_time as finish_time,r.result_time as result_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.age_category=a.id and r.race_id="+raceId+" and r.gender="+gender+" and r.age_category="+ageCategory;
		}

        def result1 = db.rows(query);
        renderJSONResponse(result1);
    }

    def renderJSONResponse(result1){
        respond result1.collect {
            [ name:it.first_name+' '+it.family_name,
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
