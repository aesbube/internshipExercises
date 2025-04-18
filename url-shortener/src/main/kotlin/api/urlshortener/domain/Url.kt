package api.urlshortener.domain

import jakarta.persistence.*

@Entity
@Table(name = "urls")
data class Url(
    @Column(name = "original_url", nullable = false)
    val originalUrl: String = "",
    @Id
    val shortUrl: Long? = null,
)