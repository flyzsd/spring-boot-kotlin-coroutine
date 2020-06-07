package com.shudong.spring.boot.kotlinapp.service

import com.shudong.spring.boot.kotlinapp.repository.Student
import com.shudong.spring.boot.kotlinapp.repository.StudentRepository
import kotlinx.coroutines.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import java.time.Instant

private val logger: Logger = LoggerFactory.getLogger(KotlinService::class.java)

data class Contributor(val login: String, val id: Long, val url: String, val contributions: Int)

@Service
class KotlinService(builder: WebClient.Builder, private val studentRepository: StudentRepository) {
    private val gitHubClient = builder.baseUrl("https://api.github.com").build()

    @Transactional
    suspend fun fetchStudent(): Student {
        logger.debug("+fetchStudent")
        delay(1_000)
        logger.debug("-fetchStudent")

        val contributors = coroutineScope {
            val student = async(Dispatchers.IO) {
                val student = studentRepository.findStudentByName("Jerry")
                logger.debug("found student $student")
                student
            }

            logger.debug("....")

            val updated = async(Dispatchers.IO) {
                studentRepository.findStudentByName("Jerry")?.let {
                    studentRepository.save(it.copy(modifiedOn = Instant.now()))
                }
                studentRepository.findStudentByName("Jerry")
            }

            logger.warn("This may not be work for coroutine with Spring Transaction with JDBC, as the statements may run in different threads.")

            val contributors1 = async {
                fetchGithubRepoContributors("flyzsd", "spring-rest")
            }
            val contributors2 = async {
                fetchGithubRepoContributors("flyzsd", "mjpeg")
            }

            logger.debug("found student ${awaitAll(student, updated)}")
            awaitAll(contributors1, contributors2).flatten()
        }
        logger.debug("contributors = $contributors")

//        val contributors = fetchGithubRepoContributors("flyzsd", "spring-rest")
//        logger.debug("contributors = $contributors")
//        val contributors2 = fetchGithubRepoContributors("flyzsd", "mjpeg")
//        logger.debug("contributors = $contributors2")

        return studentRepository.findByName("Jerry").first()
    }

    suspend fun fetchGithubRepoContributors(owner: String, repo: String): List<Contributor> {
        return gitHubClient.get().uri("/repos/$owner/$repo/contributors").accept(MediaType.APPLICATION_JSON).retrieve().awaitBody()
    }
}