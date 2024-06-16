DROP TABLE IF EXISTS books;

CREATE TABLE books
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(250) NOT NULL,
    author      VARCHAR(250) NOT NULL,
    isbn        VARCHAR(250) DEFAULT NULL,
    price       double DEFAULT NULL
);
