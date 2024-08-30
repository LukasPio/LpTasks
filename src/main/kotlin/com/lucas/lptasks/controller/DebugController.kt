package com.lucas.lptasks.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("dev")
class DebugController {
    @GetMapping("/hello")
    fun hello(): String {
        val messageToShow = "Hello World"
        return messageToShow
    }
}