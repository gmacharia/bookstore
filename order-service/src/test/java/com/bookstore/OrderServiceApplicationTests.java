package com.bookstore;

import com.bookstore.dto.OrderDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@AutoConfigureWebTestClient(timeout = "360000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderServiceApplicationTests {

    @Autowired
    private WebTestClient webClient;
    private static final String ORDERS = "/api/v1/orders";

    @Test
    @DisplayName("Test that we return no order found if no order are found.")
    public void testThatWeReturnNoRecordFoundIfNoOrderIsFound() {
        this.webClient.get()
                .uri(ORDERS + "/84")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.statuscode").isEqualTo(2)
                .jsonPath("$.statusmessage").isEqualTo("Order not found");
    }

    @Test
    @DisplayName("Test that we return order is found.")
    public void testThatWeReturnWhenOrderIsFound() {
        this.webClient.get()
                .uri(ORDERS + "/2")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.statuscode").isEqualTo(3);
    }

    @Test
    @DisplayName("Test that a order is saved successfully")
    public void testThatOrderIsCreatedSuccessfully() {

        var createBook = OrderDTO.builder()
                .customerId(2L)
                .bookId(3L)
                .mobileNumber("254731098765")
                .build();

        this.webClient.post()
                .uri(ORDERS)
                .body(BodyInserters.fromValue(createBook))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.statuscode").isEqualTo(3)
                .jsonPath("$.statusmessage").isEqualTo("Successfully processed")
                .jsonPath("$.response.mobileNumber").isEqualTo("254731098765");
    }

    @Test
    @DisplayName("Test that update of an existing order")
    public void testThatOrderIsUpdatedSuccessfully() {
        var updateOrder = OrderDTO.builder()
                .customerId(1L)
                .bookId(2L)
                .mobileNumber("254731098765")
                .build();

        this.webClient.put()
                .uri(ORDERS + "/1")
                .body(BodyInserters.fromValue(updateOrder))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.statuscode").isEqualTo(3);

        this.webClient.get()
                .uri(ORDERS + "/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.statuscode").isEqualTo(3)
                .jsonPath("$.response.orderId").isEqualTo(1);
    }

    @Test
    @DisplayName("Test that we return order using customer id.")
    public void testThatWeReturnOderWhenCustomerIdIsUsed() {
        this.webClient.get()
                .uri(ORDERS + "/customers/1/orders")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.statuscode").isEqualTo(3)
                .jsonPath("$.response.customerId").isEqualTo(1);
    }
    
    @Test
    @DisplayName("Test that we return book is found.")
    public void testThatWeReturnWhenBookIsFound() {
        this.webClient.get()
                .uri(ORDERS + "/books/3/orders")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.statuscode").isEqualTo(3)
                .jsonPath("$.response.author").isEqualTo("Ben Carson");
    }
    
      @Test
    @DisplayName("Test that we return book is not found.")
    public void testThatWeReturnWhenBookIsNotFound() {
        this.webClient.get()
                .uri(ORDERS + "/books/90/orders")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.statuscode").isEqualTo(2)
                .jsonPath("$.statusmessage").isEqualTo("Book not found");
    }
}
