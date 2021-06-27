# INFO 200 Group Calculator
This command-line application generates groups based on survey data collected at the beginning of the quarter in INFO 200 at the University of Washington.

## Requirements
- Java 1.8+
- A .xls file (Excel 1997-2003 file) containing responses from students
- A .cfg file (see below) containing various parameters related to group generation

## Configuration
The .cfg file contains various parameters that control the ingest and processing of student data. An example .cfg file, used during the INFO 200 A Summer 2021 quarter, can be found in the repo's root.

## Prepping the .xls file
The following modifications have been made to the Summer 2021 .xls file for convienence:
1. The column containing time zone information has been reduced to a single number equaling the number of hours ahead a student is from Seattle (PST)
2. Students who responded "yes" to requesting preferred teammates but did not respond with any specific students had their requests deleted

## Usage
1. Navigate to out/artifacts/group_calculator_jar
2. Open a command line
3. Use the following command, substituting your own file names:  
```java -jar uw-info-200-group-calculator.jar <surveydata>.xls <config>.cfg```
