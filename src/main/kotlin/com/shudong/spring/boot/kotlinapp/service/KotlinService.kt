package com.shudong.spring.boot.kotlinapp.service

import com.shudong.spring.boot.kotlinapp.repository.Student
import com.shudong.spring.boot.kotlinapp.repository.StudentRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

private val logger: Logger = LoggerFactory.getLogger(KotlinService::class.java)

data class Contributor(val login: String, val id: Long, val url: String, val contributions: Int)

@Service
class KotlinService(builder: WebClient.Builder, private val studentRepository: StudentRepository) {
    private val gitHubClient = builder.baseUrl("https://api.github.com").build()

    suspend fun fetchStudent(): Student {
        logger.debug("+fetchStudent")
        delay(1_000)
        logger.debug("-fetchStudent")

        val contributors = coroutineScope {
            val student = async {
                val student = studentRepository.findStudentByName("Jerry")
                logger.debug("found student $student")
                student
            }

            logger.debug("....")

            val contributors1 = async {
                fetchGithubRepoContributors("flyzsd", "spring-rest")
            }
            val contributors2 = async {
                fetchGithubRepoContributors("flyzsd", "mjpeg")
            }

            logger.debug("found student ${student.await()}")
            contributors1.await() + contributors2.await()
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