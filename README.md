# cron-parser

Small project written in Kotlin that given a specific time in HH:MM format, produces next scheduling of these 4 tasks:
- /bin/run_me_daily
- /bin/run_me_hourly
- /bin/run_me_every_minute
- /bin/run_me_sixty_times

## How to run:

Please follow these instructions to install Kotlin compiler: https://kotlinlang.org/docs/command-line.html#install-the-compiler

### Please type this command to run the script:
```
$ kotlinc -script CronParserImpl.kts [HH:MM], [HH:MM], ....
```

Examples:

```
$ kotlinc -script CronParserImpl.kts 18:10

$ kotlinc -script CronParserImpl.kts 12:10 18:16
```
