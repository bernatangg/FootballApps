package com.result.isoftscore

import com.result.isoftscore.util.*
import junit.framework.Assert.assertEquals
import org.junit.Test

class UtilTest {

    @Test
    fun dateFormatter() {
        val dateString = "2018-10-04"
        assertEquals("Thu, Oct 04 2018", dateString.dateFormatter())
    }

    @Test
    fun timeFormatter() {
        val timeString = "20:00:00"
        assertEquals("20:00", timeString.timeFormatter())
    }

    @Test
    fun dateTimeToMillis() {
        val testDateString = "2018-10-04 02:30:00"
        val expectedMillis: Long = 1538595000000
        assertEquals(expectedMillis, testDateString.dateTimeToMillis())
    }

    @Test
    fun splitAndJoin() {
        val listString = "Andros Townsend; Wilfried Zaha; "
        assertEquals("Andros Townsend;\nWilfried Zaha;\n",
                listString.splitAndJoin(";", ";\n"))
    }

    @Test
    fun nullToEmpty() {
        val nullString: String? = null
        assertEquals("", nullString.nullToEmpty())
    }

}