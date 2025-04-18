package api.timestamp.service.impl

import api.timestamp.domain.Timestamp
import api.timestamp.service.TimestampService
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*

@Service
class TimestampServiceImpl : TimestampService {

    override fun getTimestamp(input: String): Timestamp {
        return if (input.isEmpty()) {
            parseDateToTimestamp(Date())
        } else if (input.contains("-") || input.contains(".")) {
            parseFlexibleDate(input)
        } else {
            parseUnixToTimestamp(input.toLong())
        }
    }

    private val datePatterns = listOf(
            "yyyy-MM-dd",
            "dd-MM-yyyy",
            "MM-dd-yyyy",
            "yyyy.MM.dd",
            "dd.MM.yyyy",
            "MM.dd.yyyy"
    )

    private fun parseFlexibleDate(input: String): Timestamp {
        for (pattern in datePatterns) {
            try {
                val formatter = SimpleDateFormat(pattern, Locale.getDefault()).apply {
                    isLenient = false
                    timeZone = TimeZone.getTimeZone("UTC")
                }
                val date = formatter.parse(input)
                if (date != null) return parseDateToTimestamp(date)
            } catch (_: Exception) {
            }
        }
        throw IllegalArgumentException("Unrecognized date format: $input")
    }

    private fun parseUnixToTimestamp(unix: Long): Timestamp {
        val date = Date(unix)
        return parseDateToTimestamp(date)
    }

    private fun parseDateToTimestamp(date: Date): Timestamp {
        val formatter = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'UTC'", Locale.US).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        return Timestamp(date.time, formatter.format(date))
    }
}

