### Spring Boot 2.3 with Kotlin Coroutine, Spring MVC, Spring Data JDBC ###


## Docker setup
$docker pull mysql
$docker run --name docker-mysql -e MYSQL_ROOT_PASSWORD=password -d -p 3306:3306 mysql
$docker stop docker-mysql
$docker start docker-mysql

CREATE DATABASE IF NOT EXISTS test_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;