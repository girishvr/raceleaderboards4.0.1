package raceleaderboards1
import groovy.sql.Sql

class TestController {

    def saveRaceTopDataService;

    def index() { 

      saveRaceTopDataService.clearAllData()
      saveRaceTopDataService.saveDataIronman()
      saveRaceTopDataService.saveDataHalfIronman()
    }
}
