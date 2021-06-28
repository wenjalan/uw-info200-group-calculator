# INFO 200 Group Calculator
This command-line application generates groups based on survey data collected at the beginning of the quarter in INFO 200 at the University of Washington.

## Requirements
- Java 1.8+
- A .xls file (Excel 1997-2003 file) containing responses from students
- A .cfg file (see below) containing various parameters related to group generation

## Usage
### Step 1: Data Cleaning
A few changes must be made to the original .xls file for the program to parse data correctly. This section will vary depending on the quarter, since the survey format is bound to change ever so slightly between quarters. The changes made to the data in Summer 2021 include the following:
1. All entries in the column of data containing student time zones were converted to a single number describing how many hours ahead a student was from Seattle (e.g. "14 hours ahead" changed to "14")
2. Students who responded "Yes" to if they wanted to request specific teammates, but didn't list any specific UW NetIDs were changed to "No" (e.g. "Place me in a group with people in my timezone!")
3. Students who responded "No preference or don't know" to their requested role had their response emptied for that cell

### Step 2: Configuration
The .cfg file describes various parameters related to the ingest and processing of data. An example .cfg file (below) is found in the [project root directory](https://github.com/wenjalan/uw-info200-group-calculator/blob/main/info200su2021.cfg), used during the Summer 2021 session. All parameters found in the .cfg file must be set for the program to work correctly (even if time zones aren't enabled).

```
# This configuration file defines certain parameters related to the ingest and processing of data.

### Group Generation Settings ###
# Whether or not timezone differences should be considered
# This parameter (theoretically) was only used during the last remote quarter of Summer 2021
# 1=TRUE 0=FALSE
ENABLE_TIME_ZONE=1

# The minimum size of a group (unused)
MIN_GROUP_SIZE=3

# The maximum size of a group
MAX_GROUP_SIZE=4

# The maximum time difference between the mean time zones of two groups to tolerate when combining groups
# Only used if ENABLE_TIME_ZONE is enabled
MAX_TIME_DIFFERENCE=4

### Excel Spreadsheet Parameters ###
# The index of the first row of student data, literally, the where the headers stop and the data begins
DATA_START_ROW=4

# The index of the column containing student last names
LAST_NAME_COL=2

# The index of the column containing student first names
FIRST_NAME_COL=3

# The index of the column containing student UW net ids (emails)
UW_EMAIL_COL=4

# The index of the column containing time zone information (hours offset from Seattle, used if ENABLE_TIME_ZONE is enabled)
TIME_ZONE_COL=10

# The index of the column containing whether this student would like to define preferred teammates
HAS_PREFERRED_TEAMMATES_COL=11

# The index of the column containing this student's preferred teammates, if any
PREFERRED_TEAMMATES_COL=12

# The index of the column containing this student's preferred roles, if any
PREFERRED_ROLES_COL=13
```

### Step 3: Running the .jar
![image](https://user-images.githubusercontent.com/36051502/123704149-354e3800-d81a-11eb-8709-9288bc34c5f8.png)
Clone (or download) the (jar directory)[https://github.com/wenjalan/uw-info200-group-calculator/tree/main/out/artifacts/group_calculator_jar] to your local machine. Replace the surveydata.xls and config.cfg files with your respective files. Run the `run.bat` included, or run the following command in the commandline in the same directory:  

`java -jar uw-info-200-group-calculator.jar surveydata.xls config.cfg`  

If everything goes well, you should see a new .xls file generated containing groups generated with your parameters. If something goes wrong, play around with the configuration file, or contact me at wenjalan(at)uw(dot)edu. Happy generating!
