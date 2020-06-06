CREATE TABLE IF NOT EXISTS student
(
    id          VARCHAR(255) NOT NULL PRIMARY KEY COMMENT 'uuid',
    `name`        VARCHAR(255) NOT NULL,
    modified_by VARCHAR(255) NOT NULL,
    modified_on TIMESTAMP    NOT NULL,
    version     BIGINT UNSIGNED
);