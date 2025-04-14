package api.timestamp.service.impl

import api.timestamp.domain.Timestamp
import api.timestamp.service.TimestampService
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import org.slf4j.Logger
import org.slf4j.LoggerFactory


@Service
class TimestampServiceImpl : TimestampService {
    val logger: Logger = LoggerFactory.getLogger(TimestampServiceImpl::class.java)
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
        val date = normalizeDate(utc).atStartOfDay()
        val unixMillis = date.toEpochSecond(ZoneOffset.UTC) * 1000
        return Timestamp(unixMillis, dateParsing(date))
    }

    private fun normalizeDate(utc: String): LocalDate {
        val parts = utc.split("-")
        val year = parts[0].padStart(4, '0')
        val month = parts[1].padStart(2, '0')
        val day = parts[2].padStart(2, '0')
        val normalized = "$year-$month-$day"

        return LocalDate.parse(normalized)
    }

    private fun parseUnixToUtc(unix: Long): Timestamp {
        val date = Instant.ofEpochMilli(unix).atZone(ZoneOffset.UTC).toLocalDateTime()
        return Timestamp(unix, dateParsing(date))
    }

    private fun dateParsing(date: LocalDateTime): String {
        return date.format(DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss 'GMT'"))
    }
}