package api.timestamp.service.impl

import api.timestamp.domain.Timestamp
import api.timestamp.service.TimestampService
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Service
class TimestampServiceImpl : TimestampService {
    override fun getTimestamp(input: String): Timestamp {
        return if (input.isEmpty()) {
            parseUtcToUnix(LocalDate.now().toString())
        } else if (input.contains("-")) {
            parseUtcToUnix(input)
        } else {
            parseUnixToUtc(input.toLong())
        }
    }

    private fun parseUtcToUnix(utc: String): Timestamp {
        val date = LocalDate.parse(utc).atStartOfDay()
        val unix: Long = date.toEpochSecond(ZoneOffset.UTC) * 1000
        return Timestamp(unix, dateParsing(date))
    }

    private fun parseUnixToUtc(unix: Long): Timestamp {
        val date = Instant.ofEpochMilli(unix).atZone(ZoneOffset.UTC).toLocalDateTime()
        return Timestamp(unix, dateParsing(date))
    }

    private fun dateParsing(date: LocalDateTime): String {
        return date.format(DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss 'GMT'"))
    }
}