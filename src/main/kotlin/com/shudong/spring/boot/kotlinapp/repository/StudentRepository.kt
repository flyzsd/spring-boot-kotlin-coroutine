package com.shudong.spring.boot.kotlinapp.repository

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.time.Instant

data class Student(
    @Id val id: String,
    val name: String,
    val modifiedBy: String,
    val modifiedOn: Instant,
    @Version val version: Long
)

interface StudentRepository : CrudRepository<Student, String> {
    fun findByName(name: String): List<Student>

    @Query("SELECT * from student where name = :name")
    fun findStudentByName(@Param("name") name: String): Student?
}