package api.urlshortener.service

interface UrlValidationService {
    fun isValidUrl(url: String): Boolean
}