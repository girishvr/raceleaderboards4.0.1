package raceleaderboards1
import groovy.sql.Sql
import raceleaderboards1.Age_group
import raceleaderboards1.RaceTopData
import grails.gorm.transactions.Transactional

@Transactional
class SaveRaceTopDataService {

	def dataSource

	def index() { 
	}

	def clearAllData(){
		RaceTopData[] raceTopData=RaceTopData.findAll()
    	for(def r=0;r<raceTopData.length;r++){
    		raceTopData[r].delete(flush:true,failOnError:true); 
    	}
	}

    def saveDataIronman(){

    	def db = new Sql(dataSource);

    	def query1
    	def query2
    	def query3

    	def result1
    	def result2
    	def result3

    	/*Save all data for Triathlon - Ironman, country india for all age groups*/
    	Age_group[] ageGroup=Age_group.findAll()
    	for(def ag=0;ag<ageGroup.length;ag++){

    		//shortest swim time
    		query1= "SELECT r.swim_leg_time as swim_leg_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.age_category="+ageGroup[ag]['id']+" and r.race_type=2 and country=99 and result_time != '00:00:00' and swim_leg_time != '00:00:00' and run_leg_time != '00:00:00' and cycle_leg_time != '00:00:00' order by swim_leg_time ASC LIMIT 1";
    		result1 = db.rows(query1);
    		//shortest run time
    		query2= "SELECT r.run_leg_time as run_leg_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.age_category="+ageGroup[ag]['id']+" and r.race_type=2 and country=99 and result_time != '00:00:00' and swim_leg_time != '00:00:00' and run_leg_time != '00:00:00' and cycle_leg_time != '00:00:00' order by run_leg_time ASC LIMIT 1";
    		result2 = db.rows(query2);
    		//shortest bike time
    		query3= "SELECT r.cycle_leg_time as cycle_leg_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.age_category="+ageGroup[ag]['id']+" and r.race_type=2 and country=99 and result_time != '00:00:00' and swim_leg_time != '00:00:00' and run_leg_time != '00:00:00' and cycle_leg_time != '00:00:00' order by cycle_leg_time ASC LIMIT 1";
    		result3 = db.rows(query3);

            if(result1.size()>0 && result2.size()>0 && result3.size()>0){
                RaceTopData rtd1 =new RaceTopData()
                rtd1.race_type=2
                rtd1.age_category=ageGroup[ag]['id']
                rtd1.shortest_swim_leg_time=result1[0]['swim_leg_time']
                rtd1.shortest_run_leg_time=result2[0]['run_leg_time']
                rtd1.shortest_cycle_leg_time=result3[0]['cycle_leg_time']
                rtd1.indiaGlobal='India'
                rtd1.save(flush:true,failOnError:true)
            }
    	}

    	/*Save all data for Triathlon - Ironman, country india for all age groups, gender male*/
    	Age_group[] ageGroupM=Age_group.findAll()
    	for(def agM=0;agM<ageGroupM.length;agM++){

    		//shortest swim time
    		query1= "SELECT r.swim_leg_time as swim_leg_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.gender=0 and r.age_category="+ageGroupM[agM]['id']+" and r.race_type=2 and country=99 and result_time != '00:00:00' and swim_leg_time != '00:00:00' and run_leg_time != '00:00:00' and cycle_leg_time != '00:00:00' order by swim_leg_time ASC LIMIT 1";
    		result1 = db.rows(query1);
    		//shortest run time
    		query2= "SELECT r.run_leg_time as run_leg_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.gender=0 and r.age_category="+ageGroupM[agM]['id']+" and r.race_type=2 and country=99 and result_time != '00:00:00' and swim_leg_time != '00:00:00' and run_leg_time != '00:00:00' and cycle_leg_time != '00:00:00' order by run_leg_time ASC LIMIT 1";
    		result2 = db.rows(query2);
    		//shortest bike time
    		query3= "SELECT r.cycle_leg_time as cycle_leg_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.gender=0 and r.age_category="+ageGroupM[agM]['id']+" and r.race_type=2 and country=99 and result_time != '00:00:00' and swim_leg_time != '00:00:00' and run_leg_time != '00:00:00' and cycle_leg_time != '00:00:00' order by cycle_leg_time ASC LIMIT 1";
    		result3 = db.rows(query3);

            if(result1.size()>0 && result2.size()>0 && result3.size()>0){
        		RaceTopData rtd1 =new RaceTopData()
        		rtd1.race_type=2
        		rtd1.age_category=ageGroupM[agM]['id']
    		    rtd1.gender=0
    			rtd1.shortest_swim_leg_time=result1[0]['swim_leg_time']
    		    rtd1.shortest_run_leg_time=result2[0]['run_leg_time']
    		    rtd1.shortest_cycle_leg_time=result3[0]['cycle_leg_time']
    		    rtd1.indiaGlobal='India'
    		    rtd1.save(flush:true,failOnError:true)
            }
    	}

    	/*Save all data for Triathlon - Ironman, country india for all age groups, gender female*/
    	Age_group[] ageGroupF=Age_group.findAll()
    	for(def agF=0;agF<ageGroupF.length;agF++){

    		//shortest swim time
    		query1= "SELECT r.swim_leg_time as swim_leg_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.gender=1 and r.age_category="+ageGroupF[agF]['id']+" and r.race_type=2 and country=99 and result_time != '00:00:00' and swim_leg_time != '00:00:00' and run_leg_time != '00:00:00' and cycle_leg_time != '00:00:00' order by swim_leg_time ASC LIMIT 1";
    		result1 = db.rows(query1);
    		//shortest run time
    		query2= "SELECT r.run_leg_time as run_leg_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.gender=1 and r.age_category="+ageGroupF[agF]['id']+" and r.race_type=2 and country=99 and result_time != '00:00:00' and swim_leg_time != '00:00:00' and run_leg_time != '00:00:00' and cycle_leg_time != '00:00:00' order by run_leg_time ASC LIMIT 1";
    		result2 = db.rows(query2);
    		//shortest bike time
    		query3= "SELECT r.cycle_leg_time as cycle_leg_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.gender=1 and r.age_category="+ageGroupF[agF]['id']+" and r.race_type=2 and country=99 and result_time != '00:00:00' and swim_leg_time != '00:00:00' and run_leg_time != '00:00:00' and cycle_leg_time != '00:00:00' order by cycle_leg_time ASC LIMIT 1";
    		result3 = db.rows(query3);

            if(result1.size()>0 && result2.size()>0 && result3.size()>0){
        		RaceTopData rtd1 =new RaceTopData()
        		rtd1.race_type=2
        		rtd1.age_category=ageGroupF[agF]['id']
    		    rtd1.gender=1
    			rtd1.shortest_swim_leg_time=result1[0]['swim_leg_time']
    		    rtd1.shortest_run_leg_time=result2[0]['run_leg_time']
    		    rtd1.shortest_cycle_leg_time=result3[0]['cycle_leg_time']
    		    rtd1.indiaGlobal='India'
    		    rtd1.save(flush:true,failOnError:true)
            }
    	}

    	/*Save all data for Triathlon - Ironman, globally for all age groups*/
    	Age_group[] ageGroup1=Age_group.findAll()
    	for(def ag1=0;ag1<ageGroup1.length;ag1++){

    		//shortest swim time
    		query1= "SELECT r.swim_leg_time as swim_leg_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.age_category="+ageGroup1[ag1]['id']+" and r.race_type=2 and result_time != '00:00:00' and swim_leg_time != '00:00:00' and run_leg_time != '00:00:00' and cycle_leg_time != '00:00:00' order by swim_leg_time ASC LIMIT 1";
    		result1 = db.rows(query1);
    		//shortest run time
    		query2= "SELECT r.run_leg_time as run_leg_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.age_category="+ageGroup1[ag1]['id']+" and r.race_type=2 and result_time != '00:00:00' and swim_leg_time != '00:00:00' and run_leg_time != '00:00:00' and cycle_leg_time != '00:00:00' order by run_leg_time ASC LIMIT 1";
    		result2 = db.rows(query2);
    		//shortest bike time
    		query3= "SELECT r.cycle_leg_time as cycle_leg_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.age_category="+ageGroup1[ag1]['id']+" and r.race_type=2 and result_time != '00:00:00' and swim_leg_time != '00:00:00' and run_leg_time != '00:00:00' and cycle_leg_time != '00:00:00' order by cycle_leg_time ASC LIMIT 1";
    		result3 = db.rows(query3);

            if(result1.size()>0 && result2.size()>0 && result3.size()>0){
        		RaceTopData rtd1 =new RaceTopData()
        		rtd1.race_type=2
        		rtd1.age_category=ageGroup1[ag1]['id']
    		    rtd1.shortest_swim_leg_time=result1[0]['swim_leg_time']
    		    rtd1.shortest_run_leg_time=result2[0]['run_leg_time']
    		    rtd1.shortest_cycle_leg_time=result3[0]['cycle_leg_time']
    		    rtd1.indiaGlobal='Global'
    		    rtd1.save(flush:true,failOnError:true)
            }
    	}

    	/*Save all data for Triathlon - Ironman, globally for all age groups, gender male*/
    	Age_group[] ageGroup1M=Age_group.findAll()
    	for(def ag1M=0;ag1M<ageGroup1M.length;ag1M++){

    		//shortest swim time
    		query1= "SELECT r.swim_leg_time as swim_leg_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.gender=0 and r.age_category="+ageGroup1M[ag1M]['id']+" and r.race_type=2 and result_time != '00:00:00' and swim_leg_time != '00:00:00' and run_leg_time != '00:00:00' and cycle_leg_time != '00:00:00' order by swim_leg_time ASC LIMIT 1";
    		result1 = db.rows(query1);
    		//shortest run time
    		query2= "SELECT r.run_leg_time as run_leg_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.gender=0 and r.age_category="+ageGroup1M[ag1M]['id']+" and r.race_type=2 and result_time != '00:00:00' and swim_leg_time != '00:00:00' and run_leg_time != '00:00:00' and cycle_leg_time != '00:00:00' order by run_leg_time ASC LIMIT 1";
    		result2 = db.rows(query2);
    		//shortest bike time
    		query3= "SELECT r.cycle_leg_time as cycle_leg_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.gender=0 and r.age_category="+ageGroup1M[ag1M]['id']+" and r.race_type=2 and result_time != '00:00:00' and swim_leg_time != '00:00:00' and run_leg_time != '00:00:00' and cycle_leg_time != '00:00:00' order by cycle_leg_time ASC LIMIT 1";
    		result3 = db.rows(query3);

            if(result1.size()>0 && result2.size()>0 && result3.size()>0){
        		RaceTopData rtd1 =new RaceTopData()
        		rtd1.race_type=2
        		rtd1.age_category=ageGroup1M[ag1M]['id']
    		    rtd1.gender=0
    			rtd1.shortest_swim_leg_time=result1[0]['swim_leg_time']
    		    rtd1.shortest_run_leg_time=result2[0]['run_leg_time']
    		    rtd1.shortest_cycle_leg_time=result3[0]['cycle_leg_time']
    		    rtd1.indiaGlobal='Global'
    		    rtd1.save(flush:true,failOnError:true)
            }
    	}

    	/*Save all data for Triathlon - Ironman, globally for all age groups, gender female*/
    	Age_group[] ageGroup1F=Age_group.findAll()
    	for(def ag1F=0;ag1F<ageGroup1F.length;ag1F++){

    		//shortest swim time
    		query1= "SELECT r.swim_leg_time as swim_leg_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.gender=1 and r.age_category="+ageGroup1F[ag1F]['id']+" and r.race_type=2 and result_time != '00:00:00' and swim_leg_time != '00:00:00' and run_leg_time != '00:00:00' and cycle_leg_time != '00:00:00' order by swim_leg_time ASC LIMIT 1";
    		result1 = db.rows(query1);
    		//shortest run time
    		query2= "SELECT r.run_leg_time as run_leg_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.gender=1 and r.age_category="+ageGroup1F[ag1F]['id']+" and r.race_type=2 and result_time != '00:00:00' and swim_leg_time != '00:00:00' and run_leg_time != '00:00:00' and cycle_leg_time != '00:00:00' order by run_leg_time ASC LIMIT 1";
    		result2 = db.rows(query2);
    		//shortest bike time
    		query3= "SELECT r.cycle_leg_time as cycle_leg_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.gender=1 and r.age_category="+ageGroup1F[ag1F]['id']+" and r.race_type=2 and result_time != '00:00:00' and swim_leg_time != '00:00:00' and run_leg_time != '00:00:00' and cycle_leg_time != '00:00:00' order by cycle_leg_time ASC LIMIT 1";
    		result3 = db.rows(query3);

            if(result1.size()>0 && result2.size()>0 && result3.size()>0){
        		RaceTopData rtd1 =new RaceTopData()
        		rtd1.race_type=2
        		rtd1.age_category=ageGroup1F[ag1F]['id']
    		    rtd1.gender=1
    			rtd1.shortest_swim_leg_time=result1[0]['swim_leg_time']
    		    rtd1.shortest_run_leg_time=result2[0]['run_leg_time']
    		    rtd1.shortest_cycle_leg_time=result3[0]['cycle_leg_time']
    		    rtd1.indiaGlobal='Global'
    		    rtd1.save(flush:true,failOnError:true)
            }
    	}
    }

