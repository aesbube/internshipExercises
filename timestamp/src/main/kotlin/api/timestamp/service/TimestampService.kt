package api.timestamp.service

import api.timestamp.domain.Timestamp

interface TimestampService {
    fun getTimestamp(input: String): Timestamp
}