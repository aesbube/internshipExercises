package api.urlshortener.service.impl

import api.urlshortener.service.UrlValidationService
import api.urlshortener.service.loggerFor
import org.springframework.stereotype.Service
import java.net.HttpURLConnection
import java.net.URI


@Service
class UrlValidationServiceImpl : UrlValidationService {
    val logger = loggerFor<UrlValidationServiceImpl>()
    override fun isValidUrl(url: String): Boolean {
        logger.info("Validating URL: $url")
        return try {
            val uri = URI(url)
            if (uri.scheme != null && (uri.scheme.equals("http", true) || uri.scheme.equals("https", true))) {
                val connection = uri.toURL().openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.instanceFollowRedirects = true
                connection.connectTimeout = 3000
                connection.readTimeout = 3000

                connection.setRequestProperty(
                    "User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/122.0.0.0 Safari/537.36"
                )
                connection.setRequestProperty(
                    "Accept",
                    "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"
                )
                connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5")

                connection.connect()
                val responseCode = connection.responseCode
                responseCode in 200..399
            } else {
                false
            }
        } catch (e: Exception) {
            logger.info("Failed validation of URL: $url")
            false
        }
    }
}