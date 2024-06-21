DROP TABLE IF EXISTS customer;

CREATE TABLE customer (
    customerId BIGINT AUTO_INCREMENT PRIMARY KEY,
    customerSurName VARCHAR(255),
    customerOtherNames VARCHAR(255),
    emailAddress VARCHAR(255) NOT NULL UNIQUE,
    mobileNumber VARCHAR(255) NOT NULL UNIQUE
);
