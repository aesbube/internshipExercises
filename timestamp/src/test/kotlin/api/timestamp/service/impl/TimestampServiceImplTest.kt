package api.timestamp.service.impl

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class TimestampServiceImplTest {

    private val timestampService = TimestampServiceImpl()
    private val utcTimeZone = TimeZone.getTimeZone("UTC")

    @Test
    fun `test unix timestamp input`() {
        val unixTime = 1609459200000L // 2021-01-01 00:00:00 UTC
        val timestamp = timestampService.getTimestamp(unixTime.toString())

        assertEquals(unixTime, timestamp.unix)
        assertEquals("Fri, 01 Jan 2021 00:00:00 UTC", timestamp.utc)
    }

    @Test
    fun `test YYYY-MM-DD format`() {
        val timestamp = timestampService.getTimestamp("2023-12-25")

        val expectedDate = getExpectedDate(2023, 11, 25)
        assertEquals(expectedDate.time, timestamp.unix)
        assertEquals("Mon, 25 Dec 2023 00:00:00 UTC", timestamp.utc)
    }

    @Test
    fun `test DD-MM-YYYY format`() {
        val timestamp = timestampService.getTimestamp("13-01-2023")

        val expectedDate = getExpectedDate(2023, 0, 13)
        assertEquals(expectedDate.time, timestamp.unix)
        assertEquals("Fri, 13 Jan 2023 00:00:00 UTC", timestamp.utc)
    }

    @Test
    fun `test MM-DD-YYYY format`() {
        val timestamp = timestampService.getTimestamp("07-04-2023")

        val expectedDate = getExpectedDate(2023, 6, 4)
        assertEquals(expectedDate.time, timestamp.unix)
        assertEquals("Tue, 04 Jul 2023 00:00:00 UTC", timestamp.utc)
    }

    @Test
    fun `test YYYY-MM-DD format with dots`() {
        val timestamp = timestampService.getTimestamp("2023.12.25")

        val expectedDate = getExpectedDate(2023, 11, 25)
        assertEquals(expectedDate.time, timestamp.unix)
        assertEquals("Mon, 25 Dec 2023 00:00:00 UTC", timestamp.utc)
    }

    @Test
    fun `test ancient date handling`() {
        val timestamp = timestampService.getTimestamp("13-01-0213")

        // Testing that January 13, 213 is correctly handled
        timestamp.utc?.let { assertTrue(it.contains("13 Jan 0213")) }
    }

    @Test
    fun `test future date handling`() {
        val timestamp = timestampService.getTimestamp("01-01-2100")

        val expectedDate = getExpectedDate(2100, 0, 1)
        assertEquals(expectedDate.time, timestamp.unix)
        assertEquals("Fri, 01 Jan 2100 00:00:00 UTC", timestamp.utc)
    }

    @Test
    fun `test invalid date format throws exception`() {
        val exception = assertThrows<IllegalArgumentException> {
            timestampService.getTimestamp("not-a-date")
        }
        assertEquals("Unrecognized date format: not-a-date", exception.message)
    }

    @Test
    fun `test invalid date value throws exception`() {
        val exception = assertThrows<IllegalArgumentException> {
            timestampService.getTimestamp("31-02-2023") // February 31st is invalid
        }
        assertEquals("Unrecognized date format: 31-02-2023", exception.message)
    }

    private fun getExpectedDate(year: Int, month: Int, day: Int): Date {
        val calendar = Calendar.getInstance(utcTimeZone)
        calendar.set(year, month, day, 0, 0, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }
}