CREATE TABLE IF NOT EXISTS Student
(
    id          VARCHAR(255) NOT NULL PRIMARY KEY COMMENT 'uuid',
    name        VARCHAR(255) NOT NULL,
    modified_by VARCHAR(255) NOT NULL,
    modified_on TIMESTAMP    NOT NULL,
    deleted_on  TIMESTAMP    NULL,
    version     BIGINT UNSIGNED
);