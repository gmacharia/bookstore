DROP TABLE IF EXISTS orders;


CREATE TABLE orders (
    orderId BIGINT AUTO_INCREMENT PRIMARY KEY,
    customerId BIGINT,
    bookId BIGINT,
    mobileNumber VARCHAR(255),
    orderDate datetime,
    FOREIGN KEY (customerId) REFERENCES customer(customerId),
    FOREIGN KEY (mobileNumber) REFERENCES customer(mobileNumber),
    FOREIGN KEY (bookId) REFERENCES books(bookId)
);