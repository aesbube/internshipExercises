package api.urlshortener.api

import api.urlshortener.domain.Url
import api.urlshortener.domain.UrlRequest
import api.urlshortener.repository.UrlRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.*
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UrlControllerIntegrationTest {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var urlRepository: UrlRepository

    private fun baseUrl(path: String = "") = "http://localhost:$port/api/shorturl$path"

    @BeforeEach
    fun setup() {
        urlRepository.deleteAll()
    }

    @Test
    fun `POST should create and return a new short URL`() {
        val request = UrlRequest("https://example.com")
        val response = restTemplate.postForEntity(
            baseUrl(), request, Url::class.java
        )

        assertEquals(HttpStatus.OK, response.statusCode)
        val body = response.body!!
        assertEquals("https://example.com", body.originalUrl)
        assertNotNull(body.shortUrl)
    }

    @Test
    fun `POST should return existing short URL for same original`() {
        val saved = urlRepository.save(Url("https://existing.com",123456789))

        val request = UrlRequest("https://existing.com")
        val response = restTemplate.postForEntity(
            baseUrl(), request, Url::class.java
        )

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(saved.shortUrl, response.body!!.shortUrl)
    }

    @Test
    fun `GET should redirect to original URL when shortUrl exists`() {
        val saved = urlRepository.save(Url("https://redirect.com",555555555))

        val response = restTemplate.getForEntity(
            baseUrl("/${saved.shortUrl}"), String::class.java
        )

        assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    fun `GET should return 404 when shortUrl does not exist`() {
        val response = restTemplate.getForEntity(
            baseUrl("/999999999"), String::class.java
        )

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertTrue(response.body!!.contains("URL not found"))
    }

    @Test
    fun `POST should return 500 for invalid URL`() {
        val request = UrlRequest("not-a-valid-url")

        val response = restTemplate.postForEntity(
            baseUrl(), request, String::class.java
        )

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
        assertTrue(response.body!!.contains("Invalid URL"))
    }
}
