DROP TABLE IF EXISTS orders;


CREATE TABLE orders (
    orderId BIGINT AUTO_INCREMENT PRIMARY KEY,
    customerName VARCHAR(255),
    bookName VARCHAR(255),
    mobileNumber VARCHAR(255),
    customerId BIGINT,
    bookId BIGINT,
    orderDate datetime,
    FOREIGN KEY (customerId) REFERENCES customer(customerId),
    FOREIGN KEY (mobileNumber) REFERENCES customer(mobileNumber),
    FOREIGN KEY (bookId) REFERENCES books(bookId)
);