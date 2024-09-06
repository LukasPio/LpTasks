package com.lucas.lptasks.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.lucas.lptasks.model.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class TokenService {
    @Value("\${spring.security.token.secret}")
    private val secret: String? = null

    fun generateToken(user: User): String {
        val algorithm: Algorithm = Algorithm.HMAC256(secret)
        val token: String =
            JWT
                .create()
                .withIssuer("auth-api")
                .withSubject(user.username)
                .withExpiresAt(genExpirationDate())
                .sign(algorithm)
                .toString()
        return token
    }

    fun getUserNameByToken(token: String): String {
        val algorithm: Algorithm = Algorithm.HMAC256(secret)
        return JWT
            .require(algorithm)
            .withIssuer("auth-api")
            .build()
            .verify(token)
            .subject
    }

    private fun genExpirationDate(): Instant = LocalDateTime.now().plusHours(3).toInstant(ZoneOffset.of("-03:00"))
}
