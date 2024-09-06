package com.lucas.lptasks.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import lombok.EqualsAndHashCode
import lombok.Getter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.UUID

@Entity
@Table(name = "users")
@EqualsAndHashCode
@Getter
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private val id: UUID = UUID.randomUUID(),
    private var email: String,
    @Column(name = "password")
    private var password: String,
    private var isAdmin: Boolean = false,
) : UserDetails {
    constructor(email: String, encryptedPassword: String, isAdmin: Boolean) : this(UUID.randomUUID(), email, encryptedPassword, isAdmin)

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        if (isAdmin) {
            mutableListOf(SimpleGrantedAuthority("ROLE_ADMIN"), SimpleGrantedAuthority("ROLE_USER"))
        } else {
            mutableListOf(SimpleGrantedAuthority("ROLE_USER"))
        }

    override fun getPassword(): String = this.password

    override fun getUsername(): String = this.email
}
