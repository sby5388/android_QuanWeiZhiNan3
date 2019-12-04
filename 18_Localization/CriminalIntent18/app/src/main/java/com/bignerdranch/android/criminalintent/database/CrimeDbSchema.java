package com.bignerdranch.android.criminalintent.database;

/**
 * Schema:架构，框架
 */
public class CrimeDbSchema {
    public static final class CrimeTable {
        public static final String NAME = "crimes";

        // TODO: 2019/11/7 使用接口的形式来让数据更加简单
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
            public static final String SUSPECT = "suspect";
        }
    }
}