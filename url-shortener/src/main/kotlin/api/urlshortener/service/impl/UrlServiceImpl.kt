package api.urlshortener.service.impl

import api.urlshortener.domain.Url
import api.urlshortener.repository.UrlRepository
import api.urlshortener.service.UrlService
import org.springframework.stereotype.Service
import java.net.URI
import java.net.URISyntaxException

@Service
class UrlServiceImpl(val urlRepository: UrlRepository) : UrlService {
    private fun isValidUrl(url: String): Boolean {
        return try {
            val uri = URI(url)
            uri.scheme != null && (uri.scheme.equals("http", true) || uri.scheme.equals("https", true))
        } catch (e: URISyntaxException) {
            false
        }
    }

    override fun getUrlByShortUrl(shortUrl: Long): Url? {
        try {
            return urlRepository.findByShortUrl(shortUrl)
        } catch (e: Exception) {
            throw RuntimeException("Error fetching URL by short URL: ${e.message}", e)
        }
    }

    override fun getUrlByOriginalUrl(originalUrl: String): Url? {
        try {
            return urlRepository.findByOriginalUrl(originalUrl)
        } catch (e: Exception) {
            throw RuntimeException("Error fetching URL by original URL: ${e.message}", e)
        }
    }

    override fun addUrl(originalUrl: String): Url {
        if (!isValidUrl(originalUrl)) {
            throw IllegalArgumentException("Invalid URL format: $originalUrl")
        }
        var shortUrl: Long
        do {
            shortUrl = generateRandomId()
        } while (urlRepository.existsById(shortUrl))

        val url = Url(shortUrl = shortUrl, originalUrl = originalUrl)
        return urlRepository.save(url)
    }

    private fun generateRandomId(): Long {
        return (100_000_000..999_999_999).random().toLong()
    }
}