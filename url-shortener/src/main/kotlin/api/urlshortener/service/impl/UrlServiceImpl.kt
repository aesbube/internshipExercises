package api.urlshortener.service.impl

import api.urlshortener.domain.Url
import api.urlshortener.repository.UrlRepository
import api.urlshortener.service.UrlService
import org.springframework.stereotype.Service
import java.net.HttpURLConnection
import java.net.URI
import java.net.URISyntaxException
import java.net.URL

@Service
class UrlServiceImpl(val urlRepository: UrlRepository) : UrlService {
    // popraj go ova ne chini :(
    private fun isValidUrl(url: String): Boolean {
        return try {
            val uri = URI(url)
            if (uri.scheme != null && (uri.scheme.equals("http", true) || uri.scheme.equals("https", true))) {
                val connection = uri.toURL().openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5")
                connection.connectTimeout = 3000
                connection.readTimeout = 3000
                connection.instanceFollowRedirects = true

                connection.connect()
                val responseCode = connection.responseCode
                responseCode in 200..399
            } else {
                false
            }
        } catch (e: Exception) {
            println("Error validating URL: ${e.message}")
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