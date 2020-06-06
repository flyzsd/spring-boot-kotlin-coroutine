package com.shudong.spring.boot.kotlinapp

import com.shudong.spring.boot.kotlinapp.repository.Student
import com.shudong.spring.boot.kotlinapp.repository.StudentRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.data.repository.findByIdOrNull
import java.time.Instant
import java.util.*

@EnableJdbcRepositories
@SpringBootApplication
class KotlinAppApplication {
	@Bean
	fun init(repository: StudentRepository) = CommandLineRunner {
		val id = UUID.randomUUID().toString()
		repository.save(Student(id, "Jerry", "Jerry", Instant.now(), 0L))
		val student = repository.findByIdOrNull(id)!!
		repository.save(student.copy(modifiedOn = Instant.now()))
		println("latest student = ${repository.findByIdOrNull(id)}")
		val count = repository.count()
		println("number of student in database = $count")
	}
}

fun main(args: Array<String>) {
	runApplication<KotlinAppApplication>(*args)
}