    def saveDataHalfIronman(){

    	RaceTopData[] raceTopData=RaceTopData.findAll()
    	for(def r=0;r<raceTopData.length;r++){
    		raceTopData[r].delete(flush:true,failOnError:true); 
    	}

    	def db = new Sql(dataSource);

    	def query1
    	def query2
    	def query3

    	def result1
    	def result2
    	def result3

    	/*Save all data for Triathlon - Half Ironman, country india for all age groups*/
    	Age_group[] ageGroup=Age_group.findAll()
    	for(def ag=0;ag<ageGroup.length;ag++){

    		//shortest swim time
    		query1= "SELECT r.swim_leg_time as swim_leg_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.age_category="+ageGroup[ag]['id']+" and r.race_type=13 and country=99 and result_time != '00:00:00' and swim_leg_time != '00:00:00' and run_leg_time != '00:00:00' and cycle_leg_time != '00:00:00' order by swim_leg_time ASC LIMIT 1";
    		result1 = db.rows(query1);
    		//shortest run time
    		query2= "SELECT r.run_leg_time as run_leg_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.age_category="+ageGroup[ag]['id']+" and r.race_type=13 and country=99 and result_time != '00:00:00' and swim_leg_time != '00:00:00' and run_leg_time != '00:00:00' and cycle_leg_time != '00:00:00' order by run_leg_time ASC LIMIT 1";
    		result2 = db.rows(query2);
    		//shortest bike time
    		query3= "SELECT r.cycle_leg_time as cycle_leg_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.age_category="+ageGroup[ag]['id']+" and r.race_type=13 and country=99 and result_time != '00:00:00' and swim_leg_time != '00:00:00' and run_leg_time != '00:00:00' and cycle_leg_time != '00:00:00' order by cycle_leg_time ASC LIMIT 1";
    		result3 = db.rows(query3);

            if(result1.size()>0 && result2.size()>0 && result3.size()>0){
        		RaceTopData rtd1 =new RaceTopData()
        		rtd1.race_type=13
        		rtd1.age_category=ageGroup[ag]['id']
    		    rtd1.shortest_swim_leg_time=result1[0]['swim_leg_time']
    		    rtd1.shortest_run_leg_time=result2[0]['run_leg_time']
    		    rtd1.shortest_cycle_leg_time=result3[0]['cycle_leg_time']
    		    rtd1.indiaGlobal='India'
    		    rtd1.save(flush:true,failOnError:true)
            }
    	}

    	/*Save all data for Triathlon - Half Ironman, country india for all age groups, gender male*/
    	Age_group[] ageGroupM=Age_group.findAll()
    	for(def agM=0;agM<ageGroupM.length;agM++){

    		//shortest swim time
    		query1= "SELECT r.swim_leg_time as swim_leg_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.gender=0 and r.age_category="+ageGroupM[agM]['id']+" and r.race_type=13 and country=99 and result_time != '00:00:00' and swim_leg_time != '00:00:00' and run_leg_time != '00:00:00' and cycle_leg_time != '00:00:00' order by swim_leg_time ASC LIMIT 1";
    		result1 = db.rows(query1);
    		//shortest run time
    		query2= "SELECT r.run_leg_time as run_leg_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.gender=0 and r.age_category="+ageGroupM[agM]['id']+" and r.race_type=13 and country=99 and result_time != '00:00:00' and swim_leg_time != '00:00:00' and run_leg_time != '00:00:00' and cycle_leg_time != '00:00:00' order by run_leg_time ASC LIMIT 1";
    		result2 = db.rows(query2);
    		//shortest bike time
    		query3= "SELECT r.cycle_leg_time as cycle_leg_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.gender=0 and r.age_category="+ageGroupM[agM]['id']+" and r.race_type=13 and country=99 and result_time != '00:00:00' and swim_leg_time != '00:00:00' and run_leg_time != '00:00:00' and cycle_leg_time != '00:00:00' order by cycle_leg_time ASC LIMIT 1";
    		result3 = db.rows(query3);

            if(result1.size()>0 && result2.size()>0 && result3.size()>0){
        		RaceTopData rtd1 =new RaceTopData()
        		rtd1.race_type=13
        		rtd1.age_category=ageGroupM[agM]['id']
    		    rtd1.gender=0
    			rtd1.shortest_swim_leg_time=result1[0]['swim_leg_time']
    		    rtd1.shortest_run_leg_time=result2[0]['run_leg_time']
    		    rtd1.shortest_cycle_leg_time=result3[0]['cycle_leg_time']
    		    rtd1.indiaGlobal='India'
    		    rtd1.save(flush:true,failOnError:true)
            }
    	}

    	/*Save all data for Triathlon - Half Ironman, country india for all age groups, gender female*/
    	Age_group[] ageGroupF=Age_group.findAll()
    	for(def agF=0;agF<ageGroupF.length;agF++){

    		//shortest swim time
    		query1= "SELECT r.swim_leg_time as swim_leg_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.gender=1 and r.age_category="+ageGroupF[agF]['id']+" and r.race_type=13 and country=99 and result_time != '00:00:00' and swim_leg_time != '00:00:00' and run_leg_time != '00:00:00' and cycle_leg_time != '00:00:00' order by swim_leg_time ASC LIMIT 1";
    		result1 = db.rows(query1);
    		//shortest run time
    		query2= "SELECT r.run_leg_time as run_leg_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.gender=1 and r.age_category="+ageGroupF[agF]['id']+" and r.race_type=13 and country=99 and result_time != '00:00:00' and swim_leg_time != '00:00:00' and run_leg_time != '00:00:00' and cycle_leg_time != '00:00:00' order by run_leg_time ASC LIMIT 1";
    		result2 = db.rows(query2);
    		//shortest bike time
    		query3= "SELECT r.cycle_leg_time as cycle_leg_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.gender=1 and r.age_category="+ageGroupF[agF]['id']+" and r.race_type=13 and country=99 and result_time != '00:00:00' and swim_leg_time != '00:00:00' and run_leg_time != '00:00:00' and cycle_leg_time != '00:00:00' order by cycle_leg_time ASC LIMIT 1";
    		result3 = db.rows(query3);

            if(result1.size()>0 && result2.size()>0 && result3.size()>0){
        		RaceTopData rtd1 =new RaceTopData()
        		rtd1.race_type=13
        		rtd1.age_category=ageGroupF[agF]['id']
    		    rtd1.gender=1
    			rtd1.shortest_swim_leg_time=result1[0]['swim_leg_time']
    		    rtd1.shortest_run_leg_time=result2[0]['run_leg_time']
    		    rtd1.shortest_cycle_leg_time=result3[0]['cycle_leg_time']
    		    rtd1.indiaGlobal='India'
    		    rtd1.save(flush:true,failOnError:true)
            }
    	}

    	/*Save all data for Triathlon - Half Ironman, globally for all age groups*/
    	Age_group[] ageGroup1=Age_group.findAll()
    	for(def ag1=0;ag1<ageGroup1.length;ag1++){

    		//shortest swim time
    		query1= "SELECT r.swim_leg_time as swim_leg_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.age_category="+ageGroup1[ag1]['id']+" and r.race_type=13 and result_time != '00:00:00' and swim_leg_time != '00:00:00' and run_leg_time != '00:00:00' and cycle_leg_time != '00:00:00' order by swim_leg_time ASC LIMIT 1";
    		result1 = db.rows(query1);
    		//shortest run time
    		query2= "SELECT r.run_leg_time as run_leg_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.age_category="+ageGroup1[ag1]['id']+" and r.race_type=13 and result_time != '00:00:00' and swim_leg_time != '00:00:00' and run_leg_time != '00:00:00' and cycle_leg_time != '00:00:00' order by run_leg_time ASC LIMIT 1";
    		result2 = db.rows(query2);
    		//shortest bike time
    		query3= "SELECT r.cycle_leg_time as cycle_leg_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.age_category="+ageGroup1[ag1]['id']+" and r.race_type=13 and result_time != '00:00:00' and swim_leg_time != '00:00:00' and run_leg_time != '00:00:00' and cycle_leg_time != '00:00:00' order by cycle_leg_time ASC LIMIT 1";
    		result3 = db.rows(query3);

            if(result1.size()>0 && result2.size()>0 && result3.size()>0){
        		RaceTopData rtd1 =new RaceTopData()
        		rtd1.race_type=13
        		rtd1.age_category=ageGroup1[ag1]['id']
    		    rtd1.shortest_swim_leg_time=result1[0]['swim_leg_time']
    		    rtd1.shortest_run_leg_time=result2[0]['run_leg_time']
    		    rtd1.shortest_cycle_leg_time=result3[0]['cycle_leg_time']
    		    rtd1.indiaGlobal='Global'
    		    rtd1.save(flush:true,failOnError:true)
            }
    	}

    	/*Save all data for Triathlon - Half Ironman, globally for all age groups, gender male*/
    	Age_group[] ageGroup1M=Age_group.findAll()
    	for(def ag1M=0;ag1M<ageGroup1M.length;ag1M++){

    		//shortest swim time
    		query1= "SELECT r.swim_leg_time as swim_leg_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.gender=0 and r.age_category="+ageGroup1M[ag1M]['id']+" and r.race_type=13 and result_time != '00:00:00' and swim_leg_time != '00:00:00' and run_leg_time != '00:00:00' and cycle_leg_time != '00:00:00' order by swim_leg_time ASC LIMIT 1";
    		result1 = db.rows(query1);
    		//shortest run time
    		query2= "SELECT r.run_leg_time as run_leg_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.gender=0 and r.age_category="+ageGroup1M[ag1M]['id']+" and r.race_type=13 and result_time != '00:00:00' and swim_leg_time != '00:00:00' and run_leg_time != '00:00:00' and cycle_leg_time != '00:00:00' order by run_leg_time ASC LIMIT 1";
    		result2 = db.rows(query2);
    		//shortest bike time
    		query3= "SELECT r.cycle_leg_time as cycle_leg_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.gender=0 and r.age_category="+ageGroup1M[ag1M]['id']+" and r.race_type=13 and result_time != '00:00:00' and swim_leg_time != '00:00:00' and run_leg_time != '00:00:00' and cycle_leg_time != '00:00:00' order by cycle_leg_time ASC LIMIT 1";
    		result3 = db.rows(query3);

            if(result1.size()>0 && result2.size()>0 && result3.size()>0){
        		RaceTopData rtd1 =new RaceTopData()
        		rtd1.race_type=13
        		rtd1.age_category=ageGroup1M[ag1M]['id']
    		    rtd1.gender=0
    			rtd1.shortest_swim_leg_time=result1[0]['swim_leg_time']
    		    rtd1.shortest_run_leg_time=result2[0]['run_leg_time']
    		    rtd1.shortest_cycle_leg_time=result3[0]['cycle_leg_time']
    		    rtd1.indiaGlobal='Global'
    		    rtd1.save(flush:true,failOnError:true)
            }
    	}

    	/*Save all data for Triathlon - Half Ironman, globally for all age groups, gender female*/
    	Age_group[] ageGroup1F=Age_group.findAll()
    	for(def ag1F=0;ag1F<ageGroup1F.length;ag1F++){

    		//shortest swim time
    		query1= "SELECT r.swim_leg_time as swim_leg_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.gender=1 and r.age_category="+ageGroup1F[ag1F]['id']+" and r.race_type=13 and result_time != '00:00:00' and swim_leg_time != '00:00:00' and run_leg_time != '00:00:00' and cycle_leg_time != '00:00:00' order by swim_leg_time ASC LIMIT 1";
    		result1 = db.rows(query1);
    		//shortest run time
    		query2= "SELECT r.run_leg_time as run_leg_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.gender=1 and r.age_category="+ageGroup1F[ag1F]['id']+" and r.race_type=13 and result_time != '00:00:00' and swim_leg_time != '00:00:00' and run_leg_time != '00:00:00' and cycle_leg_time != '00:00:00' order by run_leg_time ASC LIMIT 1";
    		result2 = db.rows(query2);
    		//shortest bike time
    		query3= "SELECT r.cycle_leg_time as cycle_leg_time FROM `result_pool` r,`age_group` a,`country` c WHERE r.country=c.id and r.gender=1 and r.age_category="+ageGroup1F[ag1F]['id']+" and r.race_type=13 and result_time != '00:00:00' and swim_leg_time != '00:00:00' and run_leg_time != '00:00:00' and cycle_leg_time != '00:00:00' order by cycle_leg_time ASC LIMIT 1";
    		result3 = db.rows(query3);

            if(result1.size()>0 && result2.size()>0 && result3.size()>0){
        		RaceTopData rtd1 =new RaceTopData()
        		rtd1.race_type=13
        		rtd1.age_category=ageGroup1F[ag1F]['id']
    		    rtd1.gender=1
    			rtd1.shortest_swim_leg_time=result1[0]['swim_leg_time']
    		    rtd1.shortest_run_leg_time=result2[0]['run_leg_time']
    		    rtd1.shortest_cycle_leg_time=result3[0]['cycle_leg_time']
    		    rtd1.indiaGlobal='Global'
    		    rtd1.save(flush:true,failOnError:true)
            }
    	}
    }
}


