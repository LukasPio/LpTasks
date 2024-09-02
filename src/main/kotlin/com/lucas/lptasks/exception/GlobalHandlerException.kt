package com.lucas.lptasks.exception

import com.lucas.lptasks.service.ResponseService
import jakarta.validation.ConstraintViolationException
import org.springframework.dao.DataAccessException
import org.springframework.http.converter.HttpMessageNotReadableException
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
    protected fun httpRequestMethodNotSupported() = responseService.badRequest("The request method is not supported on actual url.")

    @ExceptionHandler(HttpMessageNotReadableException::class)
    protected fun httpMessageNotReadable() = responseService.badRequest("Non-existent params were provided on request body.")

    @ExceptionHandler(MissingServletRequestParameterException::class)
    protected fun missingServletRequestParameter() = responseService.badRequest("Insufficient request params were provided")

    @ExceptionHandler(ConstraintViolationException::class)
    protected fun validationExceptions() = responseService.badRequest("All parameters on request body are required.")

    @ExceptionHandler(IllegalArgumentException::class)
    protected fun illegalArgument() = responseService.badRequest("Invalid values were provided.")

    @ExceptionHandler(DataAccessException::class)
    protected fun dataAccess() = responseService.internalError("An error occurred with saving/getting on database")

    @ExceptionHandler(TaskNotFoundException::class)
    protected fun taskNotFound() = responseService.taskNotFound()

    @ExceptionHandler(InvalidTaskPriorityException::class)
    protected fun invalidTaskPriority() = responseService.badRequest("Invalid priority provided")
}
