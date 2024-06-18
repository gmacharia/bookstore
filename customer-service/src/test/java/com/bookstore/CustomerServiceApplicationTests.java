package com.bookstore;

import com.bookstore.dto.CustomerDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@AutoConfigureWebTestClient(timeout = "360000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerServiceApplicationTests {

    @Autowired
    private WebTestClient webClient;
    private static final String CUSTOMER = "/api/v1/customers";

    @Test
    @DisplayName("Test that the user is able to fetch a list of customers")
    public void testThatAUserCanFetchCustomer() {
        this.webClient.get()
                .uri(CUSTOMER + "/2")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.statuscode").isEqualTo(3)
                .jsonPath("$.response.length()").isEqualTo("5");
    }

    @Test
    @DisplayName("Test that update of an existing book")
    public void testThatABookIsUpdatedSuccessfully() {

        var updateCustomer = CustomerDTO.builder()
                .customerOthertNames("Jane Doe")
                .customerSurName("Smith")
                .emailAddress("janedoe@gmail.com")
                .mobileNumber("26097000000")
                .build();

        this.webClient.put()
                .uri(CUSTOMER + "/4")
                .body(BodyInserters.fromValue(updateCustomer))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.statuscode").isEqualTo(3);

        this.webClient.get()
                .uri(CUSTOMER + "/4")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.statuscode").isEqualTo(3)
                .jsonPath("$.response.mobileNumber").isEqualTo("26097000000");
    }

    @Test
    @DisplayName("Test that a customer is saved successfully")
    public void testThatCustomerIsCreatedSuccessfully() {

        var createCustomer = CustomerDTO.builder()
                .customerOthertNames("Jane Doe")
                .customerSurName("Smith")
                .emailAddress("janedoe@gmail.com")
                .mobileNumber("260981271173")
                .build();

        this.webClient.post()
                .uri(CUSTOMER)
                .body(BodyInserters.fromValue(createCustomer))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.statuscode").isEqualTo(3)
                .jsonPath("$.response.customerSurName").isEqualTo("Smith");
    }
}
