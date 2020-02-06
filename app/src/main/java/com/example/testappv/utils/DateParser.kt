package com.example.testappv.utils

class DateParser {

    companion object {
        private const val START_DAY_OF_WEEK_INDEX = 0
        private const val END_DAY_OF_WEEK_INDEX = 3
        private const val START_DAY_OF_MONTH_INDEX = 5
        private const val END_DAY_OF_MONTH_INDEX = 7
        private const val START_MONTH_INDEX = 8
        private const val END_MONTH_INDEX = 11
        private const val START_YEAR_INDEX = 12
        private const val END_YEAR_INDEX = 16
        private const val START_TIME_INDEX = 17
        private const val END_TIME_INDEX = 22

        fun getDayOfWeek(date: String) =
            when (date.substring(START_DAY_OF_WEEK_INDEX, END_DAY_OF_WEEK_INDEX)) {
                "Mon" -> "Monday"
                "Tue" -> "Monday"
                "Wed" -> "Monday"
                "Thu" -> "Monday"
                "Fri" -> "Monday"
                "Sat" -> "Monday"
                "Sun" -> "Monday"
                else -> "Unknown day"
            }

        @JvmStatic
        fun getDate(date: String): String {
            val day = date.substring(START_DAY_OF_MONTH_INDEX, END_DAY_OF_MONTH_INDEX)
            val month = date.substring(START_MONTH_INDEX, END_MONTH_INDEX)
            val year = date.substring(START_YEAR_INDEX, END_YEAR_INDEX)

            val monthDigit = when (month) {
                "Jan" -> "01"
                "Feb" -> "02"
                "Mar" -> "03"
                "Apr" -> "04"
                "May" -> "05"
                "Jun" -> "06"
                "Jul" -> "07"
                "Aug" -> "08"
                "Sep" -> "09"
                "Oct" -> "10"
                "Nov" -> "11"
                "Dec" -> "12"
                else -> "-1"
            }
            return "$day.$monthDigit.$year"
        }

        @JvmStatic
        fun getTime(date: String) = date.substring(START_TIME_INDEX, END_TIME_INDEX)
    }

}