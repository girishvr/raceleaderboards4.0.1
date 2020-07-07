package raceleaderboards1
import groovy.sql.Sql

class GlobalresultController {
    def dataSource;

    def index() {
        def name=params.querySearch;
        def year=params.year;
        if (params.cat == 'Ironman 70.3')
        {
            def db=new Sql(dataSource);
            def query="SELECT r.id as id,r.city AS city, r.country AS country, r.state AS state, EXTRACT( YEAR\n" +
                    "FROM r.start_date ) AS year, r.race_name AS race, d.description AS category\n" +
                    "FROM races r, race_category_template d\n" +
                    "WHERE r.race_category_template_id = d.id and r.race_category_template_id=13 and YEAR(r.start_date)="+year+" and r.race_name like '%"+name+"%'";
            def races=db.rows(query);
            renderJSONResponse(races)
        }
        if (params.cat == 'Ironman')
        {
            def db=new Sql(dataSource);
            def query="SELECT r.id as id,r.city AS city, r.country AS country, r.state AS state, EXTRACT( YEAR\n" +
                    "FROM r.start_date ) AS year, r.race_name AS race, d.description AS category\n" +
                    "FROM races r, race_category_template d\n" +
                    "WHERE r.race_category_template_id = d.id and r.race_category_template_id=2 and YEAR(r.start_date)="+year+" and r.race_name like '%"+name+"%'";
            def races=db.rows(query);
            renderJSONResponse(races)
        }

    }
    /* I think this is useless, may need to delete later */
    def raceSearch(){
        def name=params.querySearch;
        def cat=params.cat;
        def db = new Sql(dataSource);
        def query;
        if(cat=='Ironman') {
            query = "SELECT r.id as id,r.city AS city, r.country AS country, r.state AS state, EXTRACT( YEAR FROM r.start_date ) AS year, r.race_name AS race, d.description AS category FROM races r, race_category_template d WHERE r.race_category_template_id = d.id and r.race_name like '%" + name + "%' and r.race_category_template_id=2";
        }
        else {
            query = "SELECT r.id as id,r.city AS city, r.country AS country, r.state AS state, EXTRACT( YEAR FROM r.start_date ) AS year, r.race_name AS race, d.description AS category FROM races r, race_category_template d WHERE r.race_category_template_id = d.id and r.race_name like '%" + name + "%' and r.race_category_template_id=13";
        }
        def result1 = db.rows(query);
        renderJSONResponse(result1);


    }
    def getResult()
    {
        def id=params.id;
        def db = new Sql(dataSource);
        def query = "SELECT r.race_id as id,r.gender as gender,r.first_name as first_name,r.family_name as family_name,r.bib_number as bib_number,c.name as name,a.age_group as age_group,r.overall_position as overall_position,r.gender_position as gender_position,r.category_position as category_position,r.swim_leg_time as swim_leg_time,r.run_leg_time as run_leg_time,r.cycle_leg_time as cycle_leg_time,r.t1_leg_time as t1_leg_time,r.t2_leg_time as t2_leg_time,r.start_time as start_time,r.finish_time as finish_time,r.result_time as result_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.age_category=a.id and r.race_id="+id;
        def result1 = db.rows(query);
        renderJSONResponse1(result1);
    }
    def renderJSONResponse1(result1){
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

    def renderJSONResponse(races) {

        respond races.collect {
            [id                 : it.id,
             city          : it.city,
             country : it.country,
             state :it.state,
             year:it.year,
             race:it.race,
             category:it.category
             /*
                  person:it.person.collect{
                      [
                              id:it.id,
                              firstName:it.firstName,
                              lastName:it.lastName
                      ]
                  }*/
            ]
        }, [formats: ['json']];
    }

def renderJSONResponse2(races) {

    respond races.collect {
        [
         race:it.race_name
        ]
    }, [formats: ['json']];
}
}