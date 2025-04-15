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

@RestController
@RequestMapping("/api")
class UrlController(val urlService: UrlService) {
    @GetMapping
    fun getUrls(): Map<Any, Any> {
        return mapOf("Hello," to "World!")
    }

    @GetMapping("/{shortUrl}")
    fun getUrlByShortUrl(@PathVariable shortUrl: Long): ResponseEntity<Any> {
        val foundUrl = urlService.getUrlByShortUrl(shortUrl)
        return if (foundUrl != null) {
            ResponseEntity.ok(foundUrl)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/shorten")
    fun getUrlByShortUrl(@RequestBody urlRequest: UrlRequest): ResponseEntity<Any> {
        val existingUrl = urlService.getUrlByOriginalUrl(urlRequest.url)
        if (existingUrl != null) {
            return ResponseEntity.ok(existingUrl)
        }

        val newUrl = urlService.addUrl(urlRequest.url)
        return ResponseEntity.ok(newUrl)
    }
}