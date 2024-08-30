package com.lucas.lptasks

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class LpTasksApplication
fun main(args: Array<String>) {
    runApplication<LpTasksApplication>(*args)
}
