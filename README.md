# Simple bible group meeting notification app

## Technologies used
* Kotlin
* Gradle
* JDK 21


## Getting started

### Prerequisites
Make sure you have the Java JDK 21 installed
You can check which version you have installed using this command:
``` bash
java -version
```

### Running the application locally

#### Building the application
Need to set an environment variable GOOGLE_SHEET_XLSX_URL to current google sheet bashrc example:
```
export GOOGLE_SHEET_XLSX_URL='https://docs.google.com/spreadsheets/d/12312454123123/export?format=xlsx#gid=0'
```

To build locally
``` shell bash
./gradlew clean build
```

To run, you can simply run this command can skip -D if environment variable is set
``` shell bash
./gradlew run -DGOOGLE_SHEET_XLSX_URL=https://docs.google.com/spreadsheets/d/12312454123123/export?format=xlsx#gid=0
```
or on windows ```
gradlew.bat run -DGOOGLE_SHEET_XLSX_URL=https://docs.google.com/spreadsheets/d/12312454123123/export?format=xlsx#gid=0
```


### Upgrading the gradle wrapper
Find the newest version of gradle here: https://gradle.org/releases/ Then run this command:

``` shell bash
./gradlew wrapper --gradle-version $gradleVersjon
```
