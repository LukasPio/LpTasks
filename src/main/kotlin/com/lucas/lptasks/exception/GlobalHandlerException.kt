package com.lucas.lptasks.exception

import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import com.lucas.lptasks.service.ResponseService
import jakarta.validation.ConstraintViolationException
import org.springframework.dao.DataAccessException
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.orm.jpa.JpaSystemException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.resource.NoResourceFoundException

@RestControllerAdvice
class GlobalHandlerException(
    private val responseService: ResponseService,
) {
    @ExceptionHandler(NoResourceFoundException::class)
    protected fun noResourceFound() = responseService.notFound("Invalid url provided")

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    protected fun httpRequestMethodNotSupported() = responseService.badRequest("The request method is not supported on actual url")

    @ExceptionHandler(HttpMessageNotReadableException::class)
    protected fun httpMessageNotReadable() = responseService.badRequest("Non-existent params were provided on request body")

    @ExceptionHandler(MissingServletRequestParameterException::class)
    protected fun missingServletRequestParameter() = responseService.badRequest("Insufficient request params were provided")

    @ExceptionHandler(ConstraintViolationException::class)
    protected fun validationExceptions() = responseService.badRequest("All parameters on request body are required")

    @ExceptionHandler(IllegalArgumentException::class)
    protected fun illegalArgument() = responseService.badRequest("Invalid parameters were provided")

    @ExceptionHandler(DataAccessException::class)
    protected fun dataAccess() = responseService.internalError("An error occurred with saving/getting on database")

    @ExceptionHandler(UsernameNotFoundException::class)
    protected fun userNameNotFound() = responseService.badRequest("Email is not registered")

    @ExceptionHandler(EmailAlreadyRegisteredException::class)
    protected fun emailAlreadyRegistered() = responseService.badRequest("Email already registered")

    @ExceptionHandler(JWTCreationException::class)
    protected fun jwtCreation() = responseService.internalError("Error while generating jwt token")

    @ExceptionHandler(JWTVerificationException::class)
    protected fun jwtVerification() = responseService.badRequest("Error while validating JWT token")

    @ExceptionHandler(TaskNotFoundException::class)
    protected fun taskNotFound() = responseService.taskNotFound()

    @ExceptionHandler(InvalidOrderSortException::class)
    protected fun invalidOrderSort() = responseService.badRequest("Invalid order sort provided")

    @ExceptionHandler(InvalidTaskCategoryException::class)
    protected fun invalidTaskCategory() = responseService.badRequest("Invalid task category provided")

    @ExceptionHandler(InvalidTaskPriorityException::class)
    protected fun invalidTaskPriority() = responseService.badRequest("Invalid task priority provided")

    @ExceptionHandler(JpaSystemException::class)
    protected fun jpaSystem() = responseService.internalError("An internal error occurred, try again later")
}
