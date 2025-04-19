package api.urlshortener.domain

import jakarta.persistence.*

@Entity
@Table(name = "urls")
data class Url(
    @Column(name = "original_url", nullable = false)
    val originalUrl: String = "",
    @Id
    @Column(name = "short_url", nullable = false)
    val shortUrl: Long? = null,
)