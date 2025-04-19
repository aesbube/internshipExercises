package api.urlshortener.api

import api.urlshortener.domain.UrlRequest
import api.urlshortener.service.UrlService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView

@RestController
@RequestMapping("/api/shorturl")
class UrlController(val urlService: UrlService) {
    @GetMapping("/{shortUrl}")
    fun getUrlByShortUrl(@PathVariable shortUrl: Long): Any =
        urlService.getUrlByShortUrl(shortUrl)?.let {
            RedirectView(it.originalUrl)
        } ?: ResponseEntity.status(404).body(mapOf("error" to "URL not found"))


    @PostMapping
    fun getUrlByOriginalUrl(@RequestBody urlRequest: UrlRequest): ResponseEntity<Any> {
        try {
            val existingUrl = urlService.getUrlByOriginalUrl(urlRequest.url)
            if (existingUrl != null) {
                return ResponseEntity.ok(existingUrl)
            }
            val newUrl = urlService.addUrl(urlRequest.url)
            return ResponseEntity.ok(newUrl)
        } catch (e: Exception) {
            return ResponseEntity.status(500).body(mapOf("error" to " ${e.message}"))
        }
    }
}