package api.urlshortener.repository

import api.urlshortener.domain.Url
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UrlRepository : JpaRepository<Url, Long> {
    fun findByShortUrl(shortUrl: Long): Url?
    fun findByOriginalUrl(originalUrl: String): Url?
}