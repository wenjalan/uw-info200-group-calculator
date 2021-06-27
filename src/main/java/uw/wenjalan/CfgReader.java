package uw.wenjalan;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// Reads a .cfg file associated with a .xls file
//
// author: Alan Wen
// date: 25 Jun 2021
public class CfgReader {

    // Config data structure class
    public static class Config {

        // builder class
        public static class Builder {
            // fields
            private int minGroupSize;
            private int maxGroupSize;
            private int maxTimeDifference;
            private int dataStartRow;
            private int lastNameCol;
            private int firstNameCol;
            private int uwEmailCol;
            private int timeZoneCol;
            private int hasPreferredTeammatesCol;
            private int preferredTeammatesCol;
            private int preferredRolesCol;

            // build method
            public Config build() {
                return new Config(minGroupSize, maxGroupSize, maxTimeDifference, dataStartRow, lastNameCol, firstNameCol, uwEmailCol, timeZoneCol, hasPreferredTeammatesCol, preferredTeammatesCol, preferredRolesCol);
            }

            // getters and setters
            public int getMinGroupSize() {
                return minGroupSize;
            }

            public void setMinGroupSize(int minGroupSize) {
                this.minGroupSize = minGroupSize;
            }

            public int getMaxGroupSize() {
                return maxGroupSize;
            }

            public void setMaxGroupSize(int maxGroupSize) {
                this.maxGroupSize = maxGroupSize;
            }

            public int getMaxTimeDifference() {
                return maxTimeDifference;
            }

            public void setMaxTimeDifference(int maxTimeDifference) {
                this.maxTimeDifference = maxTimeDifference;
            }

            public int getDataStartRow() {
                return dataStartRow;
            }

            public void setDataStartRow(int dataStartRow) {
                this.dataStartRow = dataStartRow;
            }

            public int getLastNameCol() {
                return lastNameCol;
            }

            public void setLastNameCol(int lastNameCol) {
                this.lastNameCol = lastNameCol;
            }

            public int getFirstNameCol() {
                return firstNameCol;
            }

            public void setFirstNameCol(int firstNameCol) {
                this.firstNameCol = firstNameCol;
            }

            public int getUwEmailCol() {
                return uwEmailCol;
            }

            public void setUwEmailCol(int uwEmailCol) {
                this.uwEmailCol = uwEmailCol;
            }

            public int getTimeZoneCol() {
                return timeZoneCol;
            }

            public void setTimeZoneCol(int timeZoneCol) {
                this.timeZoneCol = timeZoneCol;
            }

            public int getHasPreferredTeammatesCol() {
                return hasPreferredTeammatesCol;
            }

            public void setHasPreferredTeammatesCol(int hasPreferredTeammatesCol) {
                this.hasPreferredTeammatesCol = hasPreferredTeammatesCol;
            }

            public int getPreferredTeammatesCol() {
                return preferredTeammatesCol;
            }

            public void setPreferredTeammatesCol(int preferredTeammatesCol) {
                this.preferredTeammatesCol = preferredTeammatesCol;
            }

            public int getPreferredRolesCol() {
                return preferredRolesCol;
            }

            public void setPreferredRolesCol(int preferredRolesCol) {
                this.preferredRolesCol = preferredRolesCol;
            }
        }

        // fields (public constants)
        public final int MIN_GROUP_SIZE;
        public final int MAX_GROUP_SIZE;
        public final int MAX_TIME_DIFFERENCE;
        public final int DATA_START_ROW;
        public final int LAST_NAME_COL;
        public final int FIRST_NAME_COL;
        public final int UW_EMAIL_COL;
        public final int TIME_ZONE_COL;
        public final int HAS_PREFERRED_TEAMMATES_COL;
        public final int PREFERRED_TEAMMATES_COL;
        public final int PREFERRED_ROLES_COL;

