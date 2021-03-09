package raceleaderboards1


class SaveRaceTopDataJob {
    def saveRaceTopPositionService;

    static triggers = {

        cron name :'SaveRaceTopPosition Job',cronExpression : "0 0 9 ? * SUN *"
    }

    def execute() {
        /*saveRaceTopPositionService.clearAllData()
        saveRaceTopPositionService.saveDataIronman()
        saveRaceTopPositionService.saveDataHalfIronman()*/
    }
}
