package api.timestamp.service.impl

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.text.SimpleDateFormat
import java.util.*

class TimestampServiceImplTest {

    private val timestampService = TimestampServiceImpl()
    private val utcTimeZone = TimeZone.getTimeZone("UTC")

    @Test
    fun `test empty input returns current timestamp`() {
        val before = System.currentTimeMillis()
        val timestamp = timestampService.getTimestamp("")
        val after = System.currentTimeMillis()

        assertTrue(timestamp.unix in before..after)

        val formatter = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'UTC'", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        val expectedFormattedTime = formatter.format(Date(timestamp.unix))
        assertEquals(expectedFormattedTime, timestamp.utc)
    }

    @Test
    fun `test unix timestamp input`() {
        val unixTime = 1609459200000L
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
        val timestamp = timestampService.getTimestamp("13-1-2023")

        val expectedDate = getExpectedDate(2023, 0, 13)
        assertEquals(expectedDate.time, timestamp.unix)
        assertEquals("Fri, 13 Jan 2023 00:00:00 UTC", timestamp.utc)
    }

    @Test
    fun `test MM-DD-YYYY format`() {
        val timestamp = timestampService.getTimestamp("7-13-2023")

        val expectedDate = getExpectedDate(2023, 6, 13)
        assertEquals(expectedDate.time, timestamp.unix)
        assertEquals("Thu, 13 Jul 2023 00:00:00 UTC", timestamp.utc)
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
        val timestamp = timestampService.getTimestamp("13-1-0213")

        timestamp.utc?.let { assertTrue(it.contains("13 Jan 0213")) }
    }

    @Test
    fun `test future date handling`() {
        val timestamp = timestampService.getTimestamp("1-1-2100")

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
            timestampService.getTimestamp("31-2-2023")
        }
        assertEquals("Unrecognized date format: 31-2-2023", exception.message)
    }

    @Test
    fun `test missing day part throws exception`() {
        val exception = assertThrows<IllegalArgumentException> {
            timestampService.getTimestamp("2023-07")
        }
        assertEquals("Unrecognized date format: 2023-07", exception.message)
    }

    @Test
    fun `test too many segments throws exception`() {
        val exception = assertThrows<IllegalArgumentException> {
            timestampService.getTimestamp("2023-07-13-10")
        }
        assertEquals("Too many separators in date: 2023-07-13-10", exception.message)
    }

    @Test
    fun `test invalid characters throws exception`() {
        val exception = assertThrows<IllegalArgumentException> {
            timestampService.getTimestamp("July-13-2023")
        }
        assertEquals("Unrecognized date format: July-13-2023", exception.message)
    }

    @Test
    fun `test slashes instead of dashes or dots throws exception`() {
        val exception = assertThrows<IllegalArgumentException> {
            timestampService.getTimestamp("13/07/2023")
        }
        assertEquals("Unrecognized date format: 13/07/2023", exception.message)
    }

    @Test
    fun `test mixed delimiters throws exception`() {
        val exception = assertThrows<IllegalArgumentException> {
            timestampService.getTimestamp("2023-07.13")
        }
        assertEquals("Unrecognized date format: 2023-07.13", exception.message)
    }

    @Test
    fun `test reversed order date throws exception`() {
        val exception = assertThrows<IllegalArgumentException> {
            timestampService.getTimestamp("2023-13-07")
        }
        assertEquals("Unrecognized date format: 2023-13-07", exception.message)
    }

    @Test
    fun `test alphabetic input throws exception`() {
        val exception = assertThrows<IllegalArgumentException> {
            timestampService.getTimestamp("not-a-date")
        }
        assertEquals("Unrecognized date format: not-a-date", exception.message)
    }

    private fun getExpectedDate(year: Int, month: Int, day: Int): Date {
        val calendar = Calendar.getInstance(utcTimeZone)
        calendar.set(year, month, day, 0, 0, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }
}