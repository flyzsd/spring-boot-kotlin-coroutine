### Spring Boot 2.3 with Kotlin Coroutine, Spring MVC, Spring Data JDBC ###


## Docker setup
docker run --name mariadb -e MARIADB_ROOT_PASSWORD=p@ssw0rd -d -p 3306:3306 mariadb:10.7.3
docker stop mariadb
docker start mariadb

CREATE DATABASE IF NOT EXISTS test_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;