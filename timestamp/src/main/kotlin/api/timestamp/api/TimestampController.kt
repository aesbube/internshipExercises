package api.timestamp.api

import api.timestamp.service.TimestampService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class TimestampController(private val timestampService: TimestampService) {
    @GetMapping
    fun now(): ResponseEntity<Any> {
        return ResponseEntity.ok(timestampService.getTimestamp(""))
    }
    @GetMapping("/{timestamp}")
    fun getTimestamp(@PathVariable timestamp: String): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(timestampService.getTimestamp(timestamp))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid timestamp format")
        }
    }


}