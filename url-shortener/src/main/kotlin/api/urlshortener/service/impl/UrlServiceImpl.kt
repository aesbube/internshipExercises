package api.urlshortener.service.impl

import api.urlshortener.domain.Url
import api.urlshortener.repository.UrlRepository
import api.urlshortener.service.UrlService
import api.urlshortener.service.UrlValidationService
import api.urlshortener.service.loggerFor
import org.springframework.stereotype.Service


@Service
class UrlServiceImpl(val urlRepository: UrlRepository, val urlValidationService: UrlValidationService) : UrlService {
    val logger = loggerFor<UrlServiceImpl>()
    override fun getUrlByShortUrl(shortUrl: Long): Url? {
        return urlRepository.findByShortUrl(shortUrl)
    }

    override fun getUrlByOriginalUrl(originalUrl: String): Url? {
        return urlRepository.findByOriginalUrl(originalUrl)
    }

    override fun addUrl(originalUrl: String): Url {
        if (!urlValidationService.isValidUrl(originalUrl)) {
            throw IllegalArgumentException("Invalid URL: $originalUrl")
        }
        var shortUrl: Long
        do {
            shortUrl = generateRandomId()
        } while (urlRepository.existsById(shortUrl))

        val url = Url(shortUrl = shortUrl, originalUrl = originalUrl)
        logger.info("Generated short URL: $shortUrl for original URL: $originalUrl")
        return urlRepository.save(url)
    }

    private fun generateRandomId(): Long {
        return (100_000_000..999_999_999).random().toLong()
    }
}