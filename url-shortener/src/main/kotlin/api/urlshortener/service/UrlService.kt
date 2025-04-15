package api.urlshortener.service

import api.urlshortener.domain.Url

interface UrlService {
    fun addUrl(originalUrl: String): Url
    fun getUrlByShortUrl(shortUrl: Long): Url?
    fun getUrlByOriginalUrl(originalUrl: String): Url?
}