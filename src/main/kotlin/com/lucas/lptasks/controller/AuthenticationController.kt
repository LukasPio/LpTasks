package com.lucas.lptasks.controller

import com.lucas.lptasks.dto.AuthenticationDTO
import com.lucas.lptasks.dto.LoginResponseDTO
import com.lucas.lptasks.dto.RegisterDTO
import com.lucas.lptasks.exception.EmailAlreadyRegisteredException
import com.lucas.lptasks.model.User
import com.lucas.lptasks.repository.UserRepository
import com.lucas.lptasks.security.TokenService
import com.lucas.lptasks.service.ResponseService
import com.lucas.lptasks.utils.ApiResponse
import jakarta.validation.Valid
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/app/auth")
class AuthenticationController(
    private val authenticationManager: AuthenticationManager,
    private val responseService: ResponseService,
    private val userRepository: UserRepository,
    private val tokenService: TokenService,
) {
    @PostMapping("/login")
    fun login(
        @RequestBody @Valid data: AuthenticationDTO,
    ): ApiResponse<LoginResponseDTO> {
        val userNamePassword = UsernamePasswordAuthenticationToken(data.email, data.password)
        val auth = authenticationManager.authenticate(userNamePassword)
        val token = tokenService.generateToken(auth.principal as User)
        return responseService.login(token)
    }

    @PostMapping("/register")
    fun register(
        @RequestBody @Valid data: RegisterDTO,
    ): ApiResponse<Unit> {
        if (userRepository.findByEmail(data.email) != null) throw EmailAlreadyRegisteredException()

        val encryptedPassword = BCryptPasswordEncoder().encode(data.password)
        val newUser = User(data.email, encryptedPassword, data.isAdmin)

        userRepository.save(newUser)
        return responseService.noContent("Successfully create new user")
    }
}