        // private constructor
        private Config(int MIN_GROUP_SIZE, int MAX_GROUP_SIZE, int MAX_TIME_DIFFERENCE, int DATA_START_ROW, int LAST_NAME_COL, int FIRST_NAME_COL, int UW_EMAIL_COL, int TIME_ZONE_COL, int HAS_PREFERRED_TEAMMATES_COL, int PREFERRED_TEAMMATES_COL, int PREFERRED_ROLES_COL) {
            this.MIN_GROUP_SIZE = MIN_GROUP_SIZE;
            this.MAX_GROUP_SIZE = MAX_GROUP_SIZE;
            this.MAX_TIME_DIFFERENCE = MAX_TIME_DIFFERENCE;
            this.DATA_START_ROW = DATA_START_ROW;
            this.LAST_NAME_COL = LAST_NAME_COL;
            this.FIRST_NAME_COL = FIRST_NAME_COL;
            this.UW_EMAIL_COL = UW_EMAIL_COL;
            this.TIME_ZONE_COL = TIME_ZONE_COL;
            this.HAS_PREFERRED_TEAMMATES_COL = HAS_PREFERRED_TEAMMATES_COL;
            this.PREFERRED_TEAMMATES_COL = PREFERRED_TEAMMATES_COL;
            this.PREFERRED_ROLES_COL = PREFERRED_ROLES_COL;
        }

        // toString
        @Override
        public String toString() {
            return "Config{" +
                    "MIN_GROUP_SIZE=" + MIN_GROUP_SIZE +
                    ", MAX_GROUP_SIZE=" + MAX_GROUP_SIZE +
                    ", MAX_TIME_DIFFERENCE=" + MAX_TIME_DIFFERENCE +
                    ", DATA_START_ROW=" + DATA_START_ROW +
                    ", LAST_NAME_COL=" + LAST_NAME_COL +
                    ", FIRST_NAME_COL=" + FIRST_NAME_COL +
                    ", UW_EMAIL_COL=" + UW_EMAIL_COL +
                    ", TIME_ZONE_COL=" + TIME_ZONE_COL +
                    ", HAS_PREFERRED_TEAMMATES_COL=" + HAS_PREFERRED_TEAMMATES_COL +
                    ", PREFERRED_TEAMMATES_COL=" + PREFERRED_TEAMMATES_COL +
                    ", PREFERRED_ROLES_COL=" + PREFERRED_ROLES_COL +
                    '}';
        }
    }

    // returns a configuration from a given file
    public static Config readConfig(File f) throws FileNotFoundException {
        // read the file line by line
        Scanner sc = new Scanner(f);

        // get a Config Builder
        Config.Builder builder = new Config.Builder();

        // while there are more lines
        while (sc.hasNextLine()) {
            // get the next line
            String line = sc.nextLine().trim();

            // if it starts with a comment continue
            if (line.startsWith("#") || line.isEmpty()) {
                continue;
            }

            // split the line on the equals sign
            String[] tokens = line.split("=");
            String key = tokens[0].toUpperCase().trim();
            int val = Integer.parseInt(tokens[1]);

            // store the pair in the builder
            switch (key) {
                case "MIN_GROUP_SIZE":
                    builder.setMinGroupSize(val);
                    break;
                case "MAX_GROUP_SIZE":
                    builder.setMaxGroupSize(val);
                    break;
                case "MAX_TIME_DIFFERENCE":
                    builder.setMaxTimeDifference(val);
                    break;
                case "DATA_START_ROW":
                    builder.setDataStartRow(val);
                    break;
                case "LAST_NAME_COL":
                    builder.setLastNameCol(val);
                    break;
                case "FIRST_NAME_COL":
                    builder.setFirstNameCol(val);
                    break;
                case "UW_EMAIL_COL":
                    builder.setUwEmailCol(val);
                    break;
                case "TIME_ZONE_COL":
                    builder.setTimeZoneCol(val);
                    break;
                case "HAS_PREFERRED_TEAMMATES_COL":
                    builder.setHasPreferredTeammatesCol(val);
                    break;
                case "PREFERRED_TEAMMATES_COL":
                    builder.setPreferredTeammatesCol(val);
                    break;
                case "PREFERRED_ROLES_COL":
                    builder.setPreferredRolesCol(val);
                    break;
                default:
                    System.err.println("Error: Unknown property " + key + " found in .cfg file");
            }
        }

        // once all lines are read, build the config and return it
        Config cfg = builder.build();
        return cfg;
    }

}
