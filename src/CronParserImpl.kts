object CronParserImpl {
    private data class Hour(val hourInt: Int)
    private data class Minute(val minuteInt: Int)

    private const val errorMessage = "Something went wrong! Please check the time format! Ex. 18:10"

    private fun convertCurrentTime(currentTime: String): Pair<Hour, Minute> {
        try {
            val currentTimeList = currentTime.trim().split(":")
            val hourString = currentTimeList.first()
            val minuteString = currentTimeList.component2()

            val hourInt = fromCurrentTimeToInt(hourString, 0..23)
            val minuteInt = fromCurrentTimeToInt(minuteString, 0..59)

            return Pair(Hour(hourInt), Minute(minuteInt))
        } catch (e: Exception) {
            throw java.lang.NumberFormatException(errorMessage)
        }
    }

    private fun fromCurrentTimeToInt(singleCurrentTime: String, range: IntRange): Int {
        try {
            //check if hour and minute are respectively in HH and MM format
            if (singleCurrentTime.length != 2) throw java.lang.NumberFormatException(errorMessage)

            val time = singleCurrentTime.toInt()
            if (time in range) {
                return time
            } else {
                throw java.lang.NumberFormatException(errorMessage)
            }
        } catch (e: Exception) {
            throw java.lang.NumberFormatException(errorMessage)
        }
    }

    private fun convertToHHMMFormat(intValue: Int): String {
        return if (intValue < 10) "0$intValue" else intValue.toString()
    }

    private fun printSchedule(hour: Any, minute: Any, day: Any, command: Any) {
        println("$hour:$minute $day - $command")
    }

    private fun runDaily(currentHour: Int, currentMinute: Int) {
        val scheduledHour = 1
        val scheduledMinute = 30
        val command = "/bin/run_me_daily"

        if (currentHour > 1 || currentHour == 1 && currentMinute > scheduledMinute) {
            printSchedule(convertToHHMMFormat(scheduledHour), scheduledMinute, "tomorrow", command)
        } else {
            printSchedule(convertToHHMMFormat(scheduledHour), scheduledMinute, "today", command)
        }
    }

    private fun runHourly(currentHour: Int, currentMinute: Int) {
        val scheduledMinute = 45
        val command = "/bin/run_me_hourly"

        if (currentHour == 23 && currentMinute > 45) {
            printSchedule("00", scheduledMinute, "tomorrow", command)
        } else if (currentMinute > 45) {
            printSchedule(convertToHHMMFormat(currentHour + 1), scheduledMinute, "today", command)
        } else {
            printSchedule(convertToHHMMFormat(currentHour), scheduledMinute, "today", command)
        }
    }

    private fun runEveryMinute(currentHour: Int, currentMinute: Int) {
        val command = "/bin/run_me_every_minute"
        printSchedule(convertToHHMMFormat(currentHour), convertToHHMMFormat(currentMinute), "today", command)
    }

    private fun runSixtyTimes(currentHour: Int, currentMinute: Int) {
        val scheduledHour = 19
        val dailyCommand = "/bin/run_me_sixty_times"

        if (currentHour > scheduledHour || currentHour == scheduledHour && currentMinute > 0) {
            printSchedule(scheduledHour, "00", "tomorrow", dailyCommand)
        } else {
            printSchedule(scheduledHour, "00", "today", dailyCommand)
        }
    }

    fun printSchedule(currentTime: String) {
        val currentTimePair = convertCurrentTime(currentTime)
        val hour = currentTimePair.first.hourInt
        val minute = currentTimePair.second.minuteInt
        runDaily(hour, minute)
        runHourly(hour, minute)
        runEveryMinute(hour, minute)
        runSixtyTimes(hour, minute)
    }
}

args.forEach {
    print("------$it-------------------\n")
    CronParserImpl.printSchedule(it)
    print("-----------------------------\n")
}