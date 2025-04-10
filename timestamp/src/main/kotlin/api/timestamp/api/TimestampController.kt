package api.timestamp.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class TimestampController {
    @GetMapping
    fun hello(): Map<String, String> {
        return mapOf("Message" to "Hello, World!")
    }

    
}