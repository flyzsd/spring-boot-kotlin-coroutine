package com.shudong.spring.boot.kotlinapp.controller

import com.shudong.spring.boot.kotlinapp.repository.Student
import com.shudong.spring.boot.kotlinapp.service.KotlinService
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val logger: Logger = LoggerFactory.getLogger(KotlinController::class.java)

@RestController
@RequestMapping("/api")
class KotlinController(
    private val kotlinService: KotlinService
) {

    @GetMapping("/student")
    fun fetchStudent(): Student {
        val student = runBlocking {
            logger.debug("+inside coroutine")
            delay(1_000)
            kotlinService.fetchStudent()
        }
        println("student -> $student")
        return student
    }

    @GetMapping("/student2")
    suspend fun fetchStudentAsync(): Student {
        logger.debug("+inside coroutine")
        delay(1_000)
        return kotlinService.fetchStudent()
    }
}