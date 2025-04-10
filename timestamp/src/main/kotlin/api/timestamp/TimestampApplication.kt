package api.timestamp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TimestampApplication

fun main(args: Array<String>) {
    runApplication<TimestampApplication>(*args)
}
